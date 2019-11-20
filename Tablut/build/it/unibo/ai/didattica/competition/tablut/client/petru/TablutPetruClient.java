package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.io.IOException;
import java.net.UnknownHostException;

import aima.core.environment.tictactoe.TicTacToeGame;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import it.unibo.ai.didattica.competition.tablut.client.TablutHumanClient;
import it.unibo.ai.didattica.competition.tablut.client.TablutRandomClient;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.*;

/**
 * 
 * @author Petru Potrimba
 *
 */
public class TablutPetruClient extends TablutClient {
	
	
	public TablutPetruClient(String player) throws UnknownHostException, IOException {
		super(player, "humanInterface");
	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

		if (args.length == 0) {
			System.out.println("You must specify which player you are (WHITE or BLACK)!");
			System.exit(-1);
		}
		System.out.println("Selected this: " + args[0]);

		TablutClient client = new TablutPetruClient(args[0]);

		client.run();

	}

	@Override
	public void run() {
		while(true) {

			try {
				this.declareName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
		}
	}
}
