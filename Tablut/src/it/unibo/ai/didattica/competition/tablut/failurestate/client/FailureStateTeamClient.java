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

	private int d = 4;
	private TablutGame tablutGame = new TablutGame(this.d);
	private AlphaBetaSearch<MyStateTablut, XYWho, Turn> ab = new AlphaBetaSearch<MyStateTablut, XYWho, Turn> (this.tablutGame, this.d);
	
	public FailureStateTeamClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
		super(player, name, timeout, ipAddress);
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
			state2.printBoard();
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
		int timeout = 0;
		String serverIpAddress = "";
		if (args.length < 1) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		}
		if (args.length < 3) {
			System.out.println("You must specify in the following order which player you are (WHITE or BLACK), timeout time(in seconds) and server IP address.");
			System.exit(-1);
		} else if (args.length == 3) {
			role = args[0];
			name = name + args[0];
			timeout = Integer.valueOf(args[1]);
			serverIpAddress = args[2];
		}
		
		System.out.println("Selected client: " + args[0]);
		FailureStateTeamClient client = new FailureStateTeamClient(role, name, timeout, serverIpAddress);
		client.run();
	}
}