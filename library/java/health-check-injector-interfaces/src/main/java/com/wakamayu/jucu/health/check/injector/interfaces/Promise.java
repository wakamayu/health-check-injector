/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.interfaces;

/**
 *
 * @author carlos
 */
public interface Promise {

    public Promise then(PromiseTarget... funcs);

    public Promise start();

}

