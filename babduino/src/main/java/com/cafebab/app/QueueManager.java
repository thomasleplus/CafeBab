package com.cafebab.app;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.cafebab.bo.Measure;

public class QueueManager {

	private final Logger logger = Logger.getLogger("com.cafebab");

	private LinkedBlockingQueue<Measure> queue = new LinkedBlockingQueue<Measure>();

	public QueueManager() {
		logger.info("Queue initialized");
	}

	public void publish(Measure measure) {
		queue.add(measure);
		logger.debug("queue add: " + measure);
	}

	public Measure next() {
		Measure measure = null;
		do {
			try {
				measure = queue.take();
				logger.debug("queue take: " + measure);
			} catch (InterruptedException e) {
			}
		} while (measure == null);
		return measure;
	}

}
