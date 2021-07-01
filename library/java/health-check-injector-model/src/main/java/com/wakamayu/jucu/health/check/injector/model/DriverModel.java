/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class DriverModel implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("tracer")
    private List<DriverTracerModel> tracer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DriverTracerModel> getTracer() {
        return tracer;
    }

    public void setTracer(List<DriverTracerModel> tracer) {
        this.tracer = tracer;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return name != null && tracer != null && tracer.size() > 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.tracer);
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
        final DriverModel other = (DriverModel) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.tracer, other.tracer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DriverModel{" + "name=" + name + ", tracer=" + tracer + '}';
    }

}
