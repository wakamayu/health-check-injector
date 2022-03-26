/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.api;

import com.wakamayu.jucu.health.check.injector.abstracts.AbstractFactoryHealthCheck;

import com.wakamayu.jucu.health.check.injector.annotate.HealthCheck;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryConfigure;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealth;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealthCheck;
import com.wakamayu.jucu.health.check.injector.model.ResponseHealth;
import com.wakamayu.jucu.health.check.injector.resource.LivenessResource;
import com.wakamayu.jucu.health.check.injector.resource.MetricsPrometheus;
import com.wakamayu.jucu.health.check.injector.resource.PrincipalResource;
import com.wakamayu.jucu.health.check.injector.resource.ReadinessResource;
import io.prometheus.client.Counter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author carlos
 */
@ApplicationPath("/probe")
@ApplicationScoped
public class ApplicationHealth extends Application {

    static final Counter requests = Counter.build()
            .name("requests_total").help("Total requests.").register();

    @Inject
    @Named("FACTORY-CONFIGURE")
    private FactoryConfigure factoryconfigure;

    @Inject
    @Named("FACTORY-HEALTH")
    private FactoryHealth health;

    @PostConstruct
    public void init() {
        InputStream inputStream = ApplicationHealth.class.getResourceAsStream("/provider/provider.health.check.injector");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            for (String line; (line = reader.readLine()) != null;) {
                Class interfaces = Class.forName(line);
                HealthCheck healthCheck = (HealthCheck) interfaces.getDeclaredAnnotation(HealthCheck.class);
                factoryconfigure.configure(healthCheck.fileConfig(), healthCheck.typeConfig());
                factoryconfigure.build();
            }
            health.configure(factoryconfigure);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(LivenessResource.class);
        classes.add(ReadinessResource.class);
        classes.add(PrincipalResource.class);
        classes.add(MetricsPrometheus.class);
        return classes;
    }

    @Any
    @Produces()
    @HealthCheck()
    public FactoryHealthCheck factoryFrom(InjectionPoint injection) {
        FactoryHealthCheck factoryHealthCheck = AbstractFactoryHealthCheck.INITIAL_HEALTH_CHECK;
        Annotated annotated = injection.getAnnotated();
        if (annotated != null) {
            HealthCheck healthCheck = annotated.getAnnotation(HealthCheck.class);
            if (healthCheck != null) {
                factoryHealthCheck = new FactoryHealthCheck() {
                    @Override
                    public ResponseHealth ready() {
                        requests.inc();
                        return health.all();
                    }

                    @Override
                    public ResponseHealth readyLiveness() {
                        requests.inc();
                        return health.readyLiveness();
                    }

                    @Override
                    public ResponseHealth readyReadiness() {
                        requests.inc();
                        return health.readyReadiness();
                    }
                };
            }
        }
        return factoryHealthCheck;
    }

}
