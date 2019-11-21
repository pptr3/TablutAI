package it.unibo.ai.didattica.competition.tablut.client.petru;

import aima.core.environment.tictactoe.TicTacToeGame;

import aima.core.environment.tictactoe.TicTacToeState;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.util.datastructure.XYLocation;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;

public class Main {
	
	public static void main(String[] args) {
		
//		TablutGame st = new TablutGame();
//		StateTablut c = st.getInitialState();
//		AlphaBetaSearch<StateTablut, XYWho, Turn> ab = new AlphaBetaSearch<StateTablut, XYWho, Turn> (st);
//		XYWho a = ab.makeDecision(c);
		
		TablutGame st = new TablutGame();
		StateTablut c = st.getInitialState();
		IterativeDeepeningAlphaBetaSearch<StateTablut, XYWho, Turn> ab = new IterativeDeepeningAlphaBetaSearch<StateTablut, XYWho, Turn> (st, 10, -100, 300);
		XYWho a = ab.makeDecision(c);
		System.out.println(a);
		System.out.println(ab.getMetrics());
//		c.setTurn(Turn.BLACK);
//		
//		XYWho a2 = ab.makeDecision(c);
//		System.out.println(a2.getX() + " " + a2.getY() + " | " + a2.getWho()[0] + ", " + a2.getWho()[1]);
//		c.setPawn(a2.getX(), a2.getY(), Pawn.BLACK);
//		c.setPawn(a2.getWho()[0], a2.getWho()[1], Pawn.EMPTY);
//		printBoard(c.getBoard());
//		
//		c.setTurn(Turn.WHITE);
//		
//		XYWho a22 = ab.makeDecision(c);
//		c.setPawn(a22.getX(), a22.getY(), Pawn.WHITE);
//		c.setPawn(a22.getWho()[0], a22.getWho()[1], Pawn.EMPTY);
//		printBoard(c.getBoard());
		
		
//		TicTacToeGame ttg = new TicTacToeGame();
//		TicTacToeState ss = ttg.getInitialState();
//		AlphaBetaSearch<TicTacToeState, XYLocation, String> ab = new AlphaBetaSearch<TicTacToeState, XYLocation, String> (ttg);
//		XYLocation a = ab.makeDecision(ss);
//		System.out.println(ss);
	}
	
	static void printBoard(Pawn[][] c) {
		for(int i=0; i < 9; i++) {
			for(int j=0; j < 9; j++) {
				System.out.print(c[i][j]+ "  ");
			}
			System.out.println("\n");
		}
	}
}
