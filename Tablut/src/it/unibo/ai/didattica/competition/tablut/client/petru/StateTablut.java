package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.List;



public class StateTablut extends State implements Serializable {

	private static final long serialVersionUID = 1L;
	private double utility = -1; // 1: win for X, 0: win for O, 0.5: draw
	
	public StateTablut() {
		super();
		super.setBoard(new Pawn[State.WIDTH][State.HEIGHT]);
		super.setBoardArea(new Area[State.WIDTH][State.WIDTH]);
		this.initBoard();
	}
	
	
	public List<XYWho> getAllLegalMoves() {
		Pawn[][] currentBoardState = super.getBoard();
		// all possible moves for white (without constraints)
		if(this.getTurn().equals(Turn.WHITE)) {
			List<XYWho> whiteLegalMoves = new ArrayList<>();
			List<XYWho> whitePositions = new ArrayList<>();
			XYWho buf;
			for (int i = 0; i < currentBoardState.length; i++) {
				for (int j = 0; j < currentBoardState.length; j++) {
					if (super.getPawn(i, j).equalsPawn(State.Pawn.WHITE.toString()))  {
						buf = new XYWho(i, j, new int[]{i, j}, false);
						whitePositions.add(buf);
						
					}
				}
			}
			// for each (i, j) in white position, try every possible move
			for (XYWho whitePawn : whitePositions) {
				// move each pawn vertically
				for (int j = 0; j < currentBoardState.length; j++) {
					// (x, y - j) UP
					if(((whitePawn.getY() - j) >= 0) && super.getPawn(whitePawn.getX(), whitePawn.getY() - j) == Pawn.EMPTY && (super.getArea(whitePawn.getX(), whitePawn.getY() - j) != Area.CAMPS) && (this.getArea(whitePawn.getX(), whitePawn.getY() - j) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = whitePawn.getY() - 1; f > whitePawn.getY() - j; f--) {
							if(super.getPawn(whitePawn.getX(), f) != Pawn.EMPTY) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							
							whiteLegalMoves.add(new XYWho(whitePawn.getX(), whitePawn.getY() - j, new int[]{whitePawn.getX(), whitePawn.getY()}, false));
						}
					}
					// (x, y + j) DOWN
					if(((whitePawn.getY() + j) < currentBoardState.length) && super.getPawn(whitePawn.getX(), whitePawn.getY() + j) == Pawn.EMPTY && (super.getArea(whitePawn.getX(), whitePawn.getY() + j) != Area.CAMPS) && (super.getArea(whitePawn.getX(), whitePawn.getY() + j) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = whitePawn.getY() + 1; f < whitePawn.getY() + j; f++) {
							if(super.getPawn(whitePawn.getX(), f) != Pawn.EMPTY) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							whiteLegalMoves.add(new XYWho(whitePawn.getX(), whitePawn.getY() + j, new int[]{whitePawn.getX(), whitePawn.getY()}, false));
						}
					}
				}
				// move each pawn horizontally
				for (int i = 0; i < currentBoardState.length; i++) {
					// (x - i, y) LEFT
					if(((whitePawn.getX() - i) >= 0) && super.getPawn(whitePawn.getX() - i, whitePawn.getY()) == Pawn.EMPTY && (super.getArea(whitePawn.getX() - i, whitePawn.getY()) != Area.CAMPS) && (this.getArea(whitePawn.getX() - i, whitePawn.getY()) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = whitePawn.getX() - 1; f > whitePawn.getX() - i; f--) {
							if(super.getPawn(f, whitePawn.getY()) != Pawn.EMPTY) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							whiteLegalMoves.add(new XYWho(whitePawn.getX() - i, whitePawn.getY(), new int[]{whitePawn.getX(), whitePawn.getY()}, false));
						}
					}
					// (x + i, y) RIGHT
					if(((whitePawn.getX() + i) < currentBoardState.length) && super.getPawn(whitePawn.getX() + i, whitePawn.getY()) == Pawn.EMPTY && (super.getArea(whitePawn.getX() + i, whitePawn.getY()) != Area.CAMPS) && (super.getArea(whitePawn.getX() + i, whitePawn.getY()) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = whitePawn.getX() + 1; f < whitePawn.getX() + i; f++) {
							if(super.getPawn(f, whitePawn.getY()) != Pawn.EMPTY) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							whiteLegalMoves.add(new XYWho(whitePawn.getX() + i, whitePawn.getY(), new int[]{whitePawn.getX(), whitePawn.getY()}, false));
						}
					}
				}
					
			}
			// add all possible moves for the king. If he leaves the castle, he cannot enter anymore
			int[] king_position = super.getKingPosition();
			for (int j = 0; j < currentBoardState.length; j++) {
				// (x, y - j) UP
				if(((king_position[1] - j) >= 0) && super.getPawn(king_position[0], king_position[1] - j) == Pawn.EMPTY && (super.getArea(king_position[0], king_position[1] - j) != Area.CAMPS) && (king_position[0] != State.KING_POSITION || (king_position[1] - j) != State.KING_POSITION)) {
					whiteLegalMoves.add(new XYWho(king_position[0], king_position[1] - j, new int[]{king_position[0], king_position[1]}, false));
				}
				// (x, y + j) DOWN
				if(((king_position[1] + j) < currentBoardState.length) && super.getPawn(king_position[0], king_position[1] + j) == Pawn.EMPTY && (super.getArea(king_position[0], king_position[1] + j) != Area.CAMPS) && (king_position[0] != State.KING_POSITION | (king_position[1] + j) != State.KING_POSITION)) {
					whiteLegalMoves.add(new XYWho(king_position[0], king_position[1] + j, new int[]{king_position[0], king_position[1]}, false));
				}
			}
			for (int i = 0; i < currentBoardState.length; i++) {
				// (x - i, y) LEFT
				if(((king_position[0] - i) >= 0) && super.getPawn(king_position[0] - i, king_position[1]) == Pawn.EMPTY && (super.getArea(king_position[0] - i, king_position[1]) != Area.CAMPS) && ((king_position[0] - i) != State.KING_POSITION || (king_position[1]) != State.KING_POSITION)) {
					whiteLegalMoves.add(new XYWho(king_position[0] - i, king_position[1], new int[]{king_position[0], king_position[1]}, false));
				}
				// (x + i, y) RIGHT
				if(((king_position[0] + i) < currentBoardState.length) && super.getPawn(king_position[0] + i, king_position[1]) == Pawn.EMPTY && (super.getArea(king_position[0] + i, king_position[1]) != Area.CAMPS) && ((king_position[0] + i) != State.KING_POSITION || (king_position[1]) != State.KING_POSITION)) {
					whiteLegalMoves.add(new XYWho(king_position[0] + i, king_position[1], new int[]{king_position[0], king_position[1]}, false));
				}
			}
			return whiteLegalMoves;
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		} else {
			// all possible moves for black
			List<XYWho> blackLegalMoves = new ArrayList<>();
			List<XYWho> blackPositions = new ArrayList<>();
			XYWho buf;
			for (int i = 0; i < currentBoardState.length; i++) {
				for (int j = 0; j < currentBoardState.length; j++) {
					if (this.getPawn(i, j).equalsPawn(State.Pawn.BLACK.toString()))  {
						buf = new XYWho(i, j, new int[]{i, j}, false); //BUG in this false
						blackPositions.add(buf);
					}
				}
			}
			// for each (i, j) in black position, try every possible move
			for (XYWho blackPawn : blackPositions) {
				// move each pawn vertically
				for (int j = 0; j < currentBoardState.length; j++) {
					// (x, y - j) UP
					if(((blackPawn.getY() - j) >= 0) && super.getPawn(blackPawn.getX(), blackPawn.getY() - j) == Pawn.EMPTY && (super.getArea(blackPawn.getX(), blackPawn.getY() - j) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = blackPawn.getY() - 1; f > blackPawn.getY() - j; f--) {
							if(super.getPawn(blackPawn.getX(), f) != Pawn.EMPTY) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							// BUG here, a black pawn if leaves his camps, cannot go in the other camps (ACTUALLY this is ok)
							//if((blackPawn.hasLeftTheCamp() && (super.getArea(blackPawn.getX(), blackPawn.getY() - j) != Area.CAMPS)) || (!blackPawn.hasLeftTheCamp())) {
								blackLegalMoves.add(new XYWho(blackPawn.getX(), blackPawn.getY() - j, new int[]{blackPawn.getX(), blackPawn.getY()}, false));
							//}
						}
					}
					// (x, y + j) DOWN
					if(((blackPawn.getY() + j) < currentBoardState.length) && super.getPawn(blackPawn.getX(), blackPawn.getY() + j) == Pawn.EMPTY && (super.getArea(blackPawn.getX(), blackPawn.getY() + j) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = blackPawn.getY() + 1; f < blackPawn.getY() + j; f++) {
							if(super.getPawn(blackPawn.getX(), f) != Pawn.EMPTY) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							blackLegalMoves.add(new XYWho(blackPawn.getX(), blackPawn.getY() + j, new int[]{blackPawn.getX(), blackPawn.getY()}, false));
						}
					}
				}
				// move each pawn horizontally
				for (int i = 0; i < currentBoardState.length; i++) {
					// (x - i, y) LEFT
					if(((blackPawn.getX() - i) >= 0) && super.getPawn(blackPawn.getX() - i, blackPawn.getY()) == Pawn.EMPTY && (super.getArea(blackPawn.getX() - i, blackPawn.getY()) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = blackPawn.getX() - 1; f > blackPawn.getX() - i; f--) {
							if(super.getPawn(f, blackPawn.getY()) != Pawn.EMPTY) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							blackLegalMoves.add(new XYWho(blackPawn.getX() - i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}, false));
						}
					}
					// (x + i, y) RIGHT
					if(((blackPawn.getX() + i) < currentBoardState.length) && super.getPawn(blackPawn.getX() + i, blackPawn.getY()) == Pawn.EMPTY && (super.getArea(blackPawn.getX() + i, blackPawn.getY()) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = blackPawn.getX() + 1; f < blackPawn.getX() + i; f++) {
							if(super.getPawn(f, blackPawn.getY()) != Pawn.EMPTY) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							blackLegalMoves.add(new XYWho(blackPawn.getX() + i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}, false));
						}
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
		s.printBoardArea();
		//System.out.println(white.size());
		
	}

	
	
	
	
	
	
	public double getUtility() {
		return this.utility;
	}
	
	
	
	
	
	
	
	
	
	
	private void initBoard() {
		// initialize pawns on board
		for (int i = 0; i < super.getBoard().length; i++) {
			for (int j = 0; j < super.getBoard().length; j++) {
				super.setPawn(i,  j, Pawn.EMPTY);
			}
		}
		super.setPawn(State.KING_POSITION,  State.KING_POSITION, Pawn.THRONE);
//		super.setPawn(State.KING_POSITION,  State.KING_POSITION, Pawn.KING);
		
		super.setPawn(2,  4, Pawn.WHITE);
		super.setPawn(3,  4, Pawn.WHITE);
		super.setPawn(5,  4, Pawn.WHITE);
		super.setPawn(6,  4, Pawn.WHITE);
		super.setPawn(4,  2, Pawn.WHITE);
		super.setPawn(4,  3, Pawn.WHITE);
		super.setPawn(4,  5, Pawn.WHITE);
		super.setPawn(4,  6, Pawn.WHITE);
//		super.setPawn(1,  2, Pawn.WHITE);
//		super.setPawn(3,  2, Pawn.WHITE);
		
		
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
//		super.setPawn(2,  0, Pawn.BLACK);
//		super.setPawn(2,  4, Pawn.BLACK);
		
		// initialize area on board
		for (int i = 0; i < super.getBoardArea().length; i++) {
			for (int j = 0; j < super.getBoardArea().length; j++) {
				super.setArea(i, j, Area.NORMAL);
			}
		}

		super.setArea(4, 4, Area.CASTLE);
//		super.setArea(2, 2, Area.CASTLE);
		
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
//		super.setArea(2, 0, Area.CAMPS);
//		super.setArea(2, 4, Area.CAMPS);
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
