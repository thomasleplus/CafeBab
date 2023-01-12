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

	public static void main(final String[] args) {
		Logger logger = null;
		try {
			final Properties props = new Properties();
			props.load(Main.class.getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(props);
			logger = Logger.getLogger("com.cafebab");
		} catch (final Exception e) {
			e.printStackTrace();
			return;
		}
		final QueueManager manager = new QueueManager();
		final RESTPublisher rest = new RESTPublisher(manager);
		rest.start();
		final TimeKeeper start = new TimeKeeper();
		final TimeKeeper end = new TimeKeeper();
		final TwitterPublisher twitter = new TwitterPublisher(start, end);
		twitter.start();
		XBeeReader reader;
		try {
			reader = new XBeeReader();
		} catch (final XBeeException e) {
			logger.error(e);
			return;
		}
		while (true) {
			final Measure measure = reader.readMeasure();
			manager.publish(measure);
			if (measure.getSensor() == KNOCK) {
				final Date last = end.get();
				end.set(measure.getDate());
				if (measure.getDate().getTime() - last.getTime() > INTERVAL) {
					start.set(measure.getDate());
				}
			}
		}

	}

}
