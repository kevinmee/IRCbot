import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.jibble.pircbot.*;


public class MyBot extends PircBot{

	ArrayList<String> superUser = new ArrayList<String>();
	ArrayList<String> targets = new ArrayList<String>();
	ArrayList<String> techCost = new ArrayList<String>();
	
	public MyBot() throws IOException {
        this.setName("Decimus");
        superUser.add("Fin");
        superUser.add("Fin_");
        superUser.add("macmoney");
        superUser.add("Fernando");
        superUser.add("AK");
        readTargets();
        
        setMessageDelay(25);
    }
	

	public void readTargets() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("targets.txt"));
		String line = br.readLine();
		
		while (line != null){
			targets.add(line);
			line = br.readLine();
		}	
		br = new BufferedReader(new FileReader("techCosts.txt"));
		line = br.readLine();
		
		while (line != null){
			techCost.add(line);
			line = br.readLine();
		}
	}
	public void writeTarget() throws IOException{
		File file = new File("targets.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i = 0; i< targets.size(); i++){
			bw.write(targets.get(i));
			bw.newLine();
		}
		bw.close();
	}
	
	
	public void onMessage(String channel, String sender,
            String login, String hostname, String message){
		if (message.equalsIgnoreCase("!time")){
			String time = new java.util.Date().toString();
			sendMessage(channel, sender+ ": The time is now " + time);
		}
		if(message.equalsIgnoreCase("!deeznuts")){
			sendMessage(channel, sender+ " how do you like deeznuts?!");
		}
		if(channel.equals("#gld")){
			if(message.equalsIgnoreCase("!mercs")){
				Calendar now = Calendar.getInstance();
				now.add(Calendar.SECOND, +5);
				int minute = now.get(Calendar.MINUTE);
				int second = now.get(Calendar.SECOND);
				int inSec = 60 - second;
				int inMin = (3 - (minute%3));
				
				if(inSec > 0){
					inMin--;
				}
				//if(inSec - 10 < 0){
				//	inSec+=60;
				//	inMin--;
				//}
				String mercTime = "Next merc update in: " + inMin + " minutes and " + (inSec) + " seconds.";
				sendMessage(channel, mercTime);
				
			
				//sendMessage(channel, "Well it is minute: "+ minute+ " so try to divide that by 3");
				sendMessage(channel, "ps: I never was paid to fight.");
			}
			if(message.equalsIgnoreCase("!techs")){
				for(int i = 0; i < techCost.size(); i++){
					sendMessage(channel, techCost.get(i));
				}
			}
			if(message.equalsIgnoreCase("!targets")){
				// display targets from text file
				sendMessage(channel, "Targets: ");
				if(targets.size() > 0){
					for(int i=0; i < targets.size(); i++){
						sendMessage(channel, targets.get(i));
					}
				}
				else{
					sendMessage(channel, "We don't currently have any targets.. let's change that ;)");
				}
			}
			if(message.contains("!addTarget")|| message.contains("!addtarget")){
				if(superUser.contains(sender)){
					// add target to text file
					String temp = message.substring(11);
					targets.add(temp);
					try {
						writeTarget();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(message.contains("!removeTarget") || message.contains("!removetarget")){
				if(superUser.contains(sender)){
					if(targets.contains(message.substring(14))){
						System.out.println("it contains the target"); // doesnt work
						targets.remove(message.substring(14));
						try {
							writeTarget();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}
		return;
	}
	public void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel){
		if(sourceNick.equalsIgnoreCase("chanserv")){
			joinChannel(channel);
		}
	}
}
