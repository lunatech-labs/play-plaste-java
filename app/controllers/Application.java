package controllers;

import backend.PlasteBot;
import backend.Pygments;
import models.Paste;
import play.mvc.Controller;
import play.mvc.Router;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application extends Controller {
	public static void gotoIndex() {
		index();
	}

	public static void index() {
		List<Paste> pastes = Paste.find("order by pastedAt desc").fetch();
		render(pastes);
	}

	public static void show(Long id) {
		final Paste paste = Paste.findById(id);
		final String highlightedCode = Pygments.highlight(paste.code, paste.codeMimeType);
		render(paste, highlightedCode);
	}

	public static void newPaste() {
		final List<String> nicks = PlasteBot.getInstance().getNicks();
		final Map<String,String> lexers = Pygments.lexers();
		render(nicks, lexers);
	}

	public static void post(final String title, final String code,
								final String codeMimeType,
								final byte[] attachment,
								final String attachmentMimeType,
								final String pastedByNick,
								final String pastedForNick) {
		final Paste paste = new Paste(title, code, codeMimeType, attachment,
			attachmentMimeType, pastedByNick, pastedForNick);
		paste.save();

		// Send notification to IRC
		final HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("id", paste.id);
		PlasteBot.getInstance().sendMessage(paste.getLongDescription() + " at " +
			Router.getFullUrl("Application.show", args));

		show(paste.id);
	}
}
