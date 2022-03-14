///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.wakamayu.jucu.health.check.injector.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import java.io.Serializable;
//import java.net.URI;
//import java.util.Objects;
//
///**
// *
// * @author carlos
// */
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class DriverWebhookModel implements Serializable {
//
//    @JsonProperty("port")
//    private Integer port;
//
//    @JsonProperty("domainIP")
//    private String domainIP;
//
//    @JsonProperty("path")
//    private String path;
//
//    @JsonProperty("protocol")
//    private String protocol;
//
//    public Integer getPort() {
//        return port;
//    }
//
//    public void setPort(Integer port) {
//        this.port = port;
//    }
//
//    public String getDomainIP() {
//        return domainIP;
//    }
//
//    public void setDomainIP(String domainIP) {
//        this.domainIP = domainIP;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public String getProtocol() {
//        return protocol;
//    }
//
//    public void setProtocol(String protocol) {
//        this.protocol = protocol;
//    }
//
//    public URI getURI() {
//
//        return URI.create(String.format("%s://%s/%s", protocol, domainIP, path));
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 89 * hash + Objects.hashCode(this.port);
//        hash = 89 * hash + Objects.hashCode(this.domainIP);
//        hash = 89 * hash + Objects.hashCode(this.path);
//        hash = 89 * hash + Objects.hashCode(this.protocol);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final DriverWebhookModel other = (DriverWebhookModel) obj;
//        if (!Objects.equals(this.domainIP, other.domainIP)) {
//            return false;
//        }
//        if (!Objects.equals(this.path, other.path)) {
//            return false;
//        }
//        if (!Objects.equals(this.protocol, other.protocol)) {
//            return false;
//        }
//        if (!Objects.equals(this.port, other.port)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "DriverWebhookModel{" + "port=" + port + ", domainIP=" + domainIP + ", path=" + path + ", protocol=" + protocol + '}';
//    }
//
//}
