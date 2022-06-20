/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.api;

import com.wakamayu.jucu.health.check.injector.configure.HealthCheckModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeConfig;
import com.wakamayu.jucu.health.check.injector.interfaces.FactoryConfigure;
import com.wakamayu.jucu.health.check.injector.utils.InstanceEnviroment;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;

/**
 *
 * @author carlos
 */
@Singleton
@Named("FACTORY-CONFIGURE")
public class FactoryConfigureImpl implements FactoryConfigure {

	private static final String URI_FILE = "/META-INF/healthcheck-config.properties";

	private String uriFile;

	private TypeConfig typeConfig;

	@Inject
	private InstanceEnviroment instanceEnviroment;

	public FactoryConfigureImpl() {
		this.uriFile = URI_FILE;
		this.typeConfig = TypeConfig.PROPERTIES;
	}

	private void configureEnviroment() throws IOException {
		boolean checkFile = instanceEnviroment.isValidFile(uriFile);
		if (checkFile) {
			instanceEnviroment.configure(uriFile, typeConfig);
		}
	}

	@Override
	public boolean isValid() {
		return instanceEnviroment != null && instanceEnviroment.isValidFile(uriFile) && !instanceEnviroment.isEmpty();
	}

	@Override
	public void configure(String uriFile, TypeConfig typeConfig) {
		if (instanceEnviroment.isValidFile(uriFile)) {
			setUriFile(uriFile);
			setTypeConfig(typeConfig);

		}
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public void setTypeConfig(TypeConfig typeConfig) {
		this.typeConfig = typeConfig;
	}

	@Override
	public HealthCheckModel getHealthCheckModel() {
		return instanceEnviroment.getConfigureModel().getHealthcheck();
	}

	@Override
	public void build() {
		try {
			if (instanceEnviroment.isValidFile(uriFile)) {
				configureEnviroment();
			}
		} catch (IOException ex) {
			Logger.getLogger(FactoryConfigureImpl.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public String toString() {
		return "FactoryConfigureImpl [uriFile=" + uriFile + ", typeConfig=" + typeConfig + "]";
	}

}
