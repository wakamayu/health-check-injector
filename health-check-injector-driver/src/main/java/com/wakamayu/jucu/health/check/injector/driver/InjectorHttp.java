/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.driver;

import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Named("HTTP")
@Stateless
public class InjectorHttp implements Driver {

    @Override
    public TracerModel execute(TracerModel model) {

        String url = String.format("%s://%s:%s/%s", model.getProtocol(), model.getDomain(), model.getPort(), model.getPath());
        System.out.println("com.wakamayu.jucu.health.check.injector.driver.InjectorHttp.execute()");
        System.out.println(url);

        if (validateURL(url)) {
            model.setStatus(TypeStatus.AVAILABILITY);
        } else {
            model.setStatus(TypeStatus.UNAVAILABILITY);
        }

        return model;
    }

    public boolean validateURL(String urlString) {

        try {
            System.err.println(urlString);
            URLConnection urlConnection = new URL(urlString).openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            System.out.println(e);
            return false;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }

}
