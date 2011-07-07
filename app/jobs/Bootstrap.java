package jobs;

import backend.PlasteBot;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Bootstrap extends Job<Object> {
	@Override
	public void doJob() {
		if (Play.configuration.getProperty("irc.server") != null &&
				Play.configuration.getProperty("irc.channel") != null &&
				Play.configuration.getProperty("irc.nick") != null)
			try {
				PlasteBot.start();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		else Logger.info("irc configuration not set, skipping PlasteBot initialization");
	}
}
