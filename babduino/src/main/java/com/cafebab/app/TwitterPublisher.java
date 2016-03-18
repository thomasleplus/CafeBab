package com.cafebab.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TwitterPublisher implements Runnable {

	private static final long TWITTER_FREQUENCY = PropertyManager
			.getLong("TWITTER_FREQUENCY");
	private static final long INTERVAL = PropertyManager.getLong("INTERVAL");
	private static final String BABY_KO = PropertyManager.get("BABY_KO");
	private static final String BABY_OK = PropertyManager.get("BABY_OK");

	private final Logger logger = Logger.getLogger("com.cafebab");

	private final SimpleDateFormat sdf = new SimpleDateFormat(
			"HH:mm:ss dd/MM/yyyy", Locale.FRANCE);

	private final TimeKeeper start;
	private final TimeKeeper end;

	public TwitterPublisher(TimeKeeper start, TimeKeeper end) {
		this.start = start;
		this.end = end;
		logger.info("TwitterPublisher initialized");
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(TWITTER_FREQUENCY);
			} catch (InterruptedException e) {
			}
			try {
				Date startD = start.get();
				Date endD = end.get();
				Date now = new Date();
				long sinceEnd = now.getTime() - endD.getTime();
				long sinceStart = now.getTime() - startD.getTime();
				String tweet = null;
				if (sinceEnd > INTERVAL) {
					tweet = BABY_OK + convert(sinceEnd) + " ("
							+ sdf.format(endD) + ").";
				} else {
					tweet = BABY_KO + convert(sinceStart) + " ("
							+ sdf.format(startD) + ").";
				}
				Twitter twitter = new TwitterFactory().getInstance();
				twitter.updateStatus(tweet);
				logger.info("Twitter published: " + tweet);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public String convert(long diff) {
		StringBuffer sb = new StringBuffer();
		long days = diff / 86400000;
		diff -= days * 86400000;
		long hours = diff / 3600000;
		diff -= hours * 3600000;
		long mins = diff / 60000;
		diff -= mins * 60000;
		if (days > 0) {
			sb.append(" " + days + " jour" + (days > 1 ? "s" : ""));
		}
		if (hours > 0) {
			sb.append(" " + hours + " heure" + (hours > 1 ? "s" : ""));
		}
		sb.append(" " + mins + " minute" + (mins > 1 ? "s" : ""));
		return sb.toString();
	}

	public void start() {
		new Thread(this).start();
		logger.info("TwitterPublished started");
	}

}
