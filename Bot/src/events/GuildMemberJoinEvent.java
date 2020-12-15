package events;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoinEvent extends ListenerAdapter {
	public MessageReceivedEvent evente;
	public String[] Messages = { "Hallo [member]!", "Wie geht´s [member]?", "AAAAACHTUNG! [member] ist da!" };

	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Random rand = new Random();
		int number = rand.nextInt(Messages.length);
		EmbedBuilder join = new EmbedBuilder();
		join.setColor(0x66d8ff);
		join.setDescription(Messages[number].replace("[member]", evente.getMember().getAsMention()));
		evente.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
	}

}
