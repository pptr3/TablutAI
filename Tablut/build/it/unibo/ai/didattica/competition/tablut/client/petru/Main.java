package it.unibo.ai.didattica.competition.tablut.client.petru;

import aima.core.environment.tictactoe.TicTacToeGame;



import aima.core.environment.tictactoe.TicTacToeState;

import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.util.datastructure.XYLocation;
import it.unibo.ai.didattica.competition.tablut.client.ab.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;

public class Main {
	
	public static void main(String[] args) {
		
		TablutGame tablutGame = new TablutGame();
		StateTablut st = tablutGame.getInitialState();
		AlphaBetaSearch<StateTablut, XYWho, Turn> ab = new AlphaBetaSearch<StateTablut, XYWho, Turn> (tablutGame, 3);
		XYWho a = ab.makeDecision(st);
		System.out.println(ab.getMetrics());
		System.out.println(st);
		System.out.println(a);
		
		XYWho a2 = ab.makeDecision(st);
		System.out.println(ab.getMetrics());
		System.out.println(st);
		System.out.println(a2);
	}
	
	
}
