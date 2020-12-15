package commands;

import java.io.IOException;

import botter.tests.CleverParser;
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

public class ChatterbotListener extends ListenerAdapter {
	public boolean komplettstumm = false;
	private CleverParser r = new CleverParser();
	private String answer;
	private boolean gibkomentar = true;
	private boolean bot;
	private boolean botunterhaltung;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		try {
			r.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String prefix = "<";
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		JDA jda = event.getJDA();
		long responseNumber = event.getResponseNumber();

		// Event specific information
		User autor = event.getAuthor();
		Message nachricht = event.getMessage();
		MessageChannel channel = event.getChannel();

		String msg = nachricht.getContentDisplay();

		 bot = autor.isBot();

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
		
		if(!bot) {
		if (!komplettstumm) {
			if(args[0].equals(prefix + "gibkommentar")) {
				
				event.getChannel().sendMessage(setkommentar()).queue();
			
			}
			if(gibkomentar) {
			
				event.getChannel().sendTyping().queue();
				try {
					answer = r.sendAI(msg);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				event.getChannel().sendMessage(answer).queue();
			}
			
			if (args[0].equals(prefix + "cl")) {
				
				msg = msg.substring(3);
				event.getChannel().sendTyping().queue();
				try {
					answer = r.sendAI(msg);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				event.getChannel().sendMessage(answer).queue();
			}
			
			
		}
	}
	}
	public void setkomplettmute(boolean b) {
		komplettstumm = b;
	}
	
	public String setkommentar() {
		if(gibkomentar == true) {
			gibkomentar =false;
			return "Jetzt gebe ich keine automatischen Kommentare mehr...";
		} else {
			gibkomentar = true;
			return "Jetzt gebe ich  automatischen Kommentare";
		
	}

}}
