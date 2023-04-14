/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.api;

//import com.wakamayu.jucu.health.check.injector.abstracts.AbstractFactoryHealthCheck;

import com.wakamayu.jucu.health.check.injector.resource.HealthResource;
import com.wakamayu.jucu.health.check.injector.resource.MetricsPrometheus;

import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author carlos
 */
@ApplicationPath("strategy")
@ApplicationScoped
public class ApplicationHealth extends Application {

	/*
	 * static final Counter requests = Counter.build()
	 * .name("requests_total").help("Total requests.").register();
	 */
	@PostConstruct
	public void init() {
		System.out.println("PostConstruct - ApplicationHealth");

	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(HealthResource.class);
		classes.add(MetricsPrometheus.class);
		return classes;
	}

}
