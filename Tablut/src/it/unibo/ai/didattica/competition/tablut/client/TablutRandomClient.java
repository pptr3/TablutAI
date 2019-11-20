package it.unibo.ai.didattica.competition.tablut.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.ai.didattica.competition.tablut.client.petru.State;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut;
import it.unibo.ai.didattica.competition.tablut.client.petru.TablutClient;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.*;

/**
 * 
 * @author A. Piretti, Andrea Galassi
 *
 */
public class TablutRandomClient extends TablutClient {

	private int game;

	public TablutRandomClient(String player, String name, int gameChosen) throws UnknownHostException, IOException {
		super(player, name);
		game = gameChosen;
	}

	public TablutRandomClient(String player) throws UnknownHostException, IOException {
		this(player, "random", 4);
	}

	public TablutRandomClient(String player, String name) throws UnknownHostException, IOException {
		this(player, name, 4);
	}

	public TablutRandomClient(String player, int gameChosen) throws UnknownHostException, IOException {
		this(player, "random", gameChosen);
	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		int gametype = 4;
		String role = "";
		String name = "random";
		// TODO: change the behavior?
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

		TablutRandomClient client = new TablutRandomClient(role, name, gametype);
		client.run();
	}

	@Override
	public void run() {
	}
}
