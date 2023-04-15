/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.api;

import com.wakamayu.jucu.health.check.injector.configure.HealthCheckModel;
import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import com.wakamayu.jucu.health.check.injector.interfaces.Action;
import com.wakamayu.jucu.health.check.injector.interfaces.PromiseTarget;
import com.wakamayu.jucu.health.check.injector.promise.ExecutePromise;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Named;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

/**
 *
 * @author carlos
 */
@ApplicationScoped
public class PromiseWebhook {

    public void eventWebhoook(@Observes @Named("WEBHOOK") HealthCheckModel checkModel) {
        promiseTarget(checkModel);
    }

    private String post(String url, Object data) throws Exception {
        WebTarget target = ClientBuilder.newClient().target(url);
        Response response = target.request().post(Entity.json(data));
        return response.readEntity(String.class);
    }

    private void promiseTarget(HealthCheckModel checkModel) {
        List<PromiseTarget> promiseTracers = new ArrayList();
        try {
            if (checkModel != null && checkModel.getTracer() != null && checkModel.getWebhook() != null && !checkModel.getWebhook().isEmpty()) {

                UriBuilder builder = UriBuilder.fromUri(new URI(checkModel.getWebhook()));
                builder.queryParam("status", checkModel.getStatus().toString());

                for (TracerModel tracerModel : checkModel.getTracer()) {
                    tracerModel.setComponentName(checkModel.getName());
                    PromiseTarget promiseTarget = promiseTarget(builder.toString(), tracerModel);
                    promiseTracers.add(promiseTarget);
                }
                promiseTarget(promiseTracers);

            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(PromiseWebhook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void promiseTarget(List<PromiseTarget> promiseTracers) {
        ExecutePromise.all(promiseTracers).then((action, data) -> {
            action.resolve(data);
        }).start();
    }

    private PromiseTarget promiseTarget(String urlWebhook, TracerModel tracerModel) {
        return new PromiseTarget() {
            @Override
            public void run(Action action, Object data) throws Exception {
                action.resolve(post(urlWebhook, tracerModel));
            }
        };
    }

}
