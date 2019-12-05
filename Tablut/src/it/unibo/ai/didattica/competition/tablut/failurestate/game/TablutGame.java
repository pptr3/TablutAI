package it.unibo.ai.didattica.competition.tablut.failurestate.game;

import java.util.List;

import it.unibo.ai.didattica.competition.tablut.failurestate.game.InternalState.Pawn;
import it.unibo.ai.didattica.competition.tablut.failurestate.game.InternalState.Turn;


public class TablutGame implements Game<InternalState, XYWho, Turn> {

	private InternalState initialState;
	
	public TablutGame(int depth) {
		this.initialState = new InternalState(depth);
	}

	@Override
	public List<XYWho> getActions(InternalState state) {
		return state.getAllLegalMoves();
	}

	@Override
	public InternalState getInitialState() {
		return this.initialState;
	}

	@Override
	public Turn getPlayer(InternalState state) {
		return state.getTurn();
	}

	@Override
	public Turn[] getPlayers() {
		return new Turn[] {Turn.WHITE, Turn.BLACK};
	}

	@Override
	public double getUtility(InternalState state, Turn player) {
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
	public boolean isTerminal(InternalState state) {
		return state.getCurrentDepth() == 0;
	}

	@Override
	public InternalState getResult(InternalState state, XYWho action) {
		InternalState result = state.clone();
		result.applyMove(action);
		return result;
	}

	
	@Override
	public int getCurrentDepth(InternalState state) {
		return state.getCurrentDepth();
	}

	@Override
	public void setCurrentDepth(InternalState state, int depth) {
		state.setCurrentDepth(depth);
		
	}
	
}
