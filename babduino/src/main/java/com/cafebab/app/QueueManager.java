package com.cafebab.app;

import com.cafebab.bo.Measure;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;

public class QueueManager {

  private final Logger logger = Logger.getLogger("com.cafebab");

  private final LinkedBlockingQueue<Measure> queue = new LinkedBlockingQueue<>();

  public QueueManager() {
    logger.info("Queue initialized");
  }

  public void publish(final Measure measure) {
    queue.add(measure);
    logger.debug("queue add: " + measure);
  }

  public Measure next() {
    Measure measure = null;
    do {
      try {
        measure = queue.take();
        logger.debug("queue take: " + measure);
      } catch (final InterruptedException e) {
      }
    } while (measure == null);
    return measure;
  }
}
