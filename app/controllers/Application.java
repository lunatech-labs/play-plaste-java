package controllers;

import backend.PlasteBot;
import models.Paste;
import play.Play;
import play.mvc.Controller;
import play.mvc.Router;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

public class Application extends Controller {
	public static void gotoIndex() {
		index();
	}

    public static void index() {
		List<Paste> pastes = Paste.all().fetch();
        render(pastes);
    }

	public static void show(Long id) {
		Paste paste = Paste.findById(id);
		render(paste);
	}

	public static void newPaste() {
		final List<String> nicks = PlasteBot.getInstance().getNicks();
		render(nicks);
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
