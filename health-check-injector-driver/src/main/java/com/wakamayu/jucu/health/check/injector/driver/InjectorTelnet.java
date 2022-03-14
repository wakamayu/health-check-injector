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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Named("IP_PORT")
@Stateless
public class InjectorTelnet implements Driver {

    @Override
    public TracerModel execute(TracerModel driverTracerModel) {
        String domainIP = driverTracerModel.getIp();
        Integer port = driverTracerModel.getPort();
        boolean pipe = isSocketAliveUitlity(domainIP, port);
        if (pipe) {
            driverTracerModel.setStatus(TypeStatus.AVAILABILITY);
        } else {
            driverTracerModel.setStatus(TypeStatus.UNAVAILABILITY);
        }
        return driverTracerModel;
    }

    public boolean isSocketAliveUitlity(String hostName, int port) {
        boolean isAlive = false;

        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();
        try {
            socket.connect(socketAddress, 2000);
            socket.close();
            isAlive = true;
        } catch (SocketTimeoutException exception) {

            //       exception.printStackTrace();    
        } catch (IOException exception) {
            //       exception.printStackTrace();
        }
        return isAlive;
    }

}
