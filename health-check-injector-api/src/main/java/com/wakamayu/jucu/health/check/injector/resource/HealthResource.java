package com.wakamayu.jucu.health.check.injector.resource;


import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import com.wakamayu.jucu.health.check.injector.annotate.HealthCheck;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryHealthCheck;
import com.wakamayu.jucu.health.check.injector.model.ResponseHealth;



import jakarta.inject.Inject;


@Path("health")
@Stateless
public class HealthResource {

	@Inject
	@HealthCheck
	FactoryHealthCheck factoryHealthCheck;

	@GET
	public Response all() {
		return buildResponse(factoryHealthCheck.ready());
	}

	@GET
	@Path("live")
	public Response liveness() {
		return buildResponse(factoryHealthCheck.readyLiveness());
	}

	@GET
	@Path("ready")
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
