package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.io.IOException;
import java.net.UnknownHostException;
import aima.core.search.adversarial.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.*;

/**
 * 
 * @author Petru Potrimba
 *
 */
public class TablutPetruClient extends TablutClient {
	
	
	private TablutGame tablut;
	private AlphaBetaSearch<StateTablut, XYWho, String> alpha_beta;
	
	public TablutPetruClient(String player, String name, int gameChosen) throws UnknownHostException, IOException {
		super(player, name);
		this.tablut = new TablutGame();
		//this.tablut.getState().setTurn(State.Turn.BLACK);
		this.alpha_beta =  new AlphaBetaSearch<StateTablut, XYWho, String> (this.tablut);
	}

	public void receiveState() {
		try {
			super.read();
		} catch (ClassNotFoundException | IOException e1) {
			System.exit(1);
		}
		System.out.println("Current state:");
		//this.tablut.setState((StateTablut) super.getCurrentState());
	}
	

	@Override
	public void run() {
		try {
			super.declareName();
		} catch (Exception e) {
		}
		
		while (true) {
			this.receiveState();
			// white turn
			/*if (this.getPlayer().equals(Turn.WHITE)) {
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					XYWho actionToPerform = this.alpha_beta.makeDecision(this.tablut.getState());
					String from = String.valueOf(actionToPerform.getX());
					String to = String.valueOf(actionToPerform.getY());
					try {
						Action action = new Action(from, to, super.getPlayer());
						super.write(action);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			} else {
				// black turn
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					XYWho actionToPerform = this.alpha_beta.makeDecision(this.tablut.getState());
					String from = String.valueOf(actionToPerform.getX());
					String to = String.valueOf(actionToPerform.getY());
					try {
						Action action = new Action(from, to, super.getPlayer());
						super.write(action);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			 		
			}*/
		}

	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		String role = "WHITE";
		String name = "Petru";
		int gametype = 4;
		TablutPetruClient client = new TablutPetruClient(role, name, gametype);
		client.run();
	}
}
