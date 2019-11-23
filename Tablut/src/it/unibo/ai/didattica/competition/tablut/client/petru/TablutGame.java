package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.util.List;

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
		if (state.getCurrentDepth() == 0) {
			// heuristic for white (MIN)
			if(player.equals(Turn.WHITE)) { // actually I have to put here whether my player is white or black and calculate accordingly the heuristic
				result = StateTablut.WHITE_WON - state.getWhiteHeuristic();					
			}
		} else {
			throw new IllegalArgumentException("State is not terminal.");
		}
		return result;
	}

	@Override
	public boolean isTerminal(StateTablut state) { // returns true if a state is terminal (namely a WHITEWIN, BLACKWIN or a DRAW)
		//System.out.println("isTerminal: "+state.getCurrentDepth() == 0 + " " + state.getCurrentDepth());
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
				result.setTurn(Turn.BLACK);
			} else {
				result.setPawn(action.getX(), action.getY(), Pawn.BLACK);
				result.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				result.setTurn(Turn.WHITE);
			}
			result.checkGameStatus(action);
		}
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
