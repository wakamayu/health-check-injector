/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.resource;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import jakarta.ejb.Stateless;

import java.io.OutputStreamWriter;
import java.io.Writer;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.StreamingOutput;

/**
 *
 * @author carlos
 */
@Path("strategy/prometheus")
@Stateless
public class MetricsPrometheus {

    @GET
    @Path("metrics")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public StreamingOutput metrics() {
        return output -> {
            try ( Writer writer = new OutputStreamWriter(output)) {
                TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());
            }

        };
    }
}
