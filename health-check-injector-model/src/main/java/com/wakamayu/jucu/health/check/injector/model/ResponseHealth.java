/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.model;

import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.eclipse.microprofile.health.HealthCheckResponse;

/**
 *
 * @author carlos
 */
public class ResponseHealth {

    private TypeStatus status;

    private Set<HealthCheckResponse> checks;

    public ResponseHealth() {
        this.status = TypeStatus.UP;
        this.checks = new HashSet();
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    public Set<HealthCheckResponse> getChecks() {
        return checks;
    }

    public void setChecks(Set<HealthCheckResponse> checks) {
        this.checks = checks;
    }

    public void addCheck(HealthCheckResponse checkResponse) {
        this.checks.add(checkResponse);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.status);
        hash = 53 * hash + Objects.hashCode(this.checks);
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
        final ResponseHealth other = (ResponseHealth) obj;
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.checks, other.checks)) {
            return false;
        }
        return true;
    }

}
