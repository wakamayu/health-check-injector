package com.wakamayu.jucu.health.check.injector.implementation;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.wakamayu.jucu.health.check.injector.annotate.HealthCheck;
import com.wakamayu.jucu.health.check.injector.interfaces.Action;
import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import javax.enterprise.inject.spi.InjectionPoint;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealthCheck;
import com.wakamayu.jucu.health.check.injector.interfaces.PromiseTarget;
import com.wakamayu.jucu.health.check.injector.model.DriverModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;

import javax.enterprise.inject.spi.Annotated;
import javax.inject.Inject;
import com.wakamayu.jucu.health.check.injector.model.DriverTracerModel;
import com.wakamayu.jucu.health.check.injector.model.TypeDriver;
import com.wakamayu.jucu.health.check.injector.promise.ExecutePromise;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Singleton;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Singleton
public class HealthCheckApplication {
    
    @Any
    @Inject
    private Instance<Driver> driverInstance;
    
    private DriverModel driverModel;
    
    public boolean checkProperties(String filePath) throws IOException {
        boolean pipe = false;
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (driverModel == null) {
                JavaPropsMapper mapper = new JavaPropsMapper();
                Properties p = new Properties();
                p.load(input);
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                driverModel = mapper.readPropertiesAs(p, DriverModel.class);
                pipe = true;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(HealthCheckApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pipe;
    }
    
    @Produces()
    @HealthCheck()
    public FactoryHealthCheck factoryFrom(InjectionPoint injection) {
        try {
            Annotated annotated = injection.getAnnotated();
            if (annotated != null) {
                HealthCheck healthCheck = annotated.getAnnotation(HealthCheck.class);
                if (healthCheck != null && healthCheck.fileConfig() != null && checkProperties(healthCheck.fileConfig())) {
                    return new HealthCheckFactoryImpl(driverModel.getTracer());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HealthCheckApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    class PromiseTargetImpl implements PromiseTarget {
        
        private Driver driver;
        private DriverTracerModel model;

        private PromiseTargetImpl(Driver driver, DriverTracerModel model) {
            this.driver = driver;
            this.model = model;
            
        }
        
        @Override
        public void run(Action action, Object data) throws Exception {
            
            action.resolve(driver.execute(model));
        }
        
    }
    
    class HealthCheckFactoryImpl implements FactoryHealthCheck {
        
        private List<DriverTracerModel> driverTracerModels;
        
        public HealthCheckFactoryImpl(List<DriverTracerModel> driverTracerModels) {
            this.driverTracerModels = driverTracerModels;
        }
        
        @Override
        public synchronized Object ready() {
            
            final List<PromiseTarget> promiseTargets = new ArrayList<PromiseTarget>();
            
            driverTracerModels.forEach((model) -> {
                Driver driver = findInstance(model.getType());
                if (driver != null) {
                    promiseTargets.add(new PromiseTargetImpl(driver, model));
                }
            });
            
            ExecutePromise start = ExecutePromise.all(promiseTargets).then((action, data) -> {
                
                action.resolve(data);
            }).start();
            return start.getValue();
            
        }
        
        public <V> V findInstance(TypeDriver type) {
            V driver = null;
            Instance<Driver> eventInstance = (Instance<Driver>) driverInstance.select(new DriverName(type));
            if (eventInstance != null && !eventInstance.isAmbiguous() && !eventInstance.isUnsatisfied()) {
                driver = (V) eventInstance.get();
            }
            return driver;
        }
        
    }
    
    static class DriverName extends AnnotationLiteral<Named> implements Named {
        
        private TypeDriver type;
        
        public DriverName(TypeDriver type) {
            super();
            this.type = type;
        }
        
        public TypeDriver getType() {
            return type;
        }
        
        public void setType(TypeDriver type) {
            this.type = type;
        }
        
        @Override
        public String value() {
            return this.type.toString();
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + Objects.hashCode(this.type);
            return hash;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final DriverName other = (DriverName) obj;
            if (this.type != other.type) {
                return false;
            }
            return true;
        }
        
    }
    
}
