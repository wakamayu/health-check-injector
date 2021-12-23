/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.annotate;

import com.wakamayu.jucu.health.check.injector.enums.TypeConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author carlos
 */
//@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Documented
public @interface HealthCheck {

 String fileConfig() default "/META-INF/healthcheck-config.properties";
    
    TypeConfig typeConfig() default TypeConfig.PROPERTIES; 
}
