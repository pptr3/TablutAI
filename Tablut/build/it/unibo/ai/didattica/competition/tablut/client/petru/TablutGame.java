package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.util.List;
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
	public double getUtility(StateTablut state, Turn player) { // given a specific state and the player, return the heuristic for that player in that state
		int result = state.getUtility();
		if (state.getCurrentDepth() == 0 || result != -1) {
			if (player.equals(Turn.WHITE)) {
				result = 1 - result;
				System.out.println(result);
				return Integer.MAX_VALUE;
			} else {
				return Integer.MIN_VALUE;
			}
		} else {
			throw new IllegalArgumentException("State is not terminal.");
		}
		//return Integer.MAX_VALUE;
		//return result;
		//return new Random().nextInt(100); 
	}

	@Override
	public boolean isTerminal(StateTablut state) { // returns true if a state is terminal (namely a WHITEWIN, BLACKWIN or a DRAW)
		return state.getCurrentDepth() == 0;
	}

	@Override
	public StateTablut getResult(StateTablut state, XYWho action) {
		StateTablut result = state.clone();
		if(result.getCurrentDepth() != 0) {
			if(result.getTurn().equals(Turn.WHITE)) {
				if(result.getPawn(action.getWho()[0], action.getWho()[1]).equals(Pawn.KING)) {
					result.setPawn(action.getX(), action.getY(), Pawn.KING);
				} else {
					result.setPawn(action.getX(), action.getY(), Pawn.WHITE);
				}
				result.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				result.checkGameStatus(action);
				result.setTurn(Turn.BLACK);
			} else {
				result.setPawn(action.getX(), action.getY(), Pawn.BLACK);
				result.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				result.checkGameStatus(action);
				result.setTurn(Turn.WHITE);
			}
		}
		//result.checkGameStatus(action);
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
