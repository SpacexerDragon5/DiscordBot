package commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BListener extends ListenerAdapter {
	public static String prefix = "<";//the prefix in front of every command
	public boolean komplettstumm = false;//boolean for mute

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {//when a message received do this:
		String[] args = event.getMessage().getContentRaw().split("\\s+");// to split the message in each argument

	
	

		// Event specific information
		
		Message nachricht = event.getMessage();
		

		String msg = nachricht.getContentDisplay();

		

		if (args[0].equals(prefix + "k")) {//the command is: <k
			setkomplettmute();
		}
		if (!komplettstumm) {

			if (msg.equals(prefix + "info b")) {//the info for math
				EmbedBuilder infob = new EmbedBuilder();

				infob.setTitle("Botter help");//the math syntax
				infob.setDescription("💻 Dieser Bot kann rechnen! KEINE LEERZEICHN!");//no spaces between math operations
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

				event.getChannel().sendMessage(infob.build()).queue();//send Message

				infob.clear();
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
