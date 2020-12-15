package commands;

import java.util.List;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class KickListener extends ListenerAdapter {
	public static String prefix = "<";
	public boolean komplettstumm = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String prefix = "<";
		String[] args = event.getMessage().getContentRaw().split("\\s+");

	
		Message nachricht = event.getMessage();
		MessageChannel channel = event.getChannel();

		String msg = nachricht.getContentDisplay();

		
		
		if (args[0].equals(prefix + "k")) {//the command is: <k
			setkomplettmute();
		}
		if (!komplettstumm) {
			if (msg.startsWith(prefix + "kick")) {

				if (nachricht.isFromType(ChannelType.TEXT)) {

					if (nachricht.getMentionedUsers().isEmpty()) {
						channel.sendMessage("You must mention 1 or more Users to be kicked!").queue();
					} else {
						Guild guild = event.getGuild();
						Member selfMember = guild.getSelfMember();

						if (!selfMember.hasPermission(Permission.KICK_MEMBERS)) {
							channel.sendMessage("Sorry! I don't have permission to kick members in this Guild!")
									.queue();
							return;
						}

						List<User> mentionedUsers = nachricht.getMentionedUsers();
						for (User user : mentionedUsers) {
							Member member = guild.getMember(user);
							if (!selfMember.canInteract(member)) {

								channel.sendMessage("Cannot kick member: ").append(member.getEffectiveName())
										.append(", they are higher in the hierarchy than I am!").queue();
								continue;
							}

							guild.kick(member).queue(success -> channel.sendMessage("Kicked ")
									.append(member.getEffectiveName()).append("! Cya!").queue(), error -> {

										if (error instanceof PermissionException) {
											PermissionException pe = (PermissionException) error;
											Permission missingPermission = pe.getPermission();

											channel.sendMessage("PermissionError kicking [")
													.append(member.getEffectiveName()).append("]: ")
													.append(error.getMessage()).queue();
										} else {
											channel.sendMessage("Unknown error while kicking [")
													.append(member.getEffectiveName()).append("]: <")
													.append(error.getClass().getSimpleName()).append(">: ")
													.append(error.getMessage()).queue();
										}
									});
						}
					}
				} else {
					channel.sendMessage("This is a Guild-Only command!").queue();
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
