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

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cafebab.bo.Measure;
import com.rapplogic.xbee.api.XBeeException;

public class Main {

	private static final int KNOCK = PropertyManager.getInt("KNOCK");

	private static final long INTERVAL = PropertyManager.getLong("INTERVAL");

	public static void main(String[] args) {
		Logger logger = null;
		try {
			Properties props = new Properties();
			props.load(Main.class.getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(props);
			logger = Logger.getLogger("com.cafebab");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		QueueManager manager = new QueueManager();
		RESTPublisher rest = new RESTPublisher(manager);
		rest.start();
		TimeKeeper start = new TimeKeeper();
		TimeKeeper end = new TimeKeeper();
		TwitterPublisher twitter = new TwitterPublisher(start, end);
		twitter.start();
		XBeeReader reader;
		try {
			reader = new XBeeReader();
		} catch (XBeeException e) {
			logger.error(e);
			return;
		}
		while (true) {
			Measure measure = reader.readMeasure();
			manager.publish(measure);
			if (measure.getSensor() == KNOCK) {
				Date last = end.get();
				end.set(measure.getDate());
				if (measure.getDate().getTime() - last.getTime() > INTERVAL) {
					start.set(measure.getDate());
				}
			}
		}

	}

}
