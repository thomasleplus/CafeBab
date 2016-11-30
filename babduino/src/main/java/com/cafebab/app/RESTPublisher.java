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

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.cafebab.bo.Measure;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RESTPublisher implements Runnable {

	private static final String FEED_TAG = "<FEED>";
	private static final String STREAM_TAG = "<STREAM>";
	
	private final static String CAFEBAB_URL = PropertyManager.get("CAFEBAB_URL");
    private final static String COSM_URL = PropertyManager.get("COSM_URL");
    private final static String COSM_FEED = PropertyManager.get("COSM_FEED");
    private final static String COSM_API_KEY = PropertyManager.get("COSM_API_KEY");
    private final static String THINGSPEAK_URL = PropertyManager.get("THINGSPEAK_URL");
    private final static String THINGSPEAK_API_KEY = PropertyManager.get("THINGSPEAK_API_KEY");

	private final Logger logger = Logger.getLogger("com.cafebab");

	private final QueueManager manager;

	public RESTPublisher(QueueManager manager) {
		this.manager = manager;
		logger.info("RESTPublisher initialized");
	}

	public void run() {
		while (true) {
			Measure measure = manager.next();
			sendToCafeBab(measure);
            sendToCosm(measure);
            //sendToThingSpeak(measure);
		}
	}

	private void sendToCafeBab(Measure measure) {
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(CAFEBAB_URL);
			String input = "sensor=" + measure.getSensor() + "&value="
					+ measure.getValue() + "&clock=" + measure.getClock()
					+ "&date=" + (int) (measure.getDate().getTime() / 1000);
			logger.info("HTTP POST: " + CAFEBAB_URL + " " + input);
			ClientResponse response = webResource.type(
					"application/x-www-form-urlencoded").post(
					ClientResponse.class, input);
			if (response.getStatus() == 200) {
				String output = response.getEntity(String.class);
				logger.debug("HTTP response: " + output);
			} else {
				logger.error("HTTP error: " + response.getStatus());
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

    private void sendToCosm(Measure measure) {
        try {
            Client client = Client.create();
            String url = COSM_URL
                    .replaceAll(FEED_TAG, COSM_FEED)
                    .replaceAll(STREAM_TAG, Integer.toString(measure.getSensor()));
            WebResource webResource = client.resource(url);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String input = "{\"datapoints\":[{\"at\":\"" + sdf.format(measure.getDate()) + "\",\"value\":\"" + measure.getValue() + "\"}]}";
            logger.info("HTTP POST: " + url + " " + input);
            ClientResponse response = webResource.type("application/json")
                    .header("X-ApiKey", COSM_API_KEY)
                    .post(ClientResponse.class, input);
            if (response.getStatus() == 200) {
                String output = response.getEntity(String.class);
                logger.debug("HTTP response: " + output);
            } else {
                logger.error("HTTP error: " + response.getStatus());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void sendToThingSpeak(Measure measure) {
        try {
            Client client = Client.create();
            String url = THINGSPEAK_URL;
            WebResource webResource = client.resource(url);
            String input = "field" + (measure.getSensor() == 255 ? 2 : 1) + "=" + measure.getValue();
            logger.info("HTTP POST: " + url + " " + input);
            ClientResponse response = webResource.type("application/x-www-form-urlencoded")
                    .header("X-THINGSPEAKAPIKEY", THINGSPEAK_API_KEY)
                    .post(ClientResponse.class, input);
            if (response.getStatus() == 200) {
                String output = response.getEntity(String.class);
                logger.debug("HTTP response: " + output);
            } else {
                logger.error("HTTP error: " + response.getStatus());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

	public void start() {
		new Thread(this).start();
		logger.info("RESTPublisher started");
	}

}
