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

		

		// Event specific information
	
		MessageChannel channel = event.getChannel();

	

		
		if (args[0].equals(prefix + "k")) {
			setkomplettmute(true);
		}
		if (args[0].equals(prefix + "u")) {
			setkomplettmute(false);
		}
		if (!komplettstumm) {
			if (args[0].equals(prefix + "d")) {
				if (args.length < 2) {//the amount of arguments should not be less than 1
					channel.sendTyping().queue();
					channel.sendMessage("Bitte gib auch das zu Löschende Wort ein!⚖").queue();
					;

				} else if (args.length > 2) {//the amount of arguments should not be more than 1
					channel.sendTyping().queue();
					channel.sendMessage("Bitte gib nur EIN Wort zum löschen ein!").queue();
					;
				} else {
					String u = args[1];//the word to delete
					if (u.length() < 3) {//the word should contain more than 2 chars, so you can´t delete all messages because they contain the letter 'e' for example
						channel.sendTyping().queue();
						channel.sendMessage(
								"Bitte gib ein längeres Wort ein, damit nich unnötog Sachen gelöscht werden: Wenn du nämlich \"l\" eingibts werden alle Nachrichten die \"l\" enthalten gelöscht!");

					} else {

						List<Message> r = event.getChannel().getHistory().retrievePast(100).complete();//get the last 100 messages
						r.remove(0);//remove the first, so you don´t delete the command for deleting

						int delcounter = 0;
						for (int q = 0; q < r.size(); q++) {//iteration for every message

							getMessageID(r.get(q).toString());//the message ID of the current message

							if (getMessage(r.get(q).toString(), args[1])) {//is true when the message contains the word to delete
								event.getChannel().deleteMessageById(getMessageID(r.get(q).toString())).queueAfter(425,
										TimeUnit.MILLISECONDS);//prevents to get blocked for acting to fast

								delcounter++;//counts how many Messages are deletet

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

	public boolean getMessage(String wors, String search) {//ignore case of letters and search in String so you can´t prevent to delete id by just adding another letter or smth.
		wors = wors.toLowerCase();
		search = search.toLowerCase();

		return wors.contains(search);

	}

	public String getMessageID(String s) {//this method exctraccts the ID of the Message, because you need to now the ID to delete it
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
