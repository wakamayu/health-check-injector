/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.driver;

import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import jakarta.ejb.Stateless;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Named("HTTP")
@Stateless
public class InjectorHttp implements Driver {

//    private Gauge promhttpMetricHandlerRequestsProgress = Gauge.build()
//            .name("promhttp_metric_handler_requests_progress")
//            .help("Inprogress requests.")
//            .register();
//
//    private Summary promhttpMetricHandlerRequestsLatencySeconds = Summary.build()
//            .name("promhttp_metric_handler_requests_latency_seconds")
//            .help("request latency in seconds")
//            .register();
//
//    private Summary promhttpMetricHandlerRequestsSizeBytes = Summary.build()
//            .name("promhttp_metric_handler_requests_size_bytes")
//            .help("request size in bytes")
//            .register();

    @Override
    public TracerModel execute(TracerModel model) {

        try {
            String url = String.format("%s://%s:%s/%s", model.getProtocol(), model.getDomain(), model.getPort(), model.getPath());

            if (validateURL(url)) {
                model.setStatus(TypeStatus.AVAILABILITY);
            } else {
                model.setStatus(TypeStatus.UNAVAILABILITY);
            }

        } catch (IOException ex) {
            model.setStatus(TypeStatus.UNAVAILABILITY);
        }

        return model;
    }

    public boolean validateURL(String urlString) throws IOException {
//        Summary.Timer summaryTimer = promhttpMetricHandlerRequestsLatencySeconds.startTimer();
        HttpURLConnection httpURLConnection = null;
        try {
//            promhttpMetricHandlerRequestsProgress.inc();
            URL url = new URL(urlString);
            httpURLConnection = HttpURLConnection.class.cast(url.openConnection());
            httpURLConnection.setConnectTimeout(50000);

            return true;
        } catch (MalformedURLException e) {

            return false;
        } catch (IOException e) {

            return false;
        } finally {
//            summaryTimer.observeDuration();
//            if (httpURLConnection != null) {
//                promhttpMetricHandlerRequestsSizeBytes.observe(httpURLConnection.getContentLengthLong());
//                promhttpMetricHandlerRequestsProgress.dec();
//            } else {
//                promhttpMetricHandlerRequestsProgress.dec();
//            }
        }
    }

}
