/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.interfaces;

import java.io.FileNotFoundException;

import com.wakamayu.jucu.health.check.injector.model.ResponseHealth;

/**
 *
 * @author carlos
 */
public interface FactoryHealth {

    public void configure(FactoryConfigure factoryConfigure) throws FileNotFoundException ;

    public ResponseHealth all();

    public ResponseHealth readyLiveness();

    public ResponseHealth readyReadiness();

}
