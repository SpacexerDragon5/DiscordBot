package Main;

import java.util.List;

import javax.security.auth.login.LoginException;


import commands.BListener;
import commands.ChatterbotListener;
import commands.DeleteListener;
import commands.InfoListener;
import commands.MathListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class MAIN {
	public static String prefixe = "<";
	public static List Users;
	
	public static void main(String[] args) {

		try {
			token token = new token();
			
			JDA jda = JDABuilder.createDefault(token.token)//token 
					.addEventListeners(new InfoListener(), 
						 new MathListener(),
							new BListener(), new DeleteListener(), new ChatterbotListener()/*, new ChatterbotListener()*/) // An instance of a class that
																	// will handle events.
					.build();
			jda.awaitReady();
			
			
		
			
			Users = jda.getUsers();
			jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("für dich"));
					
			System.out.println(Users);
			System.out.println("Finished Building JDA!");
		
			// Blocking guarantees that JDA will be completely loaded.
		} catch (LoginException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}
	

}
