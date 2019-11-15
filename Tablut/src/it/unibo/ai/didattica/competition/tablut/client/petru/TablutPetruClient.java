package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.client.TablutRandomClient;
import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

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

	public void print_pawns_empty() {
		System.out.println("PAWNS");
		for(int i = 0; i < this.pawns.size(); i++) {
				System.out.println(this.pawns.get(i)[0] + " " + this.pawns.get(i)[1]);
		}
		System.out.println("EMPTY");
		for(int i = 0; i < this.empty.size(); i++) {
			System.out.println(this.empty.get(i)[0] + " " + this.empty.get(i)[1]);
		}
	}
	
	public void store_pawns_and_empty_coordinates(Pawn s) {
		int[] buf;
		for (int i = 0; i < this.state.getBoard().length; i++) {
			for (int j = 0; j < this.state.getBoard().length; j++) {
				if(s == State.Pawn.WHITE) {
					if (this.state.getPawn(i, j).equalsPawn(State.Pawn.WHITE.toString())
							|| this.state.getPawn(i, j).equalsPawn(State.Pawn.KING.toString())) {
						buf = new int[2];
						buf[0] = i;
						buf[1] = j;
						this.pawns.add(buf);
					} else if (this.state.getPawn(i, j).equalsPawn(State.Pawn.EMPTY.toString())) {
						buf = new int[2];
						buf[0] = i;
						buf[1] = j;
						this.empty.add(buf);
					}
					// duplicate code
				} else {
					if (state.getPawn(i, j).equalsPawn(State.Pawn.BLACK.toString())) {
						buf = new int[2];
						buf[0] = i;
						buf[1] = j;
						this.pawns.add(buf);
					} else if (state.getPawn(i, j).equalsPawn(State.Pawn.EMPTY.toString())) {
						buf = new int[2];
						buf[0] = i;
						buf[1] = j;
						this.empty.add(buf);
					}
				}
			}
		}
	}
	
	// currently the next action is chosen randomly
	public Action search_next_action() {
		int[] selected = null;
		boolean found = false;
		Action action = null;
		try {
			action = new Action("z0", "z0", State.Turn.WHITE);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		while (!found) {
			if (this.pawns.size() > 1) {
				selected = this.pawns.get(new Random().nextInt(this.pawns.size() - 1)); // get a random pawn
			} else {
				selected = this.pawns.get(0);
			}
			String from = this.getCurrentState().getBox(selected[0], selected[1]);
			
			// take an empty random position where to move the pawn
			selected = this.empty.get(new Random().nextInt(this.empty.size() - 1));
			String to = this.getCurrentState().getBox(selected[0], selected[1]);

			try {
				action = new Action(from, to, State.Turn.WHITE);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				this.rules.checkMove(this.state, action);
				found = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return action;
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
	
	public void check_general_status(Turn t) {
		if (t == StateTablut.Turn.WHITE) {
			// è il turno dell'avversario
			if (this.state.getTurn().equals(StateTablut.Turn.BLACK)) {
				System.out.println("Waiting for BLACK move... ");
			}
		} else if (this.state.getTurn().equals(StateTablut.Turn.WHITE)) {
				System.out.println("Waiting for WHITE move... ");
		}
		// ho vinto
		else if (this.state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
			System.out.println("YOU WIN!");
			System.exit(0);
		}
		// ho perso
		else if (this.state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
			System.out.println("YOU LOSE!");
			System.exit(0);
		}
		// pareggio
		else if (this.state.getTurn().equals(StateTablut.Turn.DRAW)) {
			System.out.println("DRAW!");
			System.exit(0);
		}
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
					store_pawns_and_empty_coordinates(State.Pawn.WHITE);
					Action next_action = search_next_action();
					System.out.println("Mossa scelta: " + next_action.toString());
					send_action(next_action);
				} else {
					check_general_status(StateTablut.Turn.WHITE);
				}
			} else {
				// black turn
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					store_pawns_and_empty_coordinates(State.Pawn.BLACK);
					Action next_action = search_next_action();
					System.out.println("Mossa scelta: " + next_action.toString());
					send_action(next_action);
				} else {
					check_general_status(StateTablut.Turn.BLACK);
				}

			}
		}

	}
}
