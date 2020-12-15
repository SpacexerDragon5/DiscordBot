package commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MathListener extends ListenerAdapter {
	public static String prefix = "<";
	public boolean komplettstumm = false;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

	
		// Event specific information
		
		MessageChannel channel = event.getChannel();

	
		if (args[0].equals(prefix + "k")) {//the command is: <k
			setkomplettmute();
		}
		if (!komplettstumm) {
			if (args[0].equals(prefix + "b")) {
				if (args.length < 2) {
					channel.sendMessage("Bitte gib auch eine Rechnung ein!");

				} else if (args.length > 2) {
					channel.sendMessage("Bitte keine Leerzeichen zwischen den einzelnen Rechenschritten!");
				} else {
					channel.sendTyping().queue();

					String r = args[1];
					r = String.valueOf(eval(r));

					channel.sendMessage("Hier ist dein Ergebnis:⏳📊 ```" + r + "```").queue();
				}

			}
		}
	}

	public static double eval(final String str) {//all the math stuff
		return new Object() {
			int pos = -1, ch;

			void nextChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat) {
				while (ch == ' ')
					nextChar();
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			double parse() {
				nextChar();
				double x = parseExpression();
				if (pos < str.length())
					throw new RuntimeException("Unexpected: " + (char) ch);
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			// | number | functionName factor | factor `^` factor

			double parseExpression() {
				double x = parseTerm();
				for (;;) {
					if (eat('+'))
						x += parseTerm(); // addition
					else if (eat('-'))
						x -= parseTerm(); // subtraction
					else
						return x;
				}
			}

			double parseTerm() {
				double x = parseFactor();
				for (;;) {
					if (eat('*'))
						x *= parseFactor(); // multiplication
					else if (eat('/'))
						x /= parseFactor(); // division
					else
						return x;
				}
			}

			double parseFactor() {
				if (eat('+'))
					return parseFactor(); // unary plus
				if (eat('-'))
					return -parseFactor(); // unary minus

				double x;
				int startPos = this.pos;
				if (eat('(')) { // parentheses
					x = parseExpression();
					eat(')');
				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
					while ((ch >= '0' && ch <= '9') || ch == '.')
						nextChar();
					x = Double.parseDouble(str.substring(startPos, this.pos));
				} else if (ch >= 'a' && ch <= 'z') { // functions
					while (ch >= 'a' && ch <= 'z')
						nextChar();
					String func = str.substring(startPos, this.pos);
					x = parseFactor();
					if (func.equals("sqrt"))
						x = Math.sqrt(x);
					else if (func.equals("sin"))
						x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos"))
						x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan"))
						x = Math.tan(Math.toRadians(x));
					else if (func.equals("log"))
						x = Math.log(Math.toRadians(x));
					else if (func.equals("sinh"))
						x = Math.sinh(Math.toRadians(x));
					else if (func.equals("cosh"))
						x = Math.cosh(Math.toRadians(x));
					else if (func.equals("tanh"))
						x = Math.tanh(Math.toRadians(x));
					else if (func.equals("logz"))
						x = Math.log10(Math.toRadians(x));

					else
						throw new RuntimeException("Unknown function: " + func);
				} else {
					throw new RuntimeException("Unexpected: " + (char) ch);
				}

				if (eat('^'))
					x = Math.pow(x, parseFactor()); // exponentiation

				return x;
			}
		}.parse();
	}

	public void setkomplettmute() {//set mute or not
		if(komplettstumm==true) {
			komplettstumm = false;
		} else {
			komplettstumm=true;
		}
	}
}
