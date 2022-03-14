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
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Named("MEMORY")
@Stateless
public class InjectorMemory implements Driver {

    static final Gauge osMemoryUsedRatio = Gauge.build().name("os_memory_used_ratio")
            .help("The ratio of the systems memory that is currently used (values are 0-1)").register();

//    private static final Summary osHandlerMemoryLatencySeconds = Summary.build()
//            .name("promhttp_metric_handler_memory_latency_seconds")
//            .help("request latency in seconds")
//            .register();
    @Override
    public TracerModel execute(TracerModel model) {
        // Summary.Timer summaryTimer = osHandlerMemoryLatencySeconds.startTimer();
        osMemoryUsedRatio.inc();
        if (model.getMax() != null && model.getMin() != null) {

            long freeMemory = Runtime.getRuntime().freeMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long usageMemory = totalMemory - freeMemory;
            double percentaje = (usageMemory*100) / totalMemory;

            Double max = model.getMax();
            Double min = model.getMin();

            if (max >= percentaje && min <= percentaje) {
                model.setStatus(TypeStatus.AVAILABILITY);

            } else if (max <= percentaje && 90 >= percentaje) {
                model.setStatus(TypeStatus.CRITICAL);

            } else {
                model.setStatus(TypeStatus.UNAVAILABILITY);

            }

            model.setRate(percentaje);

        }
        osMemoryUsedRatio.dec();
        // summaryTimer.observeDuration();
        return model;
    }

}
