package com.cafebab.app;

import com.cafebab.bo.Measure;
import com.rapplogic.xbee.api.XBeeException;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/** Entry point: wires the XBee reader, queue and publishers, then runs the measurement loop. */
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
      // A KNOCK marks table activity: advance the "end" time; if the table had
      // been idle longer than INTERVAL, this knock also starts a new session.
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
