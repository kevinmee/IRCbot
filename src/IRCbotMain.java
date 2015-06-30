import org.jibble.pircbot.*;


public class IRCbotMain extends PircBot{

	static MyBot bot;
	
	public static void main(String[] args) throws Exception {
		bot = new MyBot();
		bot.setVerbose(true);
		bot.connect("irc.cyanide-x.net");
		bot.identify("lolowned12321");
		bot.joinChannel("#gladiators");
		bot.sendMessage("chanserv", "invite #GLD");
		//bot.joinChannel("#gld");

	}
	


}
