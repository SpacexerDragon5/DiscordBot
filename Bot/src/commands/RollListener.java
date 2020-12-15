package commands;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RollListener extends ListenerAdapter {
	public boolean komplettstumm = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String prefix = "<";
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		

		// Event specific information
		
		Message nachricht = event.getMessage();
		MessageChannel channel = event.getChannel();

		String msg = nachricht.getContentDisplay();

		
		if (args[0].equals(prefix + "k")) {
			setkomplettmute();
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

	public void setkomplettmute() {//set mute or not
		if(komplettstumm==true) {
			komplettstumm = false;
		} else {
			komplettstumm=true;
		}
	}
}
