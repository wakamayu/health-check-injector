package com.wakamayu.jucu.health.check.injector.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.DeploymentException;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.ws.rs.core.Application;

import com.wakamayu.jucu.health.check.injector.annotate.Health;
import com.wakamayu.jucu.health.check.injector.annotate.HealthCheck;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryConfigure;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealth;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealthCheck;
import com.wakamayu.jucu.health.check.injector.model.ResponseHealth;

@ApplicationScoped
public class InjectPointHealth {
	@Inject
	// @Named("FACTORY-CONFIGURE")
	private FactoryConfigure configure;

	@Inject
	// @Named("FACTORY-HEALTH")
	private FactoryHealth health;

	public void onStart(@Observes @Initialized(ApplicationScoped.class) Object pointless) {
		try {
			InputStream inputStream = InjectPointHealth.class
					.getResourceAsStream("/provider/provider.health.check.injector");
			if (inputStream != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

				for (String line; (line = reader.readLine()) != null;) {
					Class interfaces = Class.forName(line);
					Health healthCheck = (Health) interfaces.getDeclaredAnnotation(Health.class);
					if(healthCheck!= null) {
						configure.configure(healthCheck.fileConfig(), healthCheck.typeConfig());
						configure.build();	
					}
					
				}
				health.configure(configure);
			}
		} catch (ClassNotFoundException | IOException | DeploymentException ex) {
			Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Any
	@Produces
	@HealthCheck
	public FactoryHealthCheck factoryFrom(InjectionPoint injection) {
		FactoryHealthCheck factoryHealthCheck = null;
		Annotated annotated = injection.getAnnotated();
		if (annotated != null) {
			Health healthCheck = annotated.getAnnotation(Health.class);
			if (health != null) {
				factoryHealthCheck = new FactoryHealthCheck() {
					@Override
					public ResponseHealth ready() {
						return health.all();
					}

					@Override
					public ResponseHealth readyLiveness() {
						return health.readyLiveness();
					}

					@Override
					public ResponseHealth readyReadiness() {
						return health.readyReadiness();
					}
				};
			}
		}
		return factoryHealthCheck;
	}
}
