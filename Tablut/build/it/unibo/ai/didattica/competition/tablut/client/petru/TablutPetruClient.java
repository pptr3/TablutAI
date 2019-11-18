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
	private List<int[]> pawns = new ArrayList<int[]>();
	private List<int[]> empty = new ArrayList<int[]>();
	private State state;
	private Game rules = null;


	public TablutPetruClient(String player, String name, int gameChosen) throws UnknownHostException, IOException {
		super(player, name);
		game = gameChosen;
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


	
	// currently the next action is chosen randomly
	public Action search_next_action() throws IOException {
		 
		return new Action("z0", "z0", State.Turn.WHITE);
		
	}
	
	
	
	public void send_action(Action next_action) {
		try {
			this.write(next_action);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		clear_pawns_and_pawns();
	}
	
	public void receive_state() {
		try {
			this.read();
		} catch (ClassNotFoundException | IOException e1) {
			System.exit(1);
		}
		System.out.println("Current state:");
		this.state = this.getCurrentState();
		/*try {
			//Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	
	
	
	private void clear_pawns_and_pawns() {
		this.pawns.clear();
		this.empty.clear();
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void run() {
		try {
			this.declareName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		switch (this.game) {
			case 4:
				this.state = new StateTablut();
				this.state.setTurn(State.Turn.WHITE);
				this.rules = new GameAshtonTablut(99, 0, "garbage", "fake", "fake");
				break;
			default:
				System.out.println("Error in default");
				System.exit(4);
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
}
