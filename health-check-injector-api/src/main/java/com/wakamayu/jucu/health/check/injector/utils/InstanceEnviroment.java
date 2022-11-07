/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.wakamayu.jucu.health.check.injector.configure.ConfigureModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;

/**
 *
 * @author carlos
 */
@Singleton
public class InstanceEnviroment {

	private final static Logger LOGGER = Logger.getLogger(InstanceEnviroment.class.getName());

	private Properties properties = new Properties();

	private ConfigureModel configureModel = new ConfigureModel();

	private void load(String filePath, TypeConfig config) throws FileNotFoundException, IOException {
		if (filePath != null && !filePath.isEmpty()) {
			InputStream inputStream = new FileInputStream(fileConfig(filePath).toString());
			if (inputStream.available() > 0) {
				configure(inputStream);
			}
		}
	}

	public void configure(InputStream inputStream) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(inputStream);
		for (String name : properties.stringPropertyNames()) {
			if (name.indexOf("healthcheck") > -1) {
				this.properties.put(clearKey(name), properties.getProperty(name));
			}
		}
		configure(this.properties);
	}

	public void configure(Properties properties) throws IOException {
		if (!properties.isEmpty()) {
			JavaPropsFactory factory = new JavaPropsFactory();
			factory.setRootValueSeparator("_");
			JavaPropsMapper mapper = new JavaPropsMapper();
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			configureModel = mapper.readPropertiesAs(properties, ConfigureModel.class);
		}
	}

	public void configure(String filePath, TypeConfig config) {
		try {
			boolean checkValidFile = isValidFile(filePath);
			if (checkValidFile) {
				load(filePath, config);
			}
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
	}

	private String fileURL(String file) {
		String urlFile = "";
		String healthCheckConfig = System.getenv("HEALTH_CHECK_CONFIG");
		if (healthCheckConfig != null && !healthCheckConfig.isEmpty()) {
			urlFile = healthCheckConfig;
		} else if (file != null && !file.isEmpty() && file.indexOf("/META-INF/") == 1) {
			urlFile = InstanceEnviroment.class.getResource(Paths.get(file).normalize().toString()).getFile();
		} else if (file != null && !file.isEmpty()) {
			urlFile = Paths.get(file).normalize().toString();
		}
		LOGGER.log(Level.INFO, "url file configure : "+urlFile);
		return urlFile;
	}

	private Path fileConfig(String urlFile) throws FileNotFoundException {
		String filePath = fileURL(urlFile);
		if (filePath != null && !filePath.isEmpty()) {
			return Paths.get(filePath).normalize();
		}
		throw new FileNotFoundException("file health configuration not found : ".concat(filePath));
	}

	public boolean isValidFile(String uriFile) throws FileNotFoundException {
		return Files.exists(fileConfig(uriFile));
	}

	private String clearKey(String key) {
		return key.replaceAll("[^a-zA-Z0-9]", ".");
	}

	public boolean isEmpty() {
		return configureModel.getHealthcheck() == null;
	}

	public ConfigureModel getConfigureModel() {
		return configureModel;
	}

}
