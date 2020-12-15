package commands;

import java.io.IOException;

import cleverbot.CleverParser;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatterbotListener extends ListenerAdapter  implements Runnable{
	public volatile boolean   komplettstumm = false;
	public volatile CleverParser r = new CleverParser();
	public volatile String answer;
	public volatile boolean gibkomentar = true;
	public volatile boolean bot;
	public volatile boolean init = true;
	public volatile String msg;
public	boolean gotanswer = false;

	
	Thread t = new Thread();

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(init) {
		try {
			
			r.init();
			init = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	
		String prefix = "<";
		String[] args = event.getMessage().getContentRaw().split("\\s+");

	
		// Event specific information
		User autor = event.getAuthor();
		Message nachricht = event.getMessage();
		
		msg = nachricht.getContentDisplay();

		 bot = autor.isBot();

	
		
		if(!bot) {
			if (args[0].equals(prefix + "k")) {
				setkomplettmute();
			}
		if (!komplettstumm) {
			if(args[0].equals(prefix + "gibkommentar")) {
				
				event.getChannel().sendMessage(setkommentar()).queue();
			
			}
			if(gibkomentar) {
			
				event.getChannel().sendTyping().queue();
				run();
				while(!gotanswer);
					gotanswer=false;
				event.getChannel().sendMessage(answer).queue();
				
			}
			
			if (args[0].equals(prefix + "cl")) {
				
				msg = msg.substring(3);
				event.getChannel().sendTyping().queue();
				run();
			while(!gotanswer);
				gotanswer=false;
				event.getChannel().sendMessage(answer).queue();
			
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
	public String setkommentar() {
		if(gibkomentar == true) {
			gibkomentar =false;
			return "Jetzt gebe ich keine automatischen Kommentare mehr...";
		} else {
			gibkomentar = true;
			return "Jetzt gebe ich  automatischen Kommentare";
		
	}

}
	@Override
	public void run() {
		try {
			
			answer = r.sendAI(msg);
			gotanswer=true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}}

