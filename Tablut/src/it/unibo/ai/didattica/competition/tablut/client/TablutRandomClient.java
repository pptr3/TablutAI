package it.unibo.ai.didattica.competition.tablut.client;

import java.io.IOException;
import java.net.UnknownHostException;
import it.unibo.ai.didattica.competition.tablut.client.ab.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;
import it.unibo.ai.didattica.competition.tablut.client.petru.TablutClient;
import it.unibo.ai.didattica.competition.tablut.client.petru.TablutGame;
import it.unibo.ai.didattica.competition.tablut.client.petru.XYWho;
import it.unibo.ai.didattica.competition.tablut.domain.*;


public class TablutRandomClient extends TablutClient {

	private int d = 3;
	private TablutGame tablutGame = new TablutGame(this.d);
	private AlphaBetaSearch<StateTablut, XYWho, Turn> ab = new AlphaBetaSearch<StateTablut, XYWho, Turn> (this.tablutGame, this.d);
	
	public TablutRandomClient(String player, String name) throws UnknownHostException, IOException {
		super(player, name);
	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		String role = "";
		String name = "random";
	
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
		TablutRandomClient client = new TablutRandomClient(role, name);
		client.run();
	}

	@Override
	public void run() {

		try {
			this.declareName();
		} catch (Exception e) {
		}

		StateTablut state = this.tablutGame.getInitialState();
		
		System.out.println("You are player " + this.getPlayer().toString() + "!");

		while (true) {
			try {
				this.read();
			} catch (ClassNotFoundException | IOException e1) {
				System.exit(1);
			}
			state = this.getCurrentState();
			System.out.println(state.toString());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}

			if (this.getPlayer().equals(Turn.WHITE)) {
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					
					Action a = null;
					XYWho a2 = this.ab.makeDecision(this.getCurrentState());
					String from = this.getCurrentState().getBox(a2.getWho()[0], a2.getWho()[1]);
					String to = this.getCurrentState().getBox(a2.getX(), a2.getY());
					try {
						a = new Action(from, to, StateTablut.Turn.WHITE);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Mossa scelta: " + a.toString());
					try {
						this.write(a);
					} catch (ClassNotFoundException | IOException e) {
					}
				

				}
			} else {

				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					
					Action a = null;
					XYWho a2 = this.ab.makeDecision(this.getCurrentState());
					String from = this.getCurrentState().getBox(a2.getWho()[0], a2.getWho()[1]);
					String to = this.getCurrentState().getBox(a2.getX(), a2.getY());
					
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
}