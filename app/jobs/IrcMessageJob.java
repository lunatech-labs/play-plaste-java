package jobs;

import backend.PlasteBot;
import models.Paste;
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
		PlasteBot.getInstance().sendMessage(paste.getLongDescription() + " at " + url);
	}
}
