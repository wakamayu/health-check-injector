/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.health.HealthCheckResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;

/**
 *
 * @author carlos
 */
public class ResponseHealth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TypeStatus status;

	private List<HealthCheckResponse> checks;

	public ResponseHealth() {
		this.status = TypeStatus.UP;
		this.checks = new ArrayList();
	}

	@JsonIgnore
	public void addCheck(HealthCheckResponse checkResponse) {
		this.checks.add(checkResponse);
	}

	public TypeStatus getStatus() {
		return status;
	}

	public void setStatus(TypeStatus status) {
		this.status = status;
	}

	public List<HealthCheckResponse> getChecks() {
		return checks;
	}

	public void setChecks(List<HealthCheckResponse> checks) {
		this.checks = checks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checks == null) ? 0 : checks.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseHealth other = (ResponseHealth) obj;
		if (checks == null) {
			if (other.checks != null)
				return false;
		} else if (!checks.equals(other.checks))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseHealth [status=" + status + ", checks=" + checks + "]";
	}

}
