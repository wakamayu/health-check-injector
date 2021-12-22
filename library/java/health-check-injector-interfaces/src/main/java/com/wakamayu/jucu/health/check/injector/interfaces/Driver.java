/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.interfaces;

import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import java.io.Serializable;

/**
 *
 * @author carlos
 */
public interface Driver extends Serializable {

 
    public TracerModel execute(TracerModel model);
    
    
    
}
