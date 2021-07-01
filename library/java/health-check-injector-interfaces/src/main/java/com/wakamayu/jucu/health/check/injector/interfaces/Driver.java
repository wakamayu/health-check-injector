/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.interfaces;

import com.wakamayu.jucu.health.check.injector.model.DriverTracerModel;
import java.io.Serializable;

/**
 *
 * @author carlos
 */
public interface Driver extends Serializable {

 
    public DriverTracerModel execute(DriverTracerModel model);
    
    
    
}
