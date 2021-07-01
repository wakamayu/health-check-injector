/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.http;

import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import com.wakamayu.jucu.health.check.injector.model.DriverTracerModel;
import com.wakamayu.jucu.health.check.injector.model.TypeStatus;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Named("HTTP")
@Stateless
public class InjectorHttp implements Driver {

    @Inject
    @Named("IP_PORT")
    Driver driver;

    @Override
    public DriverTracerModel execute(DriverTracerModel model) {
        driver.execute(model);
        String url = String.format("%s://%s:%s/%s", model.getProtocol(), model.getDomainIP(), model.getPort(), model.getPath());
        if (model.getStatus() == TypeStatus.AVAILABILITY) {
            if (validateURL(url)) {
                model.setStatus(TypeStatus.AVAILABILITY);
            } else {
                model.setStatus(TypeStatus.UNAVAILABILITY);
            }
        } else {
            model.setStatus(TypeStatus.UNAVAILABILITY);
        }
        return model;
    }

    public boolean validateURL(String url) {

        try {
            System.err.println(url);
            new URL(url).openConnection().getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

}
