package it.unibo.ai.didattica.competition.tablut.client.petru;

import aima.core.environment.tictactoe.TicTacToeGame;
import aima.core.environment.tictactoe.TicTacToeState;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.util.datastructure.XYLocation;

public class Main {
	
	public static void main(String[] args) {
		/*TicTacToeGame ttg = new TicTacToeGame();
		TicTacToeState current_state = ttg.getInitialState();
		TicTacToeState up = null;
		
		while(!ttg.isTerminal(current_state)) {
			AlphaBetaSearch<TicTacToeState, XYLocation, String> ab = new AlphaBetaSearch<TicTacToeState, XYLocation, String> (ttg);
			up = ttg.getResult(current_state, ab.makeDecision(current_state));
			current_state = up;
			System.out.println(ab.getMetrics());
		}
		
		AlphaBetaSearch<TicTacToeState, XYLocation, String> ab = new AlphaBetaSearch<TicTacToeState, XYLocation, String> (ttg);
		up = ttg.getResult(current_state, ab.makeDecision(current_state));
		System.out.println(ab.getMetrics());*/
		TablutGame st = new TablutGame();
		StateTablut c = st.getInitialState();
		AlphaBetaSearch<StateTablut, XYWho, String> ab = new AlphaBetaSearch<StateTablut, XYWho, String> (st);
		//System.out.println((st.getResult(c, ab.makeDecision(c))));
		System.out.println(ab.getMetrics());
	}
}
