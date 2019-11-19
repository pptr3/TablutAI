package it.unibo.ai.didattica.competition.tablut.client.petru;

import aima.core.search.adversarial.AlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Turn;

public class Main {
	
	public static void main(String[] args) {
		
		TablutGame st = new TablutGame();
		StateTablut c = st.getInitialState();
		st.getState().setTurn(Turn.BLACK);
		AlphaBetaSearch<StateTablut, XYWho, Turn> ab = new AlphaBetaSearch<StateTablut, XYWho, Turn> (st);
		//System.out.println((st.getResult(c, ab.makeDecision(c))));
		XYWho a = ab.makeDecision(c);
		//System.out.println(a.getX() + " " + a.getY() + " | " + a.getWho()[0] + ", " + a.getWho()[1]);
		System.out.println(ab.getMetrics());
		
	}
}
