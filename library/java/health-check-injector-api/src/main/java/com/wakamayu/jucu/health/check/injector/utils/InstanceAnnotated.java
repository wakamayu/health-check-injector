/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.utils;

import com.wakamayu.jucu.health.check.injector.annotate.AnnotatedName;

import java.lang.annotation.Annotation;
import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;

/**
 *
 * @author carlos
 */
@Stateless
public class InstanceAnnotated {

//    @Any
//    @Inject
//    private Instance<Factory> instances;
//
//    public <T> T find(Annotation annotation) {
//        T target = null;
//        Instance<T> event = (Instance<T>) instances.select(annotation);
//        System.out.println("com.wakamayu.jucu.health.check.injector.utils.InstanceAnnotated.find()");
//        System.out.println("event() " + event);
//        System.out.println("isAmbiguous() " + event.isAmbiguous());
//        System.err.println("isUnsatisfied() " + event.isUnsatisfied());
//        if (!event.isAmbiguous() && !event.isUnsatisfied()) {
//            target = event.get();
//            System.out.println("------------------------------------");
//            System.out.println(target);
//        }
//        return target;
//    }

    public <T> T find(Instance<T> instances, Annotation annotation, boolean field) {
        T target = null;
        Instance<T> event = instances.select(annotation);
//        System.out.println("com.wakamayu.jucu.health.check.injector.utils.InstanceAnnotated.find()");
//        System.out.println("event() " + event);
//        System.out.println("isAmbiguous() " + event.isAmbiguous());
//        System.err.println("isUnsatisfied() " + event.isUnsatisfied());
//        System.out.println(!event.isAmbiguous() && !event.isUnsatisfied() == field);
        if (!event.isAmbiguous() && !event.isUnsatisfied() == field) {
//            System.out.println("*******************");
//            
//            target = event.get();
//            System.out.println("------------------------------------");
            System.out.println(target);
        }
        return target;
    }

//    public <T> T find(String named) {
//        // if (named != null) {
//        return find(new AnnotatedName(named.toUpperCase()));
//        //}//else{
//        //throw  new Exception("Nombre de instacia vacia");
//        //  }
//    }

}
