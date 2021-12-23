/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.utils;


import java.lang.annotation.Annotation;
import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;

/**
 *
 * @author carlos
 */
@Stateless
public class InstanceAnnotated {

    public <T> T find(Instance<T> instances, Annotation annotation) {
        T target = null;
        Instance<T> event = instances.select(annotation);
        if (!event.isAmbiguous() && !event.isUnsatisfied()) {
              target = event.get();
        }
        return target;
    }


}
