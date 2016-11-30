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
