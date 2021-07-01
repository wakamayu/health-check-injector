package com.wakamayu.jucu.health.check.injector.promise;

/**
 *
 * @author carlos
 */
@SuppressWarnings("serial")
public class PromiseException extends Exception {

    private Object mValue;

    PromiseException(Object value) {
        mValue = value;
    }

    Object getValue() {
        return mValue;
    }
}
