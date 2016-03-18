package com.cafebab.app;

import java.util.Properties;

public class PropertyManager {

	private static final Properties props = new Properties();

	static {
		try {
			props.load(PropertyManager.class
					.getResourceAsStream("/babduino.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PropertyManager() {
		// Singleton
	}

	public static String get(String key) {
		synchronized (props) {
			return props.getProperty(key);
		}
	}

	public static int getInt(String key) {
		return Integer.parseInt(get(key));
	}

	public static long getLong(String key) {
		return Long.parseLong(get(key));
	}

}
