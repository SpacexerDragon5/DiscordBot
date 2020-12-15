package commands;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

public class RollListener extends ListenerAdapter {
	public boolean komplettstumm = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String prefix = "<";
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
			channel.sendMessage("Stumm komplett... ... ich bin traurig").queue();
		}
		if (args[0].equals(prefix + "u")) {
			setkomplettmute(false);
			channel.sendMessage("Stumm komplett aufgehoben.. ... ich bin froh").queue();
		}
		if (!komplettstumm) {
			if (msg.equals(prefix + "roll")) {

				Random rand = ThreadLocalRandom.current();
				int roll = rand.nextInt(6) + 1; // This results in 1 - 6 (instead of 0 - 5)
				channel.sendMessage("Your roll: " + roll).flatMap((v) -> roll < 3, sentMessage -> channel.sendMessage(
						"The roll for messageId: " + sentMessage.getId() + " wasn't very good... Must be bad luck!\n"))
						.queue();
			}
		}

	}

	public void setkomplettmute(boolean b) {
		komplettstumm = b;
	}

}
