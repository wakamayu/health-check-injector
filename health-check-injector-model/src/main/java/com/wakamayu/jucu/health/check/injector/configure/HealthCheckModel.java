/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.configure;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class HealthCheckModel {

    private String name;
    private List<TracerModel> tracer;
    private String[] liveness;
    private String[] rediness;
    private String webhook;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TracerModel> getTracer() {
        return tracer;
    }

    public void setTracer(List<TracerModel> tracer) {
        this.tracer = tracer;
    }

    public String[] getLiveness() {
        return liveness;
    }

    public void setLiveness(String[] liveness) {
        this.liveness = liveness;
    }

    public String[] getRediness() {
        return rediness;
    }

    public void setRediness(String[] rediness) {
        this.rediness = rediness;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.tracer);
        hash = 29 * hash + Arrays.deepHashCode(this.liveness);
        hash = 29 * hash + Arrays.deepHashCode(this.rediness);
        hash = 29 * hash + Objects.hashCode(this.webhook);
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
        final HealthCheckModel other = (HealthCheckModel) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.webhook, other.webhook)) {
            return false;
        }
        if (!Objects.equals(this.tracer, other.tracer)) {
            return false;
        }
        if (!Arrays.deepEquals(this.liveness, other.liveness)) {
            return false;
        }
        if (!Arrays.deepEquals(this.rediness, other.rediness)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HealthCheckModel{" + "name=" + name + ", tracer=" + tracer + ", liveness=" + liveness + ", rediness=" + rediness + ", webhook=" + webhook + '}';
    }

}
