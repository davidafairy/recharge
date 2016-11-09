package com.redstoneinfo.platform.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

/**
 * @author david
 * 
 */
public class Config {

	private static Config instance = null;

	private Properties properties = null;

	private Config() {
		init();
	}

	public static Config getInstance() {

		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	public void init() {

		try {
			InputStream is = ServletActionContext.getServletContext()
					.getResourceAsStream("/WEB-INF/config/config.properties");
			properties = new Properties();
			properties.load(is);

		} catch (Exception e) {
			throw new RuntimeException("Failed to get properties!");
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
}
