package commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BlockListener extends ListenerAdapter {
	public static String prefix = "<";
	public boolean komplettstumm = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

MessageChannel channel = event.getChannel();
Message message = event.getMessage();
String msg = message.getContentDisplay();
		
		if (args[0].equals(prefix + "k")) {
			setkomplettmute();
		}
		
		if (!komplettstumm) {
			if (msg.equals(prefix + "block")) {

				try {

					Message sentMessage = channel.sendMessage("I blocked and will return the nachricht!").complete();

					channel
							.sendMessage("I expect rate limitation and know how to handle it!").complete(false);

					System.out.println("Sent a nachricht using blocking! Luckly I didn't get Ratelimited... MessageId: "
							+ sentMessage.getId());
				} catch (RateLimitedException e) {
					System.out.println(
							"Whoops! Got ratelimited when attempting to use a .complete() on a RestAction! RetryAfter: "
									+ e.getRetryAfter());
				}

				catch (RuntimeException e) {
					System.out.println(
							"Unfortunately something went wrong when we tried to send the Message and .complete() threw an Exception.");
					e.printStackTrace();
				}
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
