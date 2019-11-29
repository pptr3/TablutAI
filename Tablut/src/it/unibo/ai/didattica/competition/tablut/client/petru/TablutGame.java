package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.util.List;
import it.unibo.ai.didattica.competition.tablut.client.ab.Game;
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
		double toreturn = 0;
		if (result != StateTablut.STATE_IS_NOT_YET_FINISHED) {
			if(player.equals(Turn.WHITE)) {
				toreturn = result;
				return result;
			} else if(player.equals(Turn.BLACK)) {
				
			}
		} else if(state.getCurrentDepth() == 0) {
			if(player.equals(Turn.WHITE)) {
				toreturn = 10*(state.getDistanceFromKingToClosestEscapeArea()) - 2*state.isTheKingInTheThrone() - 0.5*state.getNumberOfWhiteCloseToKing() - 4*state.getNumberOfBlackCloseToKing()
				+ 0.5*state.getNumberOf(Pawn.WHITE) - 2*state.getNumberOf(Pawn.BLACK);
			} else if(player.equals(Turn.BLACK)) {
				
			}
		}
		return toreturn;
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
