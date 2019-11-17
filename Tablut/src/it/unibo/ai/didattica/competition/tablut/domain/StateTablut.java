package it.unibo.ai.didattica.competition.tablut.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.util.datastructure.XYLocation;
import it.unibo.ai.didattica.competition.tablut.client.petru.aima.XYWho;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;


public class StateTablut extends State implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public StateTablut() {
		super();
		super.setBoard(new Pawn[State.WIDTH][State.HEIGHT]);
		super.setBoardArea(new Area[State.WIDTH][State.WIDTH]);
		this.initBoard();
		
	}
	
	
	public List<XYWho> getAllLegalMoves() {
		this.setTurn(Turn.WHITE);
		Pawn[][] currentBoardState = this.getBoard();
		// all possible moves for white (without constraints)
		if(this.getTurn().equals(Turn.WHITE)) {
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
			// for each (i, j) in white position, try every possible move and add to result List whether is a lega move (manhattan and is emtpy)
			for (XYWho whitePawn : whitePositions) {
				// move each pawn vertically
				for (int j = 0; j < currentBoardState.length; j++) {
					// NOTE: here I am moving each white pawn up and down no matter if it actually cannot move because is blocked, TODO: to improve
					if(((whitePawn.getY() - j) >= 0) && this.getPawn(whitePawn.getX(), whitePawn.getY() - j) == Pawn.EMPTY) { // (x, y - j) UP
						whiteLegalMoves.add(new XYWho(whitePawn.getX(), whitePawn.getY() - j, new int[]{whitePawn.getX(), whitePawn.getY()}));
					}
					if(((whitePawn.getY() + j) < currentBoardState.length) && this.getPawn(whitePawn.getX(), whitePawn.getY() + j) == Pawn.EMPTY) { // (x, y + j) DOWN
						whiteLegalMoves.add(new XYWho(whitePawn.getX(), whitePawn.getY() + j, new int[]{whitePawn.getX(), whitePawn.getY()}));
					}
				}
				// move each pawn horizontally
				for (int i = 0; i < currentBoardState.length; i++) {
					if(((whitePawn.getX() - i) >= 0) && this.getPawn(whitePawn.getX() - i, whitePawn.getY()) == Pawn.EMPTY) { // (x - i, y) LEFT
						whiteLegalMoves.add(new XYWho(whitePawn.getX() - i, whitePawn.getY(), new int[]{whitePawn.getX(), whitePawn.getY()}));
					}
					if(((whitePawn.getX() + i) < currentBoardState.length) && this.getPawn(whitePawn.getX() + i, whitePawn.getY()) == Pawn.EMPTY) { // (x + i, y) RIGHT
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
				for (int j = 0; j < currentBoardState.length - 1; j++) {
					// NOTE: here I am moving each white pawn up and down no matter if it actually cannot move because is blocked, TODO: to improve
					if(((blackPawn.getY() - j) >= 0) && this.getPawn(blackPawn.getX(), blackPawn.getY() - j) == Pawn.EMPTY) { // (x, y - j) UP
						blackLegalMoves.add(new XYWho(blackPawn.getX(), blackPawn.getY() , new int[]{blackPawn.getX(), blackPawn.getY()}));
					}
					if(((blackPawn.getY() + j) < currentBoardState.length) && this.getPawn(blackPawn.getX(), blackPawn.getY() + j) == Pawn.EMPTY) { // (x, y + j) DOWN
						blackLegalMoves.add(new XYWho(blackPawn.getX(), blackPawn.getY() + j, new int[]{blackPawn.getX(), blackPawn.getY()}));
					}
				}
				// move each pawn horizontally
				for (int i = 0; i < currentBoardState.length - 1; i++) {
					if(((blackPawn.getX() - i) >= 0) && this.getPawn(blackPawn.getX() - i, blackPawn.getY()) == Pawn.EMPTY) { // (x - i, y) LEFT
						blackLegalMoves.add(new XYWho(blackPawn.getX() - i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}));
					}
					if(((blackPawn.getX() + i) < currentBoardState.length) && this.getPawn(blackPawn.getX() + i, blackPawn.getY()) == Pawn.EMPTY) { // (x + i, y) RIGHT
						blackLegalMoves.add(new XYWho(blackPawn.getX() + i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}));
					}
				}
					
			}
			return blackLegalMoves;
			
		}
			
	} 
	//TODO: check whether for each white and black they do their legal moves (check it before add contraints and when I will add constraints)
	public static void main(String[] args) {
		StateTablut s = new StateTablut();
		List<XYWho> white = s.getAllLegalMoves();
		for(int i = 0; i < white.size(); i++) {
			//System.out.println("who: (" + white.get(i).getWho()[0] + ", " + white.get(i).getWho()[1] +") x: "+white.get(i).getX()+ " y: " + white.get(i).getY());
		}
		s.printBoard();		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void initBoard() {
		// initialize pawns on board
		for (int i = 0; i < super.getBoard().length; i++) {
			for (int j = 0; j < super.getBoard().length; j++) {
				super.setPawn(i,  j, Pawn.EMPTY);
			}
		}
		//super.setPawn(4,  4, Pawn.THRONE);
		super.setPawn(4,  4, Pawn.KING);
		
		super.setPawn(2,  4, Pawn.WHITE);
		super.setPawn(3,  4, Pawn.WHITE);
		super.setPawn(5,  4, Pawn.WHITE);
		super.setPawn(6,  4, Pawn.WHITE);
		super.setPawn(4,  2, Pawn.WHITE);
		super.setPawn(4,  3, Pawn.WHITE);
		super.setPawn(4,  5, Pawn.WHITE);
		super.setPawn(4,  6, Pawn.WHITE);
		
		
		
		super.setPawn(0,  3, Pawn.BLACK);
		super.setPawn(0,  4, Pawn.BLACK);
		super.setPawn(0,  5, Pawn.BLACK);
		super.setPawn(1,  4, Pawn.BLACK);
		super.setPawn(8,  3, Pawn.BLACK);
		super.setPawn(8,  4, Pawn.BLACK);
		super.setPawn(8,  5, Pawn.BLACK);
		super.setPawn(7,  4, Pawn.BLACK);
		super.setPawn(3,  0, Pawn.BLACK);
		super.setPawn(4,  0, Pawn.BLACK);
		super.setPawn(5,  0, Pawn.BLACK);
		super.setPawn(4,  1, Pawn.BLACK);
		super.setPawn(3,  8, Pawn.BLACK);
		super.setPawn(4,  8, Pawn.BLACK);
		super.setPawn(5,  8, Pawn.BLACK);
		super.setPawn(4,  7, Pawn.BLACK);
		
		// initialize area on board
		for (int i = 0; i < super.getBoardArea().length; i++) {
			for (int j = 0; j < super.getBoardArea().length; j++) {
				super.setArea(i, j, Area.NORMAL);
			}
		}

		super.setArea(4, 4, Area.CASTLE);
		
		super.setArea(0, 0, Area.ESCAPES);
		super.setArea(0, 1, Area.ESCAPES);
		super.setArea(0, 2, Area.ESCAPES);
		super.setArea(0, 6, Area.ESCAPES);
		super.setArea(0, 7, Area.ESCAPES);
		super.setArea(0, 8, Area.ESCAPES);
		super.setArea(1, 0, Area.ESCAPES);
		super.setArea(2, 0, Area.ESCAPES);
		super.setArea(6, 0, Area.ESCAPES);
		super.setArea(7, 0, Area.ESCAPES);
		super.setArea(8, 0, Area.ESCAPES);
		super.setArea(8, 1, Area.ESCAPES);
		super.setArea(8, 2, Area.ESCAPES);
		super.setArea(8, 6, Area.ESCAPES);
		super.setArea(8, 7, Area.ESCAPES);
		super.setArea(8, 8, Area.ESCAPES);
		super.setArea(1, 8, Area.ESCAPES);
		super.setArea(2, 8, Area.ESCAPES);
		super.setArea(6, 8, Area.ESCAPES);
		super.setArea(7, 8, Area.ESCAPES);

		super.setArea(0, 3, Area.CAMPS);
		super.setArea(0, 4, Area.CAMPS);
		super.setArea(0, 5, Area.CAMPS);
		super.setArea(1, 4, Area.CAMPS);
		super.setArea(8, 3, Area.CAMPS);
		super.setArea(8, 4, Area.CAMPS);
		super.setArea(8, 5, Area.CAMPS);
		super.setArea(7, 4, Area.CAMPS);
		super.setArea(3, 0, Area.CAMPS);
		super.setArea(4, 0, Area.CAMPS);
		super.setArea(5, 0, Area.CAMPS);
		super.setArea(4, 1, Area.CAMPS);
		super.setArea(3, 8, Area.CAMPS);
		super.setArea(4, 8, Area.CAMPS);
		super.setArea(5, 8, Area.CAMPS);
		super.setArea(4, 7, Area.CAMPS);
		
		// current turn is WHITE, set the next to BLACK (maybe?)
		super.setTurn(Turn.BLACK);
	}
	

	

	public StateTablut clone() {
		StateTablut result = new StateTablut();

		Pawn oldboard[][] = super.getBoard();
		Pawn newboard[][] = result.getBoard();

		for (int i = 0; i < super.getBoard().length; i++) {
			for (int j = 0; j < super.getBoard().length; j++) {
				newboard[i][j] = oldboard[i][j];
			}
		}

		result.setBoard(newboard);
		result.setTurn(this.getTurn());
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
		if (super.getBoard() == null) {
			if (other.getBoard() != null)
				return false;
		} else {
			if (other.getBoard() == null)
				return false;
			if (super.getBoard().length != other.getBoard().length)
				return false;
			if (super.getBoard()[0].length != other.getBoard()[0].length)
				return false;
			for (int i = 0; i < other.getBoard().length; i++)
				for (int j = 0; j < other.getBoard()[i].length; j++)
					if (!super.getBoard()[i][j].equals(other.getBoard()[i][j]))
						return false;
		}
		if (super.getTurn()!= other.getTurn())
			return false;
		return true;
	}

}
