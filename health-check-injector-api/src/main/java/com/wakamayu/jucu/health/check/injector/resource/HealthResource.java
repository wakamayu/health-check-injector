package com.wakamayu.jucu.health.check.injector.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.wakamayu.jucu.health.check.injector.annotate.HealthCheck;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealthCheck;
import com.wakamayu.jucu.health.check.injector.model.ResponseHealth;

import javax.inject.Inject;
import javax.inject.Singleton;

@Path("health")
@Singleton
public class HealthResource {

	@Inject
	@HealthCheck
	FactoryHealthCheck factoryHealthCheck;

	@GET
	@Consumes("application/json")
	@Produces("application/json")

	public Response all() {
		return buildResponse(factoryHealthCheck.ready());
	}

	@GET
	@Path("live")
	@Consumes("application/json")
	@Produces("application/json")

	public Response liveness() {
		return buildResponse(factoryHealthCheck.readyLiveness());
	}

	@GET
	@Path("ready")
	@Consumes("application/json")
	@Produces("application/json")

	public Response readiness() {

		return buildResponse(factoryHealthCheck.readyReadiness());
	}

	private Response buildResponse(ResponseHealth responseHealth) {
		if (TypeStatus.UP.equals(responseHealth.getStatus())) {
			return Response.status(Status.ACCEPTED.getStatusCode()).entity(responseHealth).build();
		} else {
			return Response.status(Status.SERVICE_UNAVAILABLE.getStatusCode()).entity(responseHealth).build();
		}
	}

}
