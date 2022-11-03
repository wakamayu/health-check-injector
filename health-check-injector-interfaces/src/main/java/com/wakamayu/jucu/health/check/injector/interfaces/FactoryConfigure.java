/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.interfaces;

import java.io.FileNotFoundException;

import com.wakamayu.jucu.health.check.injector.configure.HealthCheckModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeConfig;

/**
 *
 * @author carlos
 */
public interface FactoryConfigure {

    public void configure(String uriFile, TypeConfig config) throws FileNotFoundException ;

    public boolean isValid() throws FileNotFoundException ;
    
    public void build();
    
    public HealthCheckModel getHealthCheckModel();

}
