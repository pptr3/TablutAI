package it.unibo.ai.didattica.competition.tablut.client.petru;

import it.unibo.ai.didattica.competition.tablut.client.ab.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;

public class Main {
	
	public static void main(String[] args) {
		
		int depth = 4;
		TablutGame st = new TablutGame(depth);
		StateTablut c = st.getInitialState();
		AlphaBetaSearch<StateTablut, XYWho, Turn> ab = new AlphaBetaSearch<StateTablut, XYWho, Turn> (st, depth);
		
		XYWho a = ab.makeDecision(c);
		System.out.println(ab.getMetrics());
	}
	static void printBoard(Pawn[][] c) {
		for(int i=0; i < 9; i++) {
			for(int j=0; j < 9; j++) {
				System.out.print(c[i][j]+ "");
			}
			System.out.println("");
		}
	}
	
}
