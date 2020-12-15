package commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
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

public class BListener extends ListenerAdapter {
	public static String prefix = "<";
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

			if (msg.equals(prefix + "info b")) {
				EmbedBuilder infob = new EmbedBuilder();

				infob.setTitle("Botter help");
				infob.setDescription("💻 Dieser Bot kann rechnen! KEINE LEERZEICHN!");
				infob.addField("*", "	Multiplizeirt", true);
				infob.addField("/", "	Dividiert", false);
				infob.addField("-", "	subtrahiert", true);
				infob.addField("+", "	addiert", false);
				infob.addField("sin", "	nimmt den Sinus von der Nachfolgenden Zahl", false);
				infob.addField("cos", "	nimmt den Cosinus von der Nachfolgenden Zahl", false);
				infob.addField("tan", "	nimmt den Tangens von der Nachfolgenden Zahl", false);
				infob.addField("^", "	quadriert", true);
				infob.addField("sqrt", "	nimmt die Wurzel von der Nachfolgenden Zahl", false);
				infob.addField("log", "	nimmt den Logarithmus", false);
				infob.addField("sinh", "	nimmt den Sinus hoch -1 von der Nachfolgenden Zahl", false);
				infob.addField("cosh", "	nimmt den Cosinus hoch -1 von der Nachfolgenden Zahl", false);
				infob.addField("tanh", "	nimmt den Tangens hoch -1 von der Nachfolgenden Zahl", false);
				infob.addField("logz", "	nimmt den 10. Logarithmus einer Zahl", false);
				infob.setColor(Color.red);

				event.getChannel().sendTyping().queue();

				event.getChannel().sendMessage(infob.build()).queue();

				infob.clear();
			}
		}
	}

	public void setkomplettmute(boolean b) {
		komplettstumm = b;
	}
}
