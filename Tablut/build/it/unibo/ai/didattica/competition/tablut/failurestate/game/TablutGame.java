package it.unibo.ai.didattica.competition.tablut.failurestate.game;

import java.util.List;

import it.unibo.ai.didattica.competition.tablut.failurestate.game.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.failurestate.game.StateTablut.Turn;


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
		double toreturn = 0;
		if(state.getCurrentDepth() == 0) {
			if(player.equals(Turn.WHITE)) {
				toreturn = 200000*state.isTheKingOnEscapeArea()
						+ 200000*state.playerCannotMoveAnyPawn(Pawn.BLACK)
						+ 100*(state.getDistanceFromKingToClosestEscapeArea())
						+ 6*state.getNumberOf(Pawn.WHITE)
						+ 20*state.getDistanceFromKingToAllBlacks()
						+ 2*state.getNumberCloseToTheKingOf(Pawn.WHITE)
						- 2*state.isTheKingInTheThrone()
						- 10*state.getNumberCloseToTheKingOf(Pawn.BLACK)
						- 2*state.getNumberOfCampsCloseToKing()
						- 4*state.getNumberOf(Pawn.BLACK)
						- 200000*state.hasBlackWon();
				
			} else if(player.equals(Turn.BLACK)) {
				toreturn = 200000*state.hasBlackWon() 
						+ 200000*state.playerCannotMoveAnyPawn(Pawn.WHITE)
						+ 50*state.getNumberCloseToTheKingOf(Pawn.BLACK)
						+ 10*state.getNumberOf(Pawn.BLACK)
						+ 10*state.getNumberOfCampsCloseToKing()
						- 100*state.getDistanceFromKingToClosestEscapeArea()
						- 25*state.getDistanceFromKingToAllBlacks()
						- 25*state.getNumberOf(Pawn.WHITE)
						- 200000*state.isTheKingOnEscapeArea();
			}
		}
		return toreturn;
	}

	@Override
	public boolean isTerminal(StateTablut state) {
		return state.getCurrentDepth() == 0;
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
