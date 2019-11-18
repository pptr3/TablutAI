package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.ai.didattica.competition.tablut.client.TablutRandomClient;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.*;

/**
 * 
 * @author Petru Potrimba
 *
 */
public class TablutPetruClient extends TablutClient {
	private int game;
	private State state;
	private Game rules = null;


	public TablutPetruClient(String player, String name, int gameChosen) throws UnknownHostException, IOException {
		super(player, name);
		this.game = gameChosen;
		this.state = new StateTablut();
		this.state.setTurn(State.Turn.WHITE);
		this.rules = new GameAshtonTablut(99, 0, "garbage", "fake", "fake");
	}

	public TablutPetruClient(String player) throws UnknownHostException, IOException {
		this(player, "Petru", 4);
	}

	public TablutPetruClient(String player, String name) throws UnknownHostException, IOException {
		this(player, name, 4);
	}

	public TablutPetruClient(String player, int gameChosen) throws UnknownHostException, IOException {
		this(player, "Petru", gameChosen);
	}


	public Action search_next_action() throws IOException {
		return new Action("z0", "z0", State.Turn.WHITE);
	}
	
	public void send_action(Action next_action) {
		try {
			this.write(next_action);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receive_state() {
		try {
			this.read();
		} catch (ClassNotFoundException | IOException e1) {
			System.exit(1);
		}
		System.out.println("Current state:");
		this.state = this.getCurrentState();
	}
	

	@Override
	public void run() {
		try {
			super.declareName();
		} catch (Exception e) {
		}
		
		while (true) {
			receive_state();
			// white turn
			if (this.getPlayer().equals(Turn.WHITE)) {
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
				/*
					Action next_action = search_next_action();
					System.out.println("Mossa scelta: " + next_action.toString());
					send_action(next_action);*/
				}
			} else {
				// black turn
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					/*
					Action next_action = search_next_action();
					System.out.println("Mossa scelta: " + next_action.toString());
					send_action(next_action);*/
				}

			}
		}

	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		int gametype = 4;
		String role = "";
		String name = "Petru";
		
		if (args.length < 1) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		} else {
			System.out.println(args[0]);
			role = (args[0]);
		}
		if (args.length == 2) {
			System.out.println(args[1]);
			gametype = Integer.parseInt(args[1]);
		}
		if (args.length == 3) {
			name = args[2];
		}
		
		System.out.println("Selected client: " + args[0]);
		TablutPetruClient client = new TablutPetruClient(role, name, gametype);
		client.run();
	}
}
