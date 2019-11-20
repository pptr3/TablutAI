package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.util.List;



import aima.core.search.adversarial.Game;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.State.Turn;
import java.util.Random;

public class TablutGame implements Game<StateTablut, XYWho, Turn> {

	private StateTablut initialState;
	
	public TablutGame() {
		this.initialState = new StateTablut();
	}

	
	@Override
	public List<XYWho> getActions(StateTablut state) {
		return state.getAllLegalMoves();
	}

	@Override
	public StateTablut getInitialState() {
		return this.initialState;
	}

	@Override
	public Turn getPlayer(StateTablut state) {
		return state.getTurn();
	}

	@Override
	public Turn[] getPlayers() {
		return new Turn[] {Turn.WHITE, Turn.BLACK};
	}

	@Override
	public double getUtility(StateTablut state, Turn player) {
		if((player).equals(Turn.WHITE)) {
			return new Random().nextInt(100);
		} else {
			return new Random().nextInt(10000);
		}
	}

	@Override
	public boolean isTerminal(StateTablut state) {
		//int a =  new Random().nextInt(100);
		if(state.depth == 10) {
			return true;
		} else {
			state.depth++;
			return false;
		}
	}

	@Override
	public StateTablut getResult(StateTablut state, XYWho action) {
		StateTablut result = state.clone();
		result.mark(action);
		return state;
	}
	
}
