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
public interface Action {

    public void resolve(Object result);

    public void resolve();

    public void reject(Object reason);

    public void reject();

}