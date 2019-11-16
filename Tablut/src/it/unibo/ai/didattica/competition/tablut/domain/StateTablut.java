package it.unibo.ai.didattica.competition.tablut.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aima.core.util.datastructure.XYLocation;
import it.unibo.ai.didattica.competition.tablut.client.petru.aima.XYWho;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;


/**
 * This class represents a state of a match of Tablut (classical or second
 * version)
 * 
 * @author A.Piretti
 * 
 */
public class StateTablut extends State implements Serializable {

	private static final long serialVersionUID = 1L;
	private double utility = Integer.MIN_VALUE; // 1: win for X, 0: win for O, 0.5: draw

	public StateTablut() {
		super();
		this.board = new Pawn[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.board[i][j] = Pawn.EMPTY;
			}
		}

		this.board[4][4] = Pawn.THRONE;
		this.board[4][4] = Pawn.KING;

		this.board[2][4] = Pawn.WHITE;
		this.board[3][4] = Pawn.WHITE;
		this.board[5][4] = Pawn.WHITE;
		this.board[6][4] = Pawn.WHITE;
		this.board[4][2] = Pawn.WHITE;
		this.board[4][3] = Pawn.WHITE;
		this.board[4][5] = Pawn.WHITE;
		this.board[4][6] = Pawn.WHITE;

		this.board[0][3] = Pawn.BLACK;
		this.board[0][4] = Pawn.BLACK;
		this.board[0][5] = Pawn.BLACK;
		this.board[1][4] = Pawn.BLACK;
		this.board[8][3] = Pawn.BLACK;
		this.board[8][4] = Pawn.BLACK;
		this.board[8][5] = Pawn.BLACK;
		this.board[7][4] = Pawn.BLACK;
		this.board[3][0] = Pawn.BLACK;
		this.board[4][0] = Pawn.BLACK;
		this.board[5][0] = Pawn.BLACK;
		this.board[4][1] = Pawn.BLACK;
		this.board[3][8] = Pawn.BLACK;
		this.board[4][8] = Pawn.BLACK;
		this.board[5][8] = Pawn.BLACK;
		this.board[4][7] = Pawn.BLACK;
		
		// current turn is WHITE, set the next to BLACK (maybe?)
		this.setTurn(Turn.BLACK);
	}
	
	// maybe a second constructor?
	
	
	public List<XYWho> getAllLegalMoves() {
		Pawn[][] currentBoardState = this.getBoard();
		
		// all possible moves for white (without constraints)
		if(this.turn.equals(Turn.WHITE)) {
			List<XYWho> whiteLegalMoves = new ArrayList<>();
			List<XYWho> whitePositions = new ArrayList<>();
			XYWho buf;
			for (int i = 0; i < currentBoardState.length; i++) {
				for (int j = 0; j < currentBoardState.length; j++) {
					if (this.getPawn(i, j).equalsPawn(State.Pawn.WHITE.toString()) 
							|| this.getPawn(i, j).equalsPawn(State.Pawn.KING.toString()))  {
						buf = new XYWho(i, j, new int[]{i, j});
						whitePositions.add(buf);
						
					}
				}
			}
			// for each (i, j) in white position, try every possible move and add to result List wheter is a lega move (manhattan and is emtpy)
			for (XYWho whitePawn : whitePositions) {
				// move each pawn vertically
				for (int j = 0; j < currentBoardState.length; j++) {
					// NOTE: here I am moving each white pawn up and down no matter if it actually cannot move because is blocked, TODO: to improve
					if(this.getPawn(whitePawn.getX(), whitePawn.getY() - j) == Pawn.EMPTY) { // (x, y - j) UP
						whiteLegalMoves.add(new XYWho(whitePawn.getX(), whitePawn.getY() - j, new int[]{whitePawn.getX(), whitePawn.getY()}));
					}
					if(this.getPawn(whitePawn.getX(), whitePawn.getY() + j) == Pawn.EMPTY) { // (x, y + j) DOWN
						whiteLegalMoves.add(new XYWho(whitePawn.getX(), whitePawn.getY() + j, new int[]{whitePawn.getX(), whitePawn.getY()}));
					}
				}
				// move each pawn horizontally
				for (int i = 0; i < currentBoardState.length; i++) {
					if(this.getPawn(whitePawn.getX() - i, whitePawn.getY()) == Pawn.EMPTY) { // (x - i, y) LEFT
						whiteLegalMoves.add(new XYWho(whitePawn.getX() - i, whitePawn.getY(), new int[]{whitePawn.getX(), whitePawn.getY()}));
					}
					if(this.getPawn(whitePawn.getX() + i, whitePawn.getY()) == Pawn.EMPTY) { // (x + i, y) RIGHT
						whiteLegalMoves.add(new XYWho(whitePawn.getX() + i, whitePawn.getY(), new int[]{whitePawn.getX(), whitePawn.getY()}));
					}
				}
					
			}
			return whiteLegalMoves;
			
		} else {
			// all possible moves for black (without constraints)
			List<XYWho> blackLegalMoves = new ArrayList<>();
			List<XYWho> blackPositions = new ArrayList<>();
			XYWho buf;
			for (int i = 0; i < currentBoardState.length; i++) {
				for (int j = 0; j < currentBoardState.length; j++) {
					if (this.getPawn(i, j).equalsPawn(State.Pawn.BLACK.toString()))  {
						buf = new XYWho(i, j, new int[]{i, j});
						blackPositions.add(buf);
						
					}
				}
			}
			// for each (i, j) in black position, try every possible move and add to result List wheter is a lega move (manhattan and is emtpy)
			for (XYWho blackPawn : blackPositions) {
				// move each pawn vertically
				for (int j = 0; j < currentBoardState.length; j++) {
					// NOTE: here I am moving each white pawn up and down no matter if it actually cannot move because is blocked, TODO: to improve
					if(this.getPawn(blackPawn.getX(), blackPawn.getY() - j) == Pawn.EMPTY) { // (x, y - j) UP
						blackLegalMoves.add(new XYWho(blackPawn.getX(), blackPawn.getY() - j, new int[]{blackPawn.getX(), blackPawn.getY()}));
					}
					if(this.getPawn(blackPawn.getX(), blackPawn.getY() + j) == Pawn.EMPTY) { // (x, y + j) DOWN
						blackLegalMoves.add(new XYWho(blackPawn.getX(), blackPawn.getY() + j, new int[]{blackPawn.getX(), blackPawn.getY()}));
					}
				}
				// move each pawn horizontally
				for (int i = 0; i < currentBoardState.length; i++) {
					if(this.getPawn(blackPawn.getX() - i, blackPawn.getY()) == Pawn.EMPTY) { // (x - i, y) LEFT
						blackLegalMoves.add(new XYWho(blackPawn.getX() - i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}));
					}
					if(this.getPawn(blackPawn.getX() + i, blackPawn.getY()) == Pawn.EMPTY) { // (x + i, y) RIGHT
						blackLegalMoves.add(new XYWho(blackPawn.getX() + i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}));
					}
				}
					
			}
			return blackLegalMoves;
			
		}
			
	} 
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public StateTablut clone() {
		StateTablut result = new StateTablut();

		Pawn oldboard[][] = this.getBoard();
		Pawn newboard[][] = result.getBoard();

		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				newboard[i][j] = oldboard[i][j];
			}
		}

		result.setBoard(newboard);
		result.setTurn(this.turn);
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		StateTablut other = (StateTablut) obj;
		if (this.board == null) {
			if (other.board != null)
				return false;
		} else {
			if (other.board == null)
				return false;
			if (this.board.length != other.board.length)
				return false;
			if (this.board[0].length != other.board[0].length)
				return false;
			for (int i = 0; i < other.board.length; i++)
				for (int j = 0; j < other.board[i].length; j++)
					if (!this.board[i][j].equals(other.board[i][j]))
						return false;
		}
		if (this.turn != other.turn)
			return false;
		return true;
	}

}
