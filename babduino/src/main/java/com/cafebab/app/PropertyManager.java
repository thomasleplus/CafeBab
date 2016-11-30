/*
 * Babduino - Arduino project to detect usage of a table football.
 * Copyright (C) 2016 Thomas Leplus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
