package it.unibo.ai.didattica.competition.tablut.client.petru;

import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Turn;

public class Main {
	
	public static void main(String[] args) {
		
		TablutGame st = new TablutGame();
		StateTablut c = st.getInitialState();
		st.getState().setTurn(Turn.BLACK);
		MinimaxSearch<StateTablut, XYWho, Turn> ab = new MinimaxSearch<StateTablut, XYWho, Turn> (st);
		//System.out.println((st.getResult(c, ab.makeDecision(c))));
		XYWho a = ab.makeDecision(c);
		//System.out.println(a.getX() + " " + a.getY() + " | " + a.getWho()[0] + ", " + a.getWho()[1]);
		System.out.println(ab.getMetrics());
		
	}
}
/*XYWho actionToPerform = this.alpha_beta.makeDecision(this.getCurrentState());
String from = this.getCurrentState().getBox(actionToPerform.getX(), actionToPerform.getY());
String to = this.getCurrentState().getBox(actionToPerform.getWho()[0], actionToPerform.getWho()[1]);
System.out.println("PPPPPPPPP from: " + from + "to: " +  to);
try {
	Action action = new Action(from, to, Turn.WHITE);
	super.write(action);
} catch (Exception e) {
	e.printStackTrace();

}*/