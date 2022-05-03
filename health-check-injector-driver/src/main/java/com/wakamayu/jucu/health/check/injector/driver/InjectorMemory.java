/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.driver;

import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import io.prometheus.client.Gauge;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Named("MEMORY")
@Stateless
//@Stateful
public class InjectorMemory implements Driver {

//    static final Gauge osMemoryUsedRatio = Gauge.build().name("os_memory_used_ratio")
//            .help("The ratio of the systems memory that is currently used (values are 0-1)").register();
    @Override
    public TracerModel execute(TracerModel model) {
        // Summary.Timer summaryTimer = osHandlerMemoryLatencySeconds.startTimer();
//        osMemoryUsedRatio.inc();
        if (model.getMax() != null && model.getMin() != null) {

            long freeMemory = Runtime.getRuntime().freeMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long usageMemory = totalMemory - freeMemory;
            double percent = (usageMemory*100) / totalMemory;

            Double max = model.getMax();
            Double min = model.getMin();

        	if(percent <= min && percent <= max) {
    			model.setStatus(TypeStatus.AVAILABILITY);
    		}else if ( percent > min && percent <= max) {
    			model.setStatus(TypeStatus.CRITICAL);			
    		}else {
    			model.setStatus(TypeStatus.UNAVAILABILITY);
    		}
            model.setRate(percent);

        }
//        osMemoryUsedRatio.dec();
        // summaryTimer.observeDuration();
        return model;
    }

}
