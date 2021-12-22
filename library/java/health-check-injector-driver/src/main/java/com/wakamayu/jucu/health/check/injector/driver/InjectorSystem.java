/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.driver;

import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
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
    public TracerModel execute(TracerModel model) {
        
        if (model.getMax() != null && model.getMin() != null) {

            long freeMemory = Runtime.getRuntime().freeMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long usageMemory = totalMemory - freeMemory;
            double percentaje = (usageMemory * 100) / totalMemory;
            System.out.println("---------------------------------------------");
            System.err.println(freeMemory);
            System.err.println(totalMemory);
            System.err.println(usageMemory);
            System.err.println(percentaje);
            
            if (model.getMax() >= percentaje && model.getMin() >= percentaje) {
                model.setStatus(TypeStatus.AVAILABILITY);
            } else if (model.getMin() <= percentaje) {
                model.setStatus(TypeStatus.CRITICAL);
            } else {
                model.setStatus(TypeStatus.UNAVAILABILITY);
            }
            model.setRate(percentaje);
        }
        return model;
    }
    
}
