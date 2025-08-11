package com.cafebab.app;

import com.cafebab.bo.Measure;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;

public class RESTPublisher implements Runnable {

  private static final String CAFEBAB_URL = PropertyManager.get("CAFEBAB_URL");

  private final Logger logger = Logger.getLogger("com.cafebab");

  private final QueueManager manager;

  public RESTPublisher(final QueueManager manager) {
    this.manager = manager;
    logger.info("RESTPublisher initialized");
  }

  @Override
  public void run() {
    while (true) {
      final Measure measure = manager.next();
      sendToCafeBab(measure);
    }
  }

  private void sendToCafeBab(final Measure measure) {
    try {
      final Client client = ClientBuilder.newClient();
      final WebTarget webResource = client.target(CAFEBAB_URL);
      final Form form =
          new Form()
              .param("sensor", Integer.toString(measure.getSensor()))
              .param("value", Integer.toString(measure.getValue()))
              .param("clock", Integer.toString(measure.getClock()))
              .param("date", Long.toString(measure.getDate().getTime() / 1000));
      logger.info("HTTP POST: " + CAFEBAB_URL + " " + form);
      final Response response =
          webResource
              .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
              .accept(MediaType.TEXT_PLAIN)
              .post(Entity.form(form));
      if (response.getStatus() == 200) {
        final String output = response.getEntity().toString();
        logger.debug("HTTP response: " + output);
      } else {
        logger.error("HTTP error: " + response.getStatus());
      }
    } catch (final Exception e) {
      logger.error(e);
    }
  }

  public void start() {
    new Thread(this).start();
    logger.info("RESTPublisher started");
  }
}
