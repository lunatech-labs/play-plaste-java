package jobs;

import backend.PlasteBot;
import models.Paste;
import play.Play;
import play.jobs.Job;
import play.mvc.Router;

import java.util.HashMap;

public class IrcMessageJob extends Job<Object> {
	private Paste paste;
	private String url;

	public IrcMessageJob(final Paste paste, final String url) {
		this.paste = paste;
		this.url = url;
	}

	public static void sendMessage(final Paste paste, final String url) {// Send notification to IRC
		final IrcMessageJob job = new IrcMessageJob(paste, url);
		job.now();
	}

	@Override
	public void doJob() throws Exception {
		if (Play.configuration.getProperty("irc.server") != null &&
				Play.configuration.getProperty("irc.channel") != null &&
				Play.configuration.getProperty("irc.nick") != null)
			PlasteBot.getInstance().sendMessage(paste.getLongDescription() + " at " + url);
	}
}
