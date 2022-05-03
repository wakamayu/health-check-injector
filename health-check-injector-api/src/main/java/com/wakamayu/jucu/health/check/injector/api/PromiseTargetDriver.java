/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.api;

import com.wakamayu.jucu.health.check.injector.annotate.AnnotatedName;
import com.wakamayu.jucu.health.check.injector.interfaces.Action;
import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import com.wakamayu.jucu.health.check.injector.interfaces.PromiseTarget;
import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.promise.ExecutePromise;
import com.wakamayu.jucu.health.check.injector.utils.InstanceAnnotated;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;

/**
 *
 * @author carlos
 */
@RequestScoped
public class PromiseTargetDriver {

    @Inject
    private InstanceAnnotated annotated;

    @Any
    @Inject
    private Instance<Driver> instancesDrivers;

    private List<PromiseTarget> promiseTracers;

    private void attachInitDate(TracerModel tracerModel) {
        TimeZone timeZone = TimeZone.getDefault();
        if (tracerModel != null) {
            if (tracerModel.getTimeZone() != null) {
                timeZone = TimeZone.getTimeZone(tracerModel.getTimeZone());
            }
            tracerModel.setInitEvaluteTime(Calendar.getInstance(timeZone).getTimeInMillis());
        }
    }

    private void attachEndDate(TracerModel tracerModel) {
        TimeZone timeZone = TimeZone.getDefault();
        if (tracerModel != null) {
            if (tracerModel.getTimeZone() != null) {
                timeZone = TimeZone.getTimeZone(tracerModel.getTimeZone());
            }
            tracerModel.setEndEvaluateTime(Calendar.getInstance(timeZone).getTimeInMillis());
        }
    }

    public void build(Collection<TracerModel> driverTracerModels) {
        promiseTracers = new ArrayList();
        driverTracerModels.forEach((Consumer<TracerModel>) (tracerModel) -> {
            System.out.println("-----------------------------------------------------PromiseTarget Driver");
            System.out.println(tracerModel.getType());
            Driver driver = annotated.find(instancesDrivers, new AnnotatedName(tracerModel.getType().name()));

            System.out.println(driver);
            if (driver != null) {
                attachInitDate(tracerModel);
                promiseTracers.add((PromiseTarget) (Action action, Object data) -> {
                    TracerModel executeTracerModel = driver.execute(tracerModel);
                    attachEndDate(executeTracerModel);
                    action.resolve(executeTracerModel);

                });
            }

        });

    }

    public List<TracerModel> getValue() {
        List<TracerModel> tracerModels = new ArrayList();
        if (promiseTracers.size() > 0) {
            ExecutePromise<List> execute = ExecutePromise.all(promiseTracers).then((action, data) -> {
                action.resolve(data);
            }).start();
            tracerModels = execute.getValue();
        }
        return tracerModels;
    }

}
