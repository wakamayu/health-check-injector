/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.annotate;

import com.wakamayu.jucu.health.check.injector.enums.TypeDriver;

/**
 *
 * @author carlos
 */
public @interface Health {

    public String name();

    public TypeDriver type();

  
    
}
