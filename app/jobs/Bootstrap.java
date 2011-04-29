package jobs;

import backend.PlasteBot;
import models.Paste;
import org.jibble.pircbot.IrcException;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import java.io.IOException;

@OnApplicationStart
public class Bootstrap extends Job {
	@Override
	public void doJob() {
		try {
			PlasteBot.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
