package com.cafebab.app;

import java.util.Date;

import org.apache.log4j.Logger;

public class TimeKeeper {

	private final Logger logger = Logger.getLogger("com.cafebab");

	private final Object lock = new Object();

	private Date last = new Date();

	public TimeKeeper() {
		logger.info("TimeKeeper initialized");
	}

	public void set(Date date) {
		synchronized (lock) {
			last = date;
		}
		logger.info("last set: " + date);
	}

	public Date get() {
		synchronized (lock) {
			return last;
		}
	}

}
