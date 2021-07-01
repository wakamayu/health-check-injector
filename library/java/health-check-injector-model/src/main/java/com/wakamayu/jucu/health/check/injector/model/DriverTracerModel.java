/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author carlos
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverTracerModel implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private TypeDriver type;

    @JsonProperty("port")
    private Integer port;

    @JsonProperty("domainIP")
    private String domainIP;

    @JsonProperty("path")
    private String path;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("min")
    private Double min;

    @JsonProperty("max")
    private Double max;

    @JsonProperty("free")
    private Boolean free;

    @JsonProperty("persistenceUnit")
    private String persistenceUnit;

    @JsonProperty("driver")
    private String driver;

    @JsonProperty("rate")
    private Double rate;
    
    @JsonProperty("acceptable")
    private Double acceptable;

    private TypeStatus status;

    public Double getAcceptable() {
        return acceptable;
    }

    public void setAcceptable(Double acceptable) {
        this.acceptable = acceptable;
    }

    
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeDriver getType() {
        return type;
    }

    public void setType(TypeDriver type) {
        this.type = type;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDomainIP() {
        return domainIP;
    }

    public void setDomainIP(String domainIP) {
        this.domainIP = domainIP;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    public String getPersistenceUnit() {
        return persistenceUnit;
    }

    public void setPersistenceUnit(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.port);
        hash = 29 * hash + Objects.hashCode(this.domainIP);
        hash = 29 * hash + Objects.hashCode(this.path);
        hash = 29 * hash + Objects.hashCode(this.protocol);
        hash = 29 * hash + Objects.hashCode(this.min);
        hash = 29 * hash + Objects.hashCode(this.max);
        hash = 29 * hash + Objects.hashCode(this.free);
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
        final DriverTracerModel other = (DriverTracerModel) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.domainIP, other.domainIP)) {
            return false;
        }
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        if (!Objects.equals(this.protocol, other.protocol)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.port, other.port)) {
            return false;
        }
        if (!Objects.equals(this.min, other.min)) {
            return false;
        }
        if (!Objects.equals(this.max, other.max)) {
            return false;
        }
        if (!Objects.equals(this.free, other.free)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DriverTracerModel{" + "name=" + name + ", type=" + type + ", port=" + port + ", domainIP=" + domainIP + ", path=" + path + ", protocol=" + protocol + ", min=" + min + ", max=" + max + ", free=" + free + '}';
    }

}
