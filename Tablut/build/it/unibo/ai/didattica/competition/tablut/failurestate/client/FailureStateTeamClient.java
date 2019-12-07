package it.unibo.ai.didattica.competition.tablut.failurestate.client;

import java.io.IOException;

import java.net.UnknownHostException;

import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import it.unibo.ai.didattica.competition.tablut.failurestate.algorithm.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.failurestate.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.failurestate.game.MyStateTablut;
import it.unibo.ai.didattica.competition.tablut.failurestate.game.TablutGame;
import it.unibo.ai.didattica.competition.tablut.failurestate.game.XYWho;



public class FailureStateTeamClient extends TablutClient {

	private int d = 2;
	private TablutGame tablutGame = new TablutGame(this.d);
	private AlphaBetaSearch<MyStateTablut, XYWho, Turn> ab = new AlphaBetaSearch<MyStateTablut, XYWho, Turn> (this.tablutGame, this.d);
	
	public FailureStateTeamClient(String player, String name) throws UnknownHostException, IOException {
		super(player, name);
	}

	@Override
	public void run() {

		try {
			this.declareName();
		} catch (Exception e) {
		}

		State state;
		Game rules = null;
		state = new StateTablut();
		state.setTurn(State.Turn.WHITE);
		rules = new GameAshtonTablut(99, 0, "garbage", "fake", "fake");
		System.out.println("You are player " + this.getPlayer().toString() + "!");

		while (true) {
			try {
				this.read();
			} catch (ClassNotFoundException | IOException e1) {
				System.exit(1);
			}
			state = this.getCurrentState();
			MyStateTablut state2 = new MyStateTablut(4).stateAdapter(this.getCurrentState());
			System.out.println(this.getCurrentState());
			//state.printBoard();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			if (this.getPlayer().equals(Turn.WHITE)) {
				if (state.getTurn().equals(Turn.WHITE)) {
					
					Action a = null;
					XYWho a2 = this.ab.makeDecision(state2);
					String from = state.getBox(a2.getWho()[0], a2.getWho()[1]);
					String to = state.getBox(a2.getX(), a2.getY());
					try {
						a = new Action(from, to, StateTablut.Turn.WHITE);
					} catch (IOException e1) {
					}
					System.out.println("Mossa scelta: " + a.toString());
					try {
						this.write(a);
					} catch (ClassNotFoundException | IOException e) {
					}
				

				}
			} else {

				if (state.getTurn().equals(Turn.BLACK)) {
					Action a = null;
					XYWho a2 = this.ab.makeDecision(state2);
					String from = state.getBox(a2.getWho()[0], a2.getWho()[1]);
					String to = state.getBox(a2.getX(), a2.getY());
					
					try {
						a = new Action(from, to, StateTablut.Turn.BLACK);
					} catch (IOException e1) {
					}
					System.out.println("Mossa scelta: " + a.toString());
					
					try {
						this.write(a);
					} catch (ClassNotFoundException | IOException e) {
					}
				}
			}
		}	
	}
		public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
			String role = "";
			String name = "FailureState";
		
			if (args.length < 1) {
				System.out.println("You must specify which player you are (WHITE or BLACK)");
				System.exit(-1);
			} else {
				System.out.println(args[0]);
				role = (args[0]);
			}
			if (args.length == 3) {
				name = args[2];
			}
			System.out.println("Selected client: " + args[0]);
			FailureStateTeamClient client = new FailureStateTeamClient(role, name);
			client.run();
		}
}