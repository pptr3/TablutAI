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
//		if(state.depth == 10) {
//			return true;
//		} else {
//			state.depth++;
//			return false;
//		}
		return state.getUtility() != -1;
	}

	@Override
	public StateTablut getResult(StateTablut state, XYWho action) {
		if(state.getUtility() == -1) {
			System.out.println("StatePOTRIIII");
			printBoard(state.getBoard());
			System.out.println("actionPOTRIIII:" + action);
			
			if(state.getTurn().equals(Turn.WHITE)) {
				state.setPawn(action.getX(), action.getY(), Pawn.WHITE);
				state.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				state.setTurn(Turn.BLACK);
			} else {
				state.setPawn(action.getX(), action.getY(), Pawn.BLACK);
				state.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				state.setTurn(Turn.WHITE);
			}
			System.out.println("\n");
			System.out.println("Result");
			printBoard(state.getBoard());
			System.exit(0);
			
			// analyze utility
			int a =  new Random().nextInt(100);
			if(a >= 90) {
				state.setUtility(state.getTurn().equals(Turn.WHITE) ? 1 : 0);
			}
			
			return state;
		}
		return state;
	}
	
	static void printBoard(Pawn[][] c) {
		for(int i=0; i < 9; i++) {
			for(int j=0; j < 9; j++) {
				System.out.print(c[i][j]+ "  ");
			}
			System.out.println("\n");
		}
	}
	
}
