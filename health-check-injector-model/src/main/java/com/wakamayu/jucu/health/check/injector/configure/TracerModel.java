/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.configure;

import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wakamayu.jucu.health.check.injector.enums.TypeDriver;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author carlos
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TracerModel implements Serializable {

    private String componentName;

    private String name;

    private int port;

    private String ip;

    private String protocol;

    private String path;

    private String domain;

    private String driver;

    private Double min;

    @JsonProperty("type")
    private TypeDriver type;

    @JsonProperty("max")
    private Double max;

    @JsonProperty("free")
    private Boolean free;

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("acceptable")
    private Double acceptable;

    @JsonProperty("persistenceunit")
    private String persistenceUnit;
   
    @JsonProperty("initevaluatetime")
    private Long initEvaluteTime;
    
    @JsonProperty("endevaluatetime")
    private Long endEvaluateTime;

    @JsonProperty("timezone")
    private String timeZone;
    
    private TypeStatus status;
 

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public TypeDriver getType() {
        return type;
    }

    public void setType(TypeDriver type) {
        this.type = type;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getAcceptable() {
        return acceptable;
    }

    public void setAcceptable(Double acceptable) {
        this.acceptable = acceptable;
    }

    public String getPersistenceUnit() {
        return persistenceUnit;
    }

    public void setPersistenceUnit(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    public Long getEndEvaluateTime() {
        return endEvaluateTime;
    }

    public void setEndEvaluateTime(Long endEvaluateTime) {
        this.endEvaluateTime = endEvaluateTime;
    }

    public Long getInitEvaluteTime() {
        return initEvaluteTime;
    }

    public void setInitEvaluteTime(Long initEvaluteTime) {
        this.initEvaluteTime = initEvaluteTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.componentName);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + this.port;
        hash = 89 * hash + Objects.hashCode(this.ip);
        hash = 89 * hash + Objects.hashCode(this.protocol);
        hash = 89 * hash + Objects.hashCode(this.path);
        hash = 89 * hash + Objects.hashCode(this.domain);
        hash = 89 * hash + Objects.hashCode(this.driver);
        hash = 89 * hash + Objects.hashCode(this.min);
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + Objects.hashCode(this.max);
        hash = 89 * hash + Objects.hashCode(this.free);
        hash = 89 * hash + Objects.hashCode(this.rate);
        hash = 89 * hash + Objects.hashCode(this.acceptable);
        hash = 89 * hash + Objects.hashCode(this.persistenceUnit);
        hash = 89 * hash + Objects.hashCode(this.initEvaluteTime);
        hash = 89 * hash + Objects.hashCode(this.endEvaluateTime);
        hash = 89 * hash + Objects.hashCode(this.timeZone);
        hash = 89 * hash + Objects.hashCode(this.status);
      
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
        final TracerModel other = (TracerModel) obj;
        if (this.port != other.port) {
            return false;
        }
        if (!Objects.equals(this.componentName, other.componentName)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!Objects.equals(this.protocol, other.protocol)) {
            return false;
        }
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        if (!Objects.equals(this.domain, other.domain)) {
            return false;
        }
        if (!Objects.equals(this.driver, other.driver)) {
            return false;
        }
        if (!Objects.equals(this.persistenceUnit, other.persistenceUnit)) {
            return false;
        }
        if (!Objects.equals(this.timeZone, other.timeZone)) {
            return false;
        }
        if (!Objects.equals(this.min, other.min)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.max, other.max)) {
            return false;
        }
        if (!Objects.equals(this.free, other.free)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        if (!Objects.equals(this.acceptable, other.acceptable)) {
            return false;
        }
        if (!Objects.equals(this.initEvaluteTime, other.initEvaluteTime)) {
            return false;
        }
        if (!Objects.equals(this.endEvaluateTime, other.endEvaluateTime)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
 
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TracerModel{componentName=").append(componentName);
        sb.append(", name=").append(name);
        sb.append(", port=").append(port);
        sb.append(", ip=").append(ip);
        sb.append(", protocol=").append(protocol);
        sb.append(", path=").append(path);
        sb.append(", domain=").append(domain);
        sb.append(", driver=").append(driver);
        sb.append(", min=").append(min);
        sb.append(", type=").append(type);
        sb.append(", max=").append(max);
        sb.append(", free=").append(free);
        sb.append(", rate=").append(rate);
        sb.append(", acceptable=").append(acceptable);
        sb.append(", persistenceUnit=").append(persistenceUnit);
        sb.append(", initEvaluteTime=").append(initEvaluteTime);
        sb.append(", endEvaluateTime=").append(endEvaluateTime);
        sb.append(", timeZone=").append(timeZone);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
    
    
    
    
}
