package commands;

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

public class PingListener extends ListenerAdapter {
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
			if (msg.equals(prefix + "ping")) {

				channel.sendTyping().queue();
				channel.sendMessage("pong").queue();
			}
		}
	}

	public void setkomplettmute(boolean b) {
		komplettstumm = b;
	}

}
