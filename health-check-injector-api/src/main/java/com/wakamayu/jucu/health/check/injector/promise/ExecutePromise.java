package com.wakamayu.jucu.health.check.injector.promise;

import com.wakamayu.jucu.health.check.injector.interfaces.Action;
import com.wakamayu.jucu.health.check.injector.interfaces.Promise;
import com.wakamayu.jucu.health.check.injector.interfaces.PromiseTarget;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class ExecutePromise<T> implements Promise {

    private final PromiseTarget promiseTarget;

    private TypePromise mStatus = TypePromise.PENDING;
    private T mResult;
    private ExecutePromise mNextPromise;

    public ExecutePromise() {
        promiseTarget = null;
    }

    public ExecutePromise(PromiseTarget executor) {
        promiseTarget = executor;
    }

    @Override
    public ExecutePromise then(PromiseTarget... funcs) {
        final List<ExecutePromise> promiseList = new ArrayList();
        if (funcs != null) {
            for (PromiseTarget func : funcs) {
                ExecutePromise promise = new ExecutePromise(func);
                promiseList.add(promise);
            }
        }
        return then(promiseList.toArray(new ExecutePromise[0]));
    }

    public ExecutePromise then(Promise... promises) {
        Promise onFulfilled = null;
        Promise onRejected = null;

        if (promises != null && promises.length > 0) {

            onFulfilled = promises[0];
            if (promises.length > 1) {
                onRejected = promises[1];
            }
        } else {
            throw new RuntimeException("Please set  at least one Promise.");
        }

        if (this.mStatus == TypePromise.FULFILLED) {
            mNextPromise = (ExecutePromise) onFulfilled;

            if (mNextPromise != null && mNextPromise.promiseTarget != null) {
                this.invokeFunction(mNextPromise.promiseTarget, this.mResult);
            } else {

                final ExecutePromise skipPromise = new ExecutePromise(new PromiseTarget() {
                    @Override
                    public void run(Action action, Object data) {
                        action.resolve(data);
                    }
                });
                mNextPromise = skipPromise;
                this.invokeFunction(mNextPromise.promiseTarget, this.mResult);
                return mNextPromise;
            }
        }

        if (this.mStatus == TypePromise.REJECTED) {
            mNextPromise = (ExecutePromise) onRejected;
            if (mNextPromise != null && mNextPromise.promiseTarget != null) {
                this.invokeFunction(mNextPromise.promiseTarget, this.mResult);
            } else {

                final ExecutePromise skipPromise = new ExecutePromise(new PromiseTarget() {
                    @Override
                    public void run(Action action, Object data) {
                        action.reject(data);
                    }
                });
                mNextPromise = skipPromise;
                this.invokeFunction(mNextPromise.promiseTarget, this.mResult);
            }
        }

        return mNextPromise;
    }

    public ExecutePromise always(PromiseTarget func) {
        final ExecutePromise promise = new ExecutePromise(func);
        return always(promise);
    }

    public ExecutePromise always(Promise promise) {
        return then(promise, promise);
    }

    @Override
    public ExecutePromise start() {
        return ExecutePromise.this;
    }

    private void invokeFunction(PromiseTarget func, Object previousPromiseResult) {

        final Semaphore semaphore = new Semaphore(0);

        try {
            func.run(new Action() {
                @Override
                public void resolve(Object result) {
                    mNextPromise.mResult = result;
                    mNextPromise.mStatus = TypePromise.FULFILLED;

                    semaphore.release();
                }

                @Override
                public void reject(Object result) {
                    mNextPromise.mResult = result;
                    mNextPromise.mStatus = TypePromise.REJECTED;
                    semaphore.release();
                }

                @Override
                public void resolve() {
                    resolve(null);
                }

                @Override
                public void reject() {
                    reject(null);
                }
            }, previousPromiseResult);
        } catch (Exception e) {
            mNextPromise.mResult = e;
            mNextPromise.mStatus = TypePromise.REJECTED;

            semaphore.release();
        }

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            mNextPromise.mResult = null;
            // mNextPromise.mIsRejected = true;
            mNextPromise.mStatus = TypePromise.REJECTED;
        }

    }

    public TypePromise getStatus() {
        return mStatus;
    }

    public T getValue() {
        return mResult;
    }

    public static ExecutePromise resolve(Object data) {
        final ExecutePromise promise = new ExecutePromise();
        promise.mStatus = TypePromise.FULFILLED;
        promise.mResult = data;
        return promise;
    }

    public static ExecutePromise resolve() {
        return resolve(null);
    }

    public static ExecutePromise reject(Object reason) {
        final ExecutePromise promise = new ExecutePromise();
        promise.mStatus = TypePromise.REJECTED;
        promise.mResult = reason;
        return promise;
    }

    public static ExecutePromise reject() {
        return reject(null);
    }

    public static ExecutePromise all(Promise... promises) {

        if (promises == null || promises.length == 0) {
            return ExecutePromise.resolve();
        }

        final ExecutorService executor = Executors.newCachedThreadPool();

        final List<Future<ExecutePromise>> futureList = new ArrayList<Future<ExecutePromise>>();

        final List<Object> resultList = new ArrayList<Object>();

        try {

            for (Promise promise : promises) {

                final Callable<ExecutePromise> callable = new Callable<ExecutePromise>() {

                    @Override
                    public ExecutePromise call() throws Exception {
                        ExecutePromise result = ExecutePromise.resolve().then(promise);
                        if (result.getStatus() == TypePromise.REJECTED) {
                            throw new PromiseException(result.getValue());
                        }
                        return result;
                    }
                };

                final Future<ExecutePromise> future = executor.submit(callable);
                futureList.add(future);
            }
        } finally {
            executor.shutdown();
        }

        boolean rejected = false;
        Object rejectedError = null;

        for (Future<ExecutePromise> f : futureList) {
            try {
                ExecutePromise result = f.get();
                resultList.add(result.getValue());
            } catch (InterruptedException e) {
                break;
            } catch (ExecutionException e) {

                final Throwable cause = e.getCause();

                if (cause instanceof PromiseException) {
                    PromiseException pe = (PromiseException) cause;
                    rejectedError = pe.getValue();
                    rejected = true;
                }
                break;
            }
        }

        if (rejected) {
            return ExecutePromise.reject(rejectedError);
        } else {
            return ExecutePromise.resolve(resultList);
        }
    }

    public static ExecutePromise all(List<PromiseTarget> funcs) {
        if (funcs == null || funcs.size() == 0) {
            return ExecutePromise.resolve();
        }

        final List<ExecutePromise> promiseList = new ArrayList<ExecutePromise>();
        if (funcs != null) {
            for (PromiseTarget func : funcs) {
                ExecutePromise promise = new ExecutePromise(func);
                promiseList.add(promise);
            }
        }
        return ExecutePromise.all(promiseList.toArray(new ExecutePromise[0]));
    }

}
