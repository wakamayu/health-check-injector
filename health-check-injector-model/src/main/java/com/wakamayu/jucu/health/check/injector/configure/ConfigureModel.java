/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.configure;

import java.util.Objects;

/**
 *
 * @author carlos
 */
public class ConfigureModel {

    private HealthCheckModel healthcheck;

    public HealthCheckModel getHealthcheck() {
        return healthcheck;
    }

    public void setHealthcheck(HealthCheckModel healthcheck) {
        this.healthcheck = healthcheck;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.healthcheck);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfigureModel other = (ConfigureModel) obj;
        if (!Objects.equals(this.healthcheck, other.healthcheck)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConfigureModel{" + "healthCheck=" + healthcheck + '}';
    }

}
