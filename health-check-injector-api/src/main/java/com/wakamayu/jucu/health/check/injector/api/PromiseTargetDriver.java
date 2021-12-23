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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 *
 * @author carlos
 */
@Stateless
public class PromiseTargetDriver {

    @Inject
    private InstanceAnnotated annotated;

    @Any
    @Inject
    private Instance<Driver> instancesDrivers;

    private List<PromiseTarget> promiseTracers;

    public void build(Collection<TracerModel> driverTracerModels) {
        promiseTracers = new ArrayList();
        driverTracerModels.forEach((driverTracerModel) -> {
            Driver driver = annotated.find(instancesDrivers, new AnnotatedName(driverTracerModel.getType().name()));
            if (driver != null) {
                promiseTracers.add((PromiseTarget) (Action action, Object data) -> {
                    action.resolve(driver.execute(driverTracerModel));
                });
            }
        });

    }

    public List<TracerModel> getValue() {
        ExecutePromise<List> execute = ExecutePromise.all(promiseTracers).then((action, data) -> {
            action.resolve(data);
        }).start();
        return execute.getValue();
    }

}
