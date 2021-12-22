/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.abstracts;

import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealthCheck;

import com.wakamayu.jucu.health.check.injector.model.ResponseHealth;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public abstract class AbstractFactoryHealthCheck {

    private final static Logger LOGGER = Logger.getLogger("HealthCheck-Provider");

    public static FactoryHealthCheck INITIAL_HEALTH_CHECK = new FactoryHealthCheck() {

//        @Override
//        public void configure(FactoryHealthCheckConfigure healthCheckConfigure) {
//                    LOGGER.log(Level.INFO, "No se configurado Health Check");
//        }
//
//        @Override
//        public void build() {
//                    LOGGER.log(Level.INFO, "No se ha logrado construir el Health Check");
//        }

        @Override
        public ResponseHealth readyLiveness() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ResponseHealth readyReadiness() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ResponseHealth ready() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

//    public static FactoryHealthCheckConfigure INITIAL_HEALTH_CHECK_CONFIGURE = new FactoryHealthCheckConfigure() {
//        @Override
//        public DriverModel getDriverModel() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public void configure(String filePath) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public boolean check() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//    };

}
