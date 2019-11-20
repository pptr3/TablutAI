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
import it.unibo.ai.didattica.competition.tablut.util.StreamUtils;

/**
 * 
 * @author Petru Potrimba
 *
 */
public class TablutPetruClient extends TablutClient {
	
	private AlphaBetaSearch<StateTablut, XYWho, Turn> alpha_beta;

	public TablutPetruClient(String player) throws UnknownHostException, IOException {
		super(player, "humanInterface");
		super.game = new TablutGame();
		this.alpha_beta = new AlphaBetaSearch<StateTablut, XYWho, Turn> (super.game);
		super.game.getInitialState().setTurn(Turn.WHITE);
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
			/*try {
				//super.read();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(1);
			}*/
			
			try {
				this.declareName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			System.out.println("MOLDIII: \n"+ super.game.getState());
			
		}
	}
}
