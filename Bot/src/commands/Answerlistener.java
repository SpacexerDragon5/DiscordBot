package commands;

import java.util.Random;

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

public class Answerlistener extends ListenerAdapter {
	public static String prefix = "<";
	public static String[] edans = { "Spielt ihr schon wieder ED?", "Ich liebe SLF!👨‍✈️",
			"Wo seit ihr? Ich bin in Matet!", "Lasst ihr mich mitspielen?", "Welcher Rang seit ihr? Ich bin Experte!",
			"Habe gerade eine Handelsroute gefunden 10MIO gewinn 2Hops!",
			"Spielt ihr morgen auch wieder? Ich würde mich sooo freuen!", "Wie läuft es so in der Staffel?",
			"Man kann den FSA Unterbrechern echt gut ausweichen!", "DIESE THARGOIDEN!☣👽" };
	public static String[] mcans = { "Minecraft ist schön, oder?", "Spielt ihr auf Fraktionen?",
			"Ist Friedrich auch da? Aber das bezweifle ich...", "Wie viel Erfahrung hast du?, Ich habe 10 Level.",
			"Stirbst du oft?", "Wie geht´s mit der schule weiter?", "In Minecraft kann man so kreativ sein!" };
	public static String[] memans = { "Warum nehmt ihr nicht mich?", "Ich bin VIEEL besser als Memer",
			"Ich bin jetzt beleidigt!", "Das ist unfair!", "Auch wenn ihr mich verachtet, ich vergebe euch!" };
	public boolean mute = false;
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
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Jetzt gebe gar keine Kommentare mehr!")
					.queue();
		}
		if (args[0].equals(prefix + "u")) {
			setkomplettmute(false);
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Jetzt gebe wieder Kommentare mehr!")
					.queue();
		}

		if (!komplettstumm) {
			if (args[0].equals(prefix + "m")) {
				setmute(true);
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Jetzt gebe ich keine Kommentare mehr zu MC, ED oder dem blöden Memer!")
						.queue();

			}
			if (args[0].equals(prefix + "l")) {
				setmute(false);
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Jetzt gebe ich wieder Kommentare mehr zu MC, ED oder dem blöden Memer!")
						.queue();

			}

			if (!mute) {
				for (int i = 0; i < args.length; i++) {

					if (args[i].equalsIgnoreCase("ed") || args[i].equalsIgnoreCase("elite")) {

						Random rand = new Random();
						int number = rand.nextInt(edans.length);
						event.getChannel().sendTyping().queue();
						event.getChannel().sendMessage(edans[number]).queue();

					}
					if (args[i].equalsIgnoreCase("minecraft") || args[i].equalsIgnoreCase("mc")) {
						Random rand = new Random();
						int number = rand.nextInt(mcans.length);
						event.getChannel().sendTyping().queue();
						event.getChannel().sendMessage(mcans[number]).queue();
					}
					if (args[i].equalsIgnoreCase("pls")) {
						Random rand = new Random();
						int number = rand.nextInt(memans.length);
						event.getChannel().sendTyping().queue();
						event.getChannel().sendMessage(memans[number]).queue();
					}

				}
			}
		}
	}

	public void setkomplettmute(boolean b) {
		komplettstumm = b;
	}

	public void setmute(boolean b) {
		mute = b;
	}
}
