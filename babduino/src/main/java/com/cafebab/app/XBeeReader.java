package com.cafebab.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.cafebab.bo.Measure;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;

public class XBeeReader {

	private static final String PORT = PropertyManager.get("PORT");
	private static final int BAUDS = PropertyManager.getInt("BAUDS");

	private final XBee xbee;

	private final Logger logger = Logger.getLogger("com.cafebab");

	public XBeeReader() throws XBeeException {
		xbee = new XBee();
		xbee.open(PORT, BAUDS);
		logger.info("XBee initialized");
	}

	public Measure readMeasure() {
		while (true) {
			try {
				final XBeeResponse response = xbee.getResponse();
				if (response == null) {
					continue;
				}
				final int[] bytes = response.getProcessedPacketBytes();
				final StringBuilder packet = new StringBuilder("packet: ");
				for (final int b : bytes) {
					if (b >= 0 && b <= 15) {
						packet.append(0);
					}
					packet.append(Integer.toHexString(0xFF & b));
				}
				logger.debug(packet.toString());
				final StringBuilder sb = new StringBuilder();
				for (int i = 6; i < bytes.length - 3; i++) {
					final char c = (char) bytes[i];
					if (c >= '0' && c <= '9' || c == ',') {
						sb.append(c);
					}

				}
				final String line = sb.toString();
				logger.info("data: " + line);
				final Measure measure = new Measure();
				final String[] parts = line.split(",");
				measure.setSensor(Integer.parseInt(clean(parts[0])));
				measure.setValue(Integer.parseInt(clean(parts[1])));
				measure.setClock(Integer.parseInt(clean(parts[2])));
				measure.setDate(new Date());
				return measure;
			} catch (final Exception e) {
				logger.error(e);
			}
		}
	}

	public String clean(final String s) {
		return s.replaceAll("[\\D]", "");
	}

}
