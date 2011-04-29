package backend;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;
import play.Logger;
import play.Play;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PlasteBot extends PircBot {
	private static PlasteBot instance = null;
	private String channel;

	private PlasteBot() throws IOException, IrcException {
		final String nick = Play.configuration.getProperty("irc.nick", "PlasteBot");
		final String server = Play.configuration.getProperty("irc.server", "irc.lunatech.com");
		this.channel = Play.configuration.getProperty("irc.channel", "#test");

		Logger.info("IRC bot connecting as [" + nick + "]");
		setName(nick);
		setAutoNickChange(true);
		setEncoding("UTF-8");
		connect(server);
		Logger.info("IRC bot connected to [" + server + "]");
		joinChannel(channel);
		Logger.info("IRC bot joined channel [" + channel + "]");
	}

	public static synchronized PlasteBot getInstance() {
		if (PlasteBot.instance == null)
			throw new IllegalStateException("PlasteBot instance requested before starting");

		return PlasteBot.instance;
	}

	public static synchronized void start() throws InterruptedException, IOException, IrcException {
		PlasteBot bot = PlasteBot.instance;

		if (bot != null) {
			if (bot.isConnected()) {
				Logger.info("Disconnecting IRC bot before reconnecting");
				bot.disconnect();
				Thread.sleep(1000);
				Logger.info("Disconnected");
			}
		}

		PlasteBot.instance = new PlasteBot();
	}

	public List<String> getNicks() {
		final List<String> nicks = new ArrayList<String>();
		final User[] users = getUsers(this.channel);

		if (users != null)
			for (User user : users)
				nicks.add(user.getNick());

		return nicks;
	}

	public void sendMessage(final String message) {
		sendMessage(this.channel, message);
	}
}
