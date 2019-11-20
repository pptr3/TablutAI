package it.unibo.ai.didattica.competition.tablut.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut;
import it.unibo.ai.didattica.competition.tablut.client.petru.TablutClient;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.Action;

/**
 * 
 * @author A. Piretti, Andrea Galassi
 *
 */
public class TablutHumanClient extends TablutClient {

	public TablutHumanClient(String player) throws UnknownHostException, IOException {
		super(player, "humanInterface");
	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

		if (args.length == 0) {
			System.out.println("You must specify which player you are (WHITE or BLACK)!");
			System.exit(-1);
		}
		System.out.println("Selected this: " + args[0]);

		TablutClient client = new TablutHumanClient(args[0]);

		client.run();

	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
		}
	}

}
