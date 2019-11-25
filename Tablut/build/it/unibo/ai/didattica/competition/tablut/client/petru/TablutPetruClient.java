package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.io.IOException;


import java.net.UnknownHostException;

import aima.core.environment.tictactoe.TicTacToeGame;
import aima.core.search.adversarial.MinimaxSearch;
import it.unibo.ai.didattica.competition.tablut.client.TablutRandomClient;
import it.unibo.ai.didattica.competition.tablut.client.ab.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.util.StreamUtils;

/**
 * 
 * @author Petru Potrimba
 *
 */
public class TablutPetruClient extends TablutClient {
	
	private TablutGame game;
	TablutGame st = new TablutGame(3);
	StateTablut c = st.getInitialState();
	AlphaBetaSearch<StateTablut, XYWho, Turn> ab = new AlphaBetaSearch<StateTablut, XYWho, Turn> (st, 3);

		
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
				super.declareName();
			} catch (Exception e) {
				//e.printStackTrace();
			}
			
			try {
				super.read();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			if (this.getPlayer().equals(Turn.WHITE)) {
				// è il mio turno
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					XYWho a2 = ab.makeDecision(this.getCurrentState());
					
					String from = this.getCurrentState().getBox(a2.getWho()[0], a2.getWho()[1]);
					String to = this.getCurrentState().getBox(a2.getX(), a2.getY());
					Action a = null;
					try {
						a = new Action(from, to, StateTablut.Turn.WHITE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("WHITE MOVE from: " + from + "to: " +  to);
				}
			} else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
				XYWho a2 = ab.makeDecision(this.getCurrentState());
				
				String from = this.getCurrentState().getBox(a2.getWho()[0], a2.getWho()[1]);
				String to = this.getCurrentState().getBox(a2.getX(), a2.getY());
				Action a = null;
				try {
					a = new Action(from, to, StateTablut.Turn.BLACK);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("BLACK MOVE from: " + from + "to: " +  to);
			}
		}
	}
}
