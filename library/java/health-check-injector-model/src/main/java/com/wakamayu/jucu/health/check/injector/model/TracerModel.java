package com.wakamayu.jucu.health.check.injector.model;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.wakamayu.jucu.health.check.injector.model;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//import java.util.Arrays;
//import java.util.Objects;
//
///**
// *
// * @author carlos
// */
//public class TracerModel implements Serializable {
//
//    private Timestamp ts;
//    private TracerLineModel[] line;
//
//    public Timestamp getTs() {
//        return ts;
//    }
//
//    public void setTs(Timestamp ts) {
//        this.ts = ts;
//    }
//
//    public TracerLineModel[] getLine() {
//        return line;
//    }
//
//    public void setLine(TracerLineModel[] line) {
//        this.line = line;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 79 * hash + Objects.hashCode(this.ts);
//        hash = 79 * hash + Arrays.deepHashCode(this.line);
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
//        final TracerModel other = (TracerModel) obj;
//        if (!Objects.equals(this.ts, other.ts)) {
//            return false;
//        }
//        if (!Arrays.deepEquals(this.line, other.line)) {
//            return false;
//        }
//        return true;
//    }
//    
//    
//}
