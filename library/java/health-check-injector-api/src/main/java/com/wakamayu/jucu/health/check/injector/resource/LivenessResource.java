package com.wakamayu.jucu.health.check.injector.resource;

import com.wakamayu.jucu.health.check.injector.annotate.HealthCheck;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealthCheck;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carlos
 */
@Singleton
@Path("health")
public class LivenessResource {

    @Inject
    @HealthCheck
    private FactoryHealthCheck factoryHealthCheck;

    @GET
    @Path("live")
    public Response liveness() {
        return Response.ok(factoryHealthCheck.readyLiveness()).build();
    }

}
