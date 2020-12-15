package commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeleteListener extends ListenerAdapter {
	public static String prefix = "<";
	public String id;
	public boolean komplettstumm = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		JDA jda = event.getJDA();
		long responseNumber = event.getResponseNumber();

		// Event specific information
		User autor = event.getAuthor();
		Message nachricht = event.getMessage();
		MessageChannel channel = event.getChannel();

		String msg = nachricht.getContentDisplay();

		boolean bot = autor.isBot();

		if (event.isFromType(ChannelType.TEXT)) {

			Guild guild = event.getGuild();
			TextChannel textChannel = event.getTextChannel();
			Member member = event.getMember();

			String name;
			if (nachricht.isWebhookMessage()) {
				name = autor.getName();
			} else {
				name = member.getEffectiveName();
			}

			// System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(),
			// textChannel.getName(), name, msg);
		} else if (event.isFromType(ChannelType.PRIVATE)) {

			PrivateChannel privateChannel = event.getPrivateChannel();

			// System.out.printf("[PRIV]<%s>: %s\n", autor.getName(), msg);
		}
		if (args[0].equals(prefix + "k")) {
			setkomplettmute(true);
		}
		if (args[0].equals(prefix + "u")) {
			setkomplettmute(false);
		}
		if (!komplettstumm) {
			if (args[0].equals(prefix + "d")) {
				if (args.length < 2) {
					channel.sendTyping().queue();
					channel.sendMessage("Bitte gib auch das zu Löschende Wort ein!⚖").queue();
					;

				} else if (args.length > 2) {
					channel.sendTyping().queue();
					channel.sendMessage("Bitte gib nur EIN Wort zum löschen ein!").queue();
					;
				} else {
					String u = args[1];
					if (u.length() < 3) {
						channel.sendTyping().queue();
						channel.sendMessage(
								"Bitte gib ein längeres Wort ein, damit nich unnötog Sachen gelöscht werden: Wenn du nämlich \"l\" eingibts werden alle Nachrichten die \"l\" enthalten gelöscht!");

					} else {

						List<Message> r = event.getChannel().getHistory().retrievePast(100).complete();
						r.remove(0);

						int delcounter = 0;
						for (int q = 0; q < r.size(); q++) {

							getMessageID(r.get(q).toString());

							if (getMessage(r.get(q).toString(), args[1])) {
								event.getChannel().deleteMessageById(getMessageID(r.get(q).toString())).queueAfter(425,
										TimeUnit.MILLISECONDS);

								delcounter++;

							}

						}
						channel.sendTyping().queue();

						channel.sendMessage("Du hast " + delcounter + " von 100 Nachrichten erflogreich gelöscht!")
								.queue();
					}
				}
			}
		}
	}

	public boolean getMessage(String wors, String search) {
		wors = wors.toLowerCase();
		search = search.toLowerCase();

		return wors.contains(search);

	}

	public String getMessageID(String s) {
		String sr = s;

		boolean b = false;
		for (int i = 0; i < s.length(); i++) {
			if (!b) {
				if (sr.charAt(0) == '(') {
					sr = sr.substring(1);
					StringBuffer sb = new StringBuffer(sr);
					sb.deleteCharAt(sb.length() - 1);
					sr = sb.toString();

					b = true;
				} else {
					sr = sr.substring(1);
				}

			}
		}
		id = sr;
		return sr;

	}

	public void setkomplettmute(boolean b) {
		komplettstumm = b;
	}

}
