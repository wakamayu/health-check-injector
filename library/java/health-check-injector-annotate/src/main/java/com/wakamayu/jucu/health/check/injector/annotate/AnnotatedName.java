package com.wakamayu.jucu.health.check.injector.annotate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Objects;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
public class AnnotatedName extends AnnotationLiteral<Named> implements Named {

    private String type;

    public AnnotatedName(String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String value() {
        return this.type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.type);
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
        final AnnotatedName other = (AnnotatedName) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

}
