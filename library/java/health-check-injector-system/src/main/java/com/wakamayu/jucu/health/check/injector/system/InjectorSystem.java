/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.system;

import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import com.wakamayu.jucu.health.check.injector.model.DriverTracerModel;
import com.wakamayu.jucu.health.check.injector.model.TypeStatus;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Named("MEMORY")
@Stateless
public class InjectorSystem implements Driver {
    
    @Override
    public DriverTracerModel execute(DriverTracerModel model) {
        
        if (model.getMax() != null && model.getMin() != null) {

            /*            OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            System.out.println(operatingSystemMXBean.getSystemLoadAverage());*/
            long freeMemory = Runtime.getRuntime().freeMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long usageMemory = totalMemory - freeMemory;
            double percentaje = (usageMemory * 100) / totalMemory;
            
            if (model.getMax() >= percentaje && model.getMin() >= percentaje) {
                model.setStatus(TypeStatus.AVAILABILITY);
            } else if (model.getMin() <= percentaje) {
                model.setStatus(TypeStatus.CRITICAL);
            } else {
                model.setStatus(TypeStatus.UNAVAILABILITY);
            }
            model.setRate(percentaje);
            /* Total number of processors or cores available to the JVM 
            System.out.println("Available processors (cores): "
                    + Runtime.getRuntime().availableProcessors());
             */
        }
        return model;
    }
    
}
