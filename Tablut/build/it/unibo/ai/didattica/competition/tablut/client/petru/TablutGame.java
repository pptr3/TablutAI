package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.util.List;
import aima.core.search.adversarial.Game;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;
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
	public double getUtility(StateTablut state, Turn player) { // given a specific state and the player, return the heuristic for that player. If
		if((player).equals(Turn.WHITE)) {
			return new Random().nextInt(100);
		} else {
			return new Random().nextInt(10000);
		}
	}

	@Override
	public boolean isTerminal(StateTablut state) { // returns true if a state is terminal (namely a WHITEWIN, BLACKWIN or a DRAW)
		return state.getUtility() != -1;
	}

	@Override
	public StateTablut getResult(StateTablut state, XYWho action) {
		if(state.getUtility() == -1) {
			if(state.getTurn().equals(Turn.WHITE)) {
				if(state.getPawn(action.getWho()[0], action.getWho()[1]).equals(Pawn.KING)) {
					state.setPawn(action.getX(), action.getY(), Pawn.KING);
				} else {
					state.setPawn(action.getX(), action.getY(), Pawn.WHITE);
				}
				state.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				state.setTurn(Turn.BLACK);
			} else {
				state.setPawn(action.getX(), action.getY(), Pawn.BLACK);
				state.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				state.setTurn(Turn.WHITE);
			}
			// if an action on the state triggers a WIN, DRAW or LOSE, change accordingly the utility
			state.checkGameStatus(action);
			return state;
		}
		return state;
	}
	
}
