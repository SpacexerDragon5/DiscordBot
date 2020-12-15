package commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingListener extends ListenerAdapter {
	public static String prefix = "<";
	public boolean komplettstumm = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		// Event specific information
		
		Message nachricht = event.getMessage();
		MessageChannel channel = event.getChannel();
		
		String msg = nachricht.getContentDisplay();

		

		if (args[0].equals(prefix + "k")) {
			setkomplettmute();
		}
		if (!komplettstumm) {
			if (msg.equals(prefix + "ping")) {
				
				channel.sendTyping().queue();
				channel.sendMessage("pong").queue();
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
