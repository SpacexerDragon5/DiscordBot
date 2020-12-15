package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.*;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class InfoListener extends ListenerAdapter {
	public static String prefix = "<";
	public boolean komplettstumm = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		
		// Event specific information
		User autor = event.getAuthor();
		Message nachricht = event.getMessage();
	

		String msg = nachricht.getContentDisplay();

	

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

			System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
		} else if (event.isFromType(ChannelType.PRIVATE)) {

		

			System.out.printf("[PRIV]<%s>: %s\n", autor.getName(), msg);
		}
		if (args[0].equals(prefix + "k")) {//the command is: <k
			setkomplettmute();
		}
		if (!komplettstumm) {
			if (msg.equals(prefix + "info")) {
				EmbedBuilder info = new EmbedBuilder();
				info.setTitle("Botter help");
				info.setDescription("👨🏿‍💻 Dieser Bot kann nichts!");
				info.addField("<info", "	💁‍♂️ Gib dir Informationen über diesen Bot", true);
				info.addField("<ping", "	👉 pingt dich!", true);
				info.addField("<b", "	berechnet  Terme", false);
				info.addField("info zu <b ", "sin, cos, tan, ^, +, -, *, /, sqrt", false);
				info.addField("<l",
						"Löscht aus den 100 vorangegangenen Nachrichten, die die das 2. Argument enthalten, mehr info: <info d",
						false);
				info.addField("<m", "	Schaltet Botter stumm", false);
				info.addField("<l", "	Entstummt Botter", false);
				info.addField("<k", "Botter gibt keine Kommentare mehr zu irgendetwas: an/aus", false);
				info.addField("<cl", "Ask Cleverbot something...", false);
				info.addField("<gibkommentar", "Aktiviert7 Deaktiviert automatisches antworten", false);
				info.setColor(Color.red);

				event.getChannel().sendTyping().queue();

				event.getChannel().sendMessage(info.build()).queue();

				info.clear();

			}
			
			if (msg.equals((prefix + "info d"))) {
				EmbedBuilder info = new EmbedBuilder();
				info.setTitle("Botter help");
				info.setDescription("Löschen Hilfe");
				info.addField("<d Arg",
						"	Löscht alle Nachrichten die Arg enthalten \n Ohne die GROẞ und kleinschreibung zu beachten",
						true);
				info.setColor(Color.red);

				event.getChannel().sendTyping().queue();

				event.getChannel().sendMessage(info.build()).queue();

				info.clear();
			}

		}
	}

	public void setkomplettmute() {//set mute or not
		if(komplettstumm==true) {
			komplettstumm = false;
		} else {
			komplettstumm=true;
		}
	}
}
