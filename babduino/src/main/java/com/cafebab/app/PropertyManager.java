package com.cafebab.app;

import java.util.Properties;

/** Loads {@code babduino.properties} once and exposes its values as typed lookups. */
public class PropertyManager {

  private static final Properties props = new Properties();

  static {
    try {
      props.load(PropertyManager.class.getResourceAsStream("/babduino.properties"));
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  private PropertyManager() {
    // Singleton
  }

  public static String get(final String key) {
    synchronized (props) {
      return props.getProperty(key);
    }
  }

  public static int getInt(final String key) throws NumberFormatException {
    return Integer.parseInt(get(key));
  }

  public static long getLong(final String key) throws NumberFormatException {
    return Long.parseLong(get(key));
  }
}
