package it.unibo.ai.didattica.competition.tablut.failurestate.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class WhiteFailureStateTeamClient {
	
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		String[] array = new String[]{"BLACK"};
		if (args.length>0){
			array = new String[]{"BLACK", args[0]};
		}
		FailureStateTeamClient.main(array);
	}


}
