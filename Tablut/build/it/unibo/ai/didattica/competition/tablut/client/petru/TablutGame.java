package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import it.unibo.ai.didattica.competition.tablut.client.ab.Game;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Area;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;


public class TablutGame implements Game<StateTablut, XYWho, Turn> {

	private StateTablut initialState;
	
	public TablutGame(int depth) {
		this.initialState = new StateTablut(depth);
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
		int result = state.getUtility();
		System.out.println(player);
		if (result != StateTablut.STATE_IS_NOT_YET_FINISHED) { // white max, black min
			//return result;
		} else if(state.getCurrentDepth() == 0) {
			
		}
		return result;
	}

	@Override
	public boolean isTerminal(StateTablut state) {
		return state.getCurrentDepth() == 0 || state.getUtility() != StateTablut.STATE_IS_NOT_YET_FINISHED;
	}

	@Override
	public StateTablut getResult(StateTablut state, XYWho action) {
		StateTablut result = state.clone();
		result.applyMove(action);
		return result;
	}

	
	@Override
	public int getCurrentDepth(StateTablut state) {
		return state.getCurrentDepth();
	}

	@Override
	public void setCurrentDepth(StateTablut state, int depth) {
		state.setCurrentDepth(depth);
		
	}
	
}
