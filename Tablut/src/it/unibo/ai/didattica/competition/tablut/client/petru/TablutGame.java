package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.util.List;
import aima.core.search.adversarial.Game;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Area;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;
import java.util.Random;

import javax.net.ssl.SSLEngineResult.Status;

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
	public double getUtility(StateTablut state, Turn player) { // given a specific state and the player, return the heuristic for that player in that state
		int result = state.getUtility();
		if (result != StateTablut.STATE_IS_NOT_YET_FINISHED) {
			// heuristic for white (MIN)
			if(player.equals(Turn.WHITE)) { // actually I have to put here whether my player is white or black and calculate accordingly the heuristic
				result = StateTablut.WHITE_WON - this.getWhiteHeuristic(state);					
			}
		} else {
			throw new IllegalArgumentException("State is not terminal.");
		}
		return result;
	}

	@Override
	public boolean isTerminal(StateTablut state) { // returns true if a state is terminal (namely a WHITEWIN, BLACKWIN or a DRAW)
		return state.getUtility() != StateTablut.STATE_IS_NOT_YET_FINISHED;
	}

	@Override
	public StateTablut getResult(StateTablut state, XYWho action) {
		StateTablut result = state.clone();
		if(result.getUtility() == StateTablut.STATE_IS_NOT_YET_FINISHED) {
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
	public int getWhiteHeuristic(StateTablut state) {
		//getDistance from KING position to closest ESCAPE area
		int h_white = 0;
		int[] kingPosition = this.getKingPosition(state);
		// checking ring around the throne
		if(kingPosition[0] == StateTablut.KING_POSITION && kingPosition[1] == StateTablut.KING_POSITION) {
			h_white = 0;
		} 
		
		if((kingPosition[0] == 3 && kingPosition[1] == 3) ||
		(kingPosition[0] == 3 && kingPosition[1] == 5) ||
		(kingPosition[0] == 5 && kingPosition[1] == 5) ||
		(kingPosition[0] == 5 && kingPosition[1] == 3) ||
		(kingPosition[0] == 4 && kingPosition[1] == 3) ||
		(kingPosition[0] == 4 && kingPosition[1] == 5) ||
		(kingPosition[0] == 5 && kingPosition[1] == 4) ||
		(kingPosition[0] == 3 && kingPosition[1] == 4)) {
			h_white = 4;
		}
			
		
		// checking second ring
		if((kingPosition[0] == 2 && kingPosition[1] == 2) ||
		(kingPosition[0] == 3 && kingPosition[1] == 2) ||		
		(kingPosition[0] == 4 && kingPosition[1] == 2) ||			
		(kingPosition[0] == 5 && kingPosition[1] == 2) ||			
		(kingPosition[0] == 6 && kingPosition[1] == 2) ||			
		(kingPosition[0] == 2 && kingPosition[1] == 6) ||			
		(kingPosition[0] == 3 && kingPosition[1] == 6) ||			
		(kingPosition[0] == 4 && kingPosition[1] == 6) ||			
		(kingPosition[0] == 5 && kingPosition[1] == 6) ||			
		(kingPosition[0] == 6 && kingPosition[1] == 6) ||			
		(kingPosition[0] == 2 && kingPosition[1] == 3) ||			
		(kingPosition[0] == 2 && kingPosition[1] == 4) ||		
		(kingPosition[0] == 2 && kingPosition[1] == 5) ||			
		(kingPosition[0] == 6 && kingPosition[1] == 3) ||			
		(kingPosition[0] == 6 && kingPosition[1] == 4) ||			
		(kingPosition[0] == 6 && kingPosition[1] == 5)) {
			h_white = 10;
		}
		
		// checking third ring
		if((kingPosition[0] == 1 && kingPosition[1] == 1) ||
		(kingPosition[0] == 2 && kingPosition[1] == 1) ||
		(kingPosition[0] == 3 && kingPosition[1] == 1) ||
		(kingPosition[0] == 5 && kingPosition[1] == 1) ||
		(kingPosition[0] == 6 && kingPosition[1] == 1) ||
		(kingPosition[0] == 7 && kingPosition[1] == 1) ||
		(kingPosition[0] == 1 && kingPosition[1] == 7) ||
		(kingPosition[0] == 2 && kingPosition[1] == 7) ||
		(kingPosition[0] == 3 && kingPosition[1] == 7) ||
		(kingPosition[0] == 5 && kingPosition[1] == 7) ||
		(kingPosition[0] == 6 && kingPosition[1] == 7) ||
		(kingPosition[0] == 7 && kingPosition[1] == 7) ||
		(kingPosition[0] == 1 && kingPosition[1] == 2) ||
		(kingPosition[0] == 1 && kingPosition[1] == 3) ||
		(kingPosition[0] == 1 && kingPosition[1] == 5) ||
		(kingPosition[0] == 1 && kingPosition[1] == 6) ||
		(kingPosition[0] == 1 && kingPosition[1] == 7) ||
		(kingPosition[0] == 7 && kingPosition[1] == 2) ||
		(kingPosition[0] == 7 && kingPosition[1] == 3) ||
		(kingPosition[0] == 7 && kingPosition[1] == 5) ||
		(kingPosition[0] == 7 && kingPosition[1] == 6) ||
		(kingPosition[0] == 7 && kingPosition[1] == 7)) {
			h_white = 15;
		}
		
		// checking if it is in the escape area
		if(state.getArea(kingPosition[0], kingPosition[1]).equals(Area.ESCAPES)) {
			h_white = 17;
		}
		
		int whiteThatSurrounKing = 0;
		// get number of white pawns that surround the king
		if(state.getPawn(kingPosition[0], kingPosition[1] - 1).equals(Pawn.WHITE)) {
			whiteThatSurrounKing++;
		}
		if(state.getPawn(kingPosition[0], kingPosition[1] + 1).equals(Pawn.WHITE)) {
			whiteThatSurrounKing++;
		}
		if(state.getPawn(kingPosition[0] - 1, kingPosition[1]).equals(Pawn.WHITE)) {
			whiteThatSurrounKing++;
		}
		if(state.getPawn(kingPosition[0] + 1, kingPosition[1]).equals(Pawn.WHITE)) {
			whiteThatSurrounKing++;
		}
		if(whiteThatSurrounKing == 0 || whiteThatSurrounKing == 4) {
			h_white--;
		} else if(whiteThatSurrounKing == 1) {
			h_white--;
		} else if(whiteThatSurrounKing == 2) {
			h_white = h_white + 1;
		} else if(whiteThatSurrounKing == 3) {
			h_white = h_white + 2;
		}
		if(h_white > 17) {
			h_white = 17;
		}
	
		return h_white;
	}
	
	private int[] getKingPosition(StateTablut state) {
		for (int i = 0; i < state.getBoard().length; i++) {
			for (int j = 0; j < state.getBoard().length; j++) {
				if(state.getPawn(i, j) == Pawn.KING) {
					return new int[] {i,j};
				}
			}
		}
		return null;
	}
	
}
