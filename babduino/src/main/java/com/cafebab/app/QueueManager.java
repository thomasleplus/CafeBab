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
