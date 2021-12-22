/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.api;

import com.wakamayu.jucu.health.check.injector.configure.HealthCheckModel;
import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryConfigure;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealth;
import com.wakamayu.jucu.health.check.injector.model.ResponseHealth;
import com.wakamayu.jucu.health.check.injector.utils.InstanceAnnotated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

/**
 *
 * @author carlos
 */
@Singleton
@Named("FACTORY-HEALTH")
public class FactoryHealthImpl implements FactoryHealth {
    
    @Inject
    private InstanceAnnotated instanceAnnotated;
    
    @Inject
    private PromiseTargetDriver promiseTargetDriver;
    
    @Inject
    @Named("WEBHOOK")
    private Event<HealthCheckModel> eventWebhook;
    
    private HealthCheckModel healthCheckModel = new HealthCheckModel();
    
    private Map<String, TracerModel> mapTracerModel = new HashMap();
    
    @Override
    public void configure(FactoryConfigure factoryConfigure) {
        if (factoryConfigure.isValid()) {
            healthCheckModel = factoryConfigure.getHealthCheckModel();
            healthCheckModel.getTracer().forEach((x) -> {
                mapTracerModel.put(x.getName(), x);
            });
        }
    }
    
    public ResponseHealth build(String[] selectTracer) {
        List<TracerModel> resultList = new ArrayList();
        if (selectTracer != null) {
            for (String name : selectTracer) {
                resultList.add(mapTracerModel.get(name));
            }
        }
        promiseTargetDriver.build(resultList);
        return build(promiseTargetDriver.getValue());
    }
    
    public ResponseHealth build(List<TracerModel> listTargetModel) {
        healthCheckModel.setTracer(listTargetModel);
        eventWebhook.fire(healthCheckModel);
        return toResponseHelth(listTargetModel);
    }
    
    public ResponseHealth build() {
        promiseTargetDriver.build(mapTracerModel.values());
        return build(promiseTargetDriver.getValue());
    }
    
    @Override
    public ResponseHealth all() {
        //System.out.println("com.wakamayu.jucu.health.check.injector.api.FactoryHealthImpl.all()");
        return build();
        
    }
    
    @Override
    public ResponseHealth readyLiveness() {
        //System.out.println("com.wakamayu.jucu.health.check.injector.api.FactoryHealthImpl.readyLiveness()");
        return build(healthCheckModel.getLiveness());
    }
    
    @Override
    public ResponseHealth readyReadiness() {
        //System.out.println("com.wakamayu.jucu.health.check.injector.api.FactoryHealthImpl.readyReadiness()");
        return build(healthCheckModel.getRediness());
    }
    
    private ResponseHealth toResponseHelth(List<TracerModel> tracerModels) {
        ResponseHealth responseHealth = new ResponseHealth();
        Integer count = 0;
        if (tracerModels != null && !tracerModels.isEmpty()) {
            for (TracerModel tracerModel : tracerModels) {
                HealthCheckResponseBuilder checkResponse = HealthCheckResponse.named(tracerModel.getName());
                if (TypeStatus.UNAVAILABILITY == tracerModel.getStatus()) {
                    checkResponse.down();
                    count++;
                } else {
                    checkResponse.up();
                }
                setterChecks(checkResponse, tracerModel);
                responseHealth.addCheck(checkResponse.build());
            }
            if (count > 0) {
                responseHealth.setStatus(TypeStatus.DOWN);
            }
        }
        return responseHealth;
    }
    
    private void setterChecks(HealthCheckResponseBuilder checkResponseBuilder, TracerModel tracerModel) {
        
        //System.out.println("com.wakamayu.jucu.health.check.injector.microprofile.FactoryHealthCheckMicroprofile.setterMemory()");
        
        setterProperties(checkResponseBuilder, "driver", tracerModel.getDriver());
        setterProperties(checkResponseBuilder, "domain", tracerModel.getDomain());
        setterProperties(checkResponseBuilder, "ip", tracerModel.getIp());
        setterProperties(checkResponseBuilder, "free", tracerModel.getFree());
        setterProperties(checkResponseBuilder, "min", tracerModel.getMin());
        setterProperties(checkResponseBuilder, "max", tracerModel.getMax());
        setterProperties(checkResponseBuilder, "rate", tracerModel.getRate());
        setterProperties(checkResponseBuilder, "name", tracerModel.getName());
        setterProperties(checkResponseBuilder, "path", tracerModel.getPath());
        setterProperties(checkResponseBuilder, "persistunit", tracerModel.getPersistenceUnit());
        setterProperties(checkResponseBuilder, "status", tracerModel.getStatus().name());
        
    }
    
    private void setterProperties(HealthCheckResponseBuilder checkResponseBuilder, String key, String value) {
        if (key != null) {
            if (value != null) {
                checkResponseBuilder.withData(key, value);
            }
        }
    }
    
    private void setterProperties(HealthCheckResponseBuilder checkResponseBuilder, String key, Boolean value) {
        if (key != null) {
            if (value != null) {
                checkResponseBuilder.withData(key, value);
            }
        }
    }
    
    private void setterProperties(HealthCheckResponseBuilder checkResponseBuilder, String key, Double value) {
        if (key != null) {
            if (value != null) {
                checkResponseBuilder.withData(key, value.toString());
            }
        }
    }
    
}
