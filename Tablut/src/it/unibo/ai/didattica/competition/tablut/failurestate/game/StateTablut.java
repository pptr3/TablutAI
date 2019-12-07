package it.unibo.ai.didattica.competition.tablut.failurestate.game;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.unibo.ai.didattica.competition.tablut.failurestate.game.StateTablut.Pawn;



public class StateTablut {

	private static final long serialVersionUID = 1L;
	
	public enum Turn {
		WHITE("W"), BLACK("B"), WHITEWIN("WW"), BLACKWIN("BW"), DRAW("D");
		private final String inner_turn;

		private Turn(String s) {
			this.inner_turn = s;
		}

		public boolean equalsTurn(String otherName) {
			return (otherName == null) ? false : this.inner_turn.equals(otherName);
		}

		public String toString() {
			return this.inner_turn;
		}
	}

	public enum Pawn {
		EMPTY(" "), WHITE("W"), BLACK("B"), THRONE("T"), KING("K");
		private final String pawn;

		private Pawn(String s) {
			this.pawn = s;
		}

		public boolean equalsPawn(String otherPawn) {
			return (otherPawn == null) ? false : this.pawn.equals(otherPawn);
		}

		public String toString() {
			return this.pawn;
		}

	}
	
	public enum Area {
		NORMAL("N"), CASTLE("K"), CAMPS("C"), ESCAPES("E");
		private final String area;

		private Area(String a) {
			this.area = a;
		}

		public boolean equalsArea(String otherArea) {
			return (otherArea == null) ? false : this.area.equals(otherArea);
		}

		public String toString() {
			return this.area;
		}
	}

	public static final int WIDTH = 9; 
	public static final int HEIGHT = 9; 
	public static final int KING_POSITION = 4;
	private Pawn board[][];
	private Area boardArea[][];
	private Turn turn;
	private int currentDepth;
	
	
	public StateTablut(int depth) {
		this.setBoard(new Pawn[StateTablut.WIDTH][StateTablut.HEIGHT]);
		this.setBoardArea(new Area[StateTablut.WIDTH][StateTablut.WIDTH]);
		this.initBoard();
		this.setTurn(Turn.WHITE);
		this.setCurrentDepth(depth);
	}

	public void applyMove(XYWho action) {
		if(this.getCurrentDepth() != 0) {
			if(this.getTurn().equals(Turn.WHITE)) {
				if(this.getPawn(action.getWho()[0], action.getWho()[1]).equals(Pawn.KING)) {
					this.setPawn(action.getX(), action.getY(), Pawn.KING);
				} else {
					this.setPawn(action.getX(), action.getY(), Pawn.WHITE);
				}
				this.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				this.setTurn(Turn.BLACK);
			} else {
				this.setPawn(action.getX(), action.getY(), Pawn.BLACK);
				this.setPawn(action.getWho()[0], action.getWho()[1], Pawn.EMPTY);
				this.setTurn(Turn.WHITE);
			}
			this.eat(action);
		}
	}
	
	public int getNumberCloseToTheKingOf(Pawn color) {
		int pawnsThatSurroundKing = 0;
		
		int[] kingPosition = this.getKingPosition();
		
		// cross
		if((kingPosition[1] - 1) >= 0 && this.getPawn(kingPosition[0], kingPosition[1] - 1).equals(color)) {
			pawnsThatSurroundKing++;
		}
		if((kingPosition[1] + 1) < (StateTablut.KING_POSITION * 2 + 1) && this.getPawn(kingPosition[0], kingPosition[1] + 1).equals(color)) {
			pawnsThatSurroundKing++;
		}
		if((kingPosition[0] - 1) >= 0 && this.getPawn(kingPosition[0] - 1, kingPosition[1]).equals(color)) {
			pawnsThatSurroundKing++;
		}
		if((kingPosition[0] + 1) < (StateTablut.KING_POSITION * 2 + 1) && this.getPawn(kingPosition[0] + 1, kingPosition[1]).equals(color)) {
			pawnsThatSurroundKing++;
		}
		
		// angle
		if((kingPosition[0] - 1 >= 0) && ((kingPosition[1] - 1) >= 0) && this.getPawn(kingPosition[0] - 1, kingPosition[1] - 1).equals(color)) {
			pawnsThatSurroundKing++;
		}
		if(((kingPosition[0] + 1) < (StateTablut.KING_POSITION * 2 + 1)) && ((kingPosition[1] + 1) < (StateTablut.KING_POSITION * 2 + 1)) 
				&& this.getPawn(kingPosition[0] + 1, kingPosition[1] + 1).equals(color)) {	
			pawnsThatSurroundKing++;
		}
		if(((kingPosition[0] - 1) >= 0) && ((kingPosition[1] + 1) < (StateTablut.KING_POSITION * 2 + 1))
				&& this.getPawn(kingPosition[0] - 1, kingPosition[1] + 1).equals(color)) {
			pawnsThatSurroundKing++;
		}
		if((kingPosition[0] + 1) < (StateTablut.KING_POSITION  * 2 + 1) && ((kingPosition[1] - 1) >= 0) 
				&& this.getPawn(kingPosition[0] + 1, kingPosition[1] - 1).equals(color)) {
			pawnsThatSurroundKing++;
		}
		
		return pawnsThatSurroundKing;	
	}
	
	public int getNumberOfCampsCloseToKing() {
		int campsCloseToTheKing = 0;
		
		int[] kingPosition = this.getKingPosition();
		
		// cross
		if((kingPosition[1] - 1) >= 0 && this.getArea(kingPosition[0], kingPosition[1] - 1).equals(Area.CAMPS)) {
			campsCloseToTheKing++;
		}
		if((kingPosition[1] + 1) < (StateTablut.KING_POSITION * 2 + 1) && this.getArea(kingPosition[0], kingPosition[1] + 1).equals(Area.CAMPS)) {
			campsCloseToTheKing++;
		}
		if((kingPosition[0] - 1) >= 0 && this.getArea(kingPosition[0] - 1, kingPosition[1]).equals(Area.CAMPS)) {
			campsCloseToTheKing++;
		}
		if((kingPosition[0] + 1) < (StateTablut.KING_POSITION * 2 + 1) && this.getArea(kingPosition[0] + 1, kingPosition[1]).equals(Area.CAMPS)) {
			campsCloseToTheKing++;
		}
		
		// angle
		if((kingPosition[0] - 1 >= 0) && ((kingPosition[1] - 1) >= 0) && this.getArea(kingPosition[0] - 1, kingPosition[1] - 1).equals(Area.CAMPS)) {
			campsCloseToTheKing++;
		}
		if(((kingPosition[0] + 1) < (StateTablut.KING_POSITION * 2 + 1)) && ((kingPosition[1] + 1) < (StateTablut.KING_POSITION * 2 + 1)) 
				&& this.getArea(kingPosition[0] + 1, kingPosition[1] + 1).equals(Area.CAMPS)) {	
			campsCloseToTheKing++;
		}
		if(((kingPosition[0] - 1) >= 0) && ((kingPosition[1] + 1) < (StateTablut.KING_POSITION * 2 + 1))
				&& this.getArea(kingPosition[0] - 1, kingPosition[1] + 1).equals(Area.CAMPS)) {
			campsCloseToTheKing++;
		}
		if((kingPosition[0] + 1) < (StateTablut.KING_POSITION  * 2 + 1) && ((kingPosition[1] - 1) >= 0) 
				&& this.getArea(kingPosition[0] + 1, kingPosition[1] - 1).equals(Area.CAMPS)) {
			campsCloseToTheKing++;
		}
		
		return campsCloseToTheKing;	
	}
		
	public int isTheKingInTheThrone() {
		int[] kingPosition = this.getKingPosition();
		return (kingPosition[0] == KING_POSITION && kingPosition[1] == KING_POSITION) ? 1 : 0;
	}
	
	public int isTheKingOnEscapeArea() {
		int[] kingPosition = this.getKingPosition();
		return (this.getArea(kingPosition[0], kingPosition[1]).equals(Area.ESCAPES)) ? 1 : 0;
	}
	
	public double getDistanceFromKingToClosestEscapeArea() {
		int[] kingPosition = this.getKingPosition();
		List<int[]> escapeCoord = new ArrayList<>();
		List<Double> min = new ArrayList<>();
		
		for (int i = 0; i < this.getBoard().length; i++) {
			for (int j = 0; j < this.getBoard().length; j++) {
				if(this.getArea(i, j).equals(Area.ESCAPES) && this.getPawn(i, j).equals(Pawn.EMPTY)) {
					escapeCoord.add(new int[] {i, j});
				}
			}
		}
		for (int i = 0; i < escapeCoord.size(); i++) {
			double deltaX = Math.abs(kingPosition[0] - (escapeCoord.get(i)[0]));
			double deltaY = Math.abs(kingPosition[1] - (escapeCoord.get(i)[1]));
			min.add(Math.sqrt(deltaX*deltaX + deltaY*deltaY));
		}
		return 10 - Collections.min(min);
	}
	
	public double getDistanceFromKingToAllBlacks() {
		int[] kingPosition = this.getKingPosition();
		List<int[]> blacksCoord = new ArrayList<>();
		List<Double> min = new ArrayList<>();
		
		for (int i = 0; i < this.getBoard().length; i++) {
			for (int j = 0; j < this.getBoard().length; j++) {
				if(this.getPawn(i, j).equals(Pawn.BLACK)) {
					blacksCoord.add(new int[] {i, j});
				}
			}
		}
		for (int i = 0; i < blacksCoord.size(); i++) {
			double deltaX = Math.abs(kingPosition[0] - (blacksCoord.get(i)[0]));
			double deltaY = Math.abs(kingPosition[1] - (blacksCoord.get(i)[1]));
			min.add(Math.sqrt(deltaX*deltaX + deltaY*deltaY));
		}
		return min.stream().mapToDouble(Double::doubleValue).sum();
	}

	public int getNumberOf(Pawn color) {
		int count = 0;
		for (int i = 0; i < this.getBoard().length; i++) {
			for (int j = 0; j < this.getBoard().length; j++) {
				if(this.getPawn(i, j).equals(color)) {
					count++;
				}
			}
		}
		return count;
	}
	
	private void eat(XYWho action) {
		if(this.getTurn().equals(Turn.WHITE)) {
			for(int i=0; i < this.getBoard().length; i++) {
				for(int j=0; j < this.getBoard().length; j++) {
					if(this.getPawn(i, j).equals(Pawn.WHITE)) {
						// normal capture
						/*case
						 * B
						 * W
						 * B
						 */
						if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i - 1, j).equals(Pawn.BLACK) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
							if((action.getX() == (i - 1) && action.getY() == (j)) || ((action.getX() == (i + 1) && action.getY() == (j)))) { // checking whether is 
								this.setPawn(i, j, Pawn.EMPTY);																				 // an active move
							}
						}
						/* case B W B */
						if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j - 1).equals(Pawn.BLACK) && this.getPawn(i, j + 1).equals(Pawn.BLACK)) {
							if((action.getX() == (i) && action.getY() == (j - 1)) || ((action.getX() == (i) && action.getY() == (j + 1)))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						
						
						// soldier captured with the castle as barrier
						if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i - 1, j).equals(Pawn.THRONE) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
							if((action.getX() == (i + 1) && action.getY() == (j))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i + 1, j).equals(Pawn.THRONE) && this.getPawn(i - 1, j).equals(Pawn.BLACK)) {
							if((action.getX() == (i - 1) && action.getY() == (j))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j - 1).equals(Pawn.THRONE) && this.getPawn(i, j + 1).equals(Pawn.BLACK)) {
							if((action.getX() == (i) && action.getY() == (j + 1))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j + 1).equals(Pawn.THRONE) && this.getPawn(i, j - 1).equals(Pawn.BLACK)) {
							if((action.getX() == (i) && action.getY() == (j - 1))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						
						// soldier captured by camp
						if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getArea(i - 1, j).equals(Area.CAMPS) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
							if((action.getX() == (i + 1) && action.getY() == (j))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getArea(i + 1, j).equals(Area.CAMPS) && this.getPawn(i - 1, j).equals(Pawn.BLACK)) {
							if((action.getX() == (i - 1) && action.getY() == (j))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getArea(i, j - 1).equals(Area.CAMPS) && this.getPawn(i, j + 1).equals(Pawn.BLACK)) {
							if((action.getX() == (i) && action.getY() == (j + 1))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getArea(i, j + 1).equals(Area.CAMPS) && this.getPawn(i, j - 1).equals(Pawn.BLACK)) {
							if((action.getX() == (i) && action.getY() == (j - 1))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
					}
				}
			}
		} else {
			for(int i=0; i < this.getBoard().length; i++) {
				for(int j=0; j < this.getBoard().length; j++) {
					if(this.getPawn(i, j).equals(Pawn.BLACK)) {
						// normal capture
						/*case
						 * W
						 * B
						 * W
						 */
						if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i - 1, j).equals(Pawn.WHITE) && this.getPawn(i + 1, j).equals(Pawn.WHITE)) {
							if((action.getX() == (i - 1) && action.getY() == (j)) || ((action.getX() == (i + 1) && action.getY() == (j)))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						/* case W B W */
						if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j - 1).equals(Pawn.WHITE) && this.getPawn(i, j + 1).equals(Pawn.WHITE)) {
							if((action.getX() == (i) && action.getY() == (j - 1)) || ((action.getX() == (i) && action.getY() == (j + 1)))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						
						
						// soldier captured with the castle as barrier
						if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i - 1, j).equals(Pawn.THRONE) && this.getPawn(i + 1, j).equals(Pawn.WHITE)) {
							if((action.getX() == (i + 1) && action.getY() == (j))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i + 1, j).equals(Pawn.THRONE) && this.getPawn(i - 1, j).equals(Pawn.WHITE)) {
							if((action.getX() == (i - 1) && action.getY() == (j))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j - 1).equals(Pawn.THRONE) && this.getPawn(i, j + 1).equals(Pawn.WHITE)) {
							if((action.getX() == (i) && action.getY() == (j + 1))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j + 1).equals(Pawn.THRONE) && this.getPawn(i, j - 1).equals(Pawn.WHITE)) {
							if((action.getX() == (i) && action.getY() == (j - 1))) {
								this.setPawn(i, j, Pawn.EMPTY);
							}
						}
						if(!this.getArea(i, j).equals(Area.CAMPS)) {
							// soldier captured by camp
							if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getArea(i - 1, j).equals(Area.CAMPS) && this.getPawn(i + 1, j).equals(Pawn.WHITE)) {
								if((action.getX() == (i + 1) && action.getY() == (j))) {
									this.setPawn(i, j, Pawn.EMPTY);
								}
							}
							if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getArea(i + 1, j).equals(Area.CAMPS) && this.getPawn(i - 1, j).equals(Pawn.WHITE)) {
								if((action.getX() == (i - 1) && action.getY() == (j))) {
									this.setPawn(i, j, Pawn.EMPTY);
								}
							}
							if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getArea(i, j - 1).equals(Area.CAMPS) && this.getPawn(i, j + 1).equals(Pawn.WHITE)) {
								if((action.getX() == (i) && action.getY() == (j + 1))) {
									this.setPawn(i, j, Pawn.EMPTY);
								}
							}
							if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getArea(i, j + 1).equals(Area.CAMPS) && this.getPawn(i, j - 1).equals(Pawn.WHITE)) {
								if((action.getX() == (i) && action.getY() == (j - 1))) {
									this.setPawn(i, j, Pawn.EMPTY);
								}
							}
						}
					}
				}
			}
			
		}
		
	}
	
	public int playerCannotMoveAnyPawn(Pawn color) {
		
		int moves = 0;
		// if the player `color` cannot move any pawn, the other player wins
		for(int i=0; i < this.getBoard().length; i++) {
			for(int j=0; j < this.getBoard().length; j++) {
				if(this.getPawn(i, j).equals(color)) {
					if(((i + 1) < this.getBoard().length) && this.getPawn(i + 1, j).equals(Pawn.EMPTY)) {
						moves++;
					}
					if(((i - 1) >= 0) && this.getPawn(i - 1, j).equals(Pawn.EMPTY)) {
						moves++;
					} 
					if(((j + 1) < this.getBoard().length) && this.getPawn(i, j + 1).equals(Pawn.EMPTY)) {
						moves++;
					} 
					if(((j - 1) >= 0) && this.getPawn(i, j - 1).equals(Pawn.EMPTY)) {
						moves++;
					}
				}
				
			}
		}
		return moves == 0 ? 1 : 0;
	}
	
	public int hasBlackWon() {
		// if the king has already won return 0, no matter whether the black can win in one move
		int[] kingPosition = this.getKingPosition();
		if(this.getArea(kingPosition[0], kingPosition[1]).equals(Area.ESCAPES)) {
			return 0;
		}
		// normal capture of the king
		for(int i=0; i < this.getBoard().length; i++) {
			for(int j=0; j < this.getBoard().length; j++) {
				if(this.getPawn(i, j).equals(Pawn.KING)) {
					/*case
					 * B
					 * K
					 * B
					 */
					if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getPawn(i - 1, j).equals(Pawn.BLACK) && this.getPawn(i + 1, j).equals(Pawn.BLACK) && 
							(!this.getArea(i, j).equals(Area.CASTLE) && (!this.getArea(i, j - 1).equals(Area.CASTLE) &&
							(!this.getArea(i, j + 1).equals(Area.CASTLE))))) {
								return 1;
					}
					/* case B K B */
					if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getPawn(i, j - 1).equals(Pawn.BLACK) && this.getPawn(i, j + 1).equals(Pawn.BLACK) && 
							(!this.getArea(i, j).equals(Area.CASTLE) && (!this.getArea(i - 1, j).equals(Area.CASTLE) &&
							(!this.getArea(i + 1, j).equals(Area.CASTLE))))) {
								return 1;
					}
				}
			}
		}
		
		// case where the king is in the castle and is surrounded by 4 blacks
		for(int i=0; i < this.getBoard().length; i++) {
			for(int j=0; j < this.getBoard().length; j++) {
				if(this.getPawn(i, j).equals(Pawn.KING) && this.getArea(i, j).equals(Area.CASTLE)) {
					if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getPawn(i, j - 1).equals(Pawn.BLACK) && this.getPawn(i, j + 1).equals(Pawn.BLACK)
							&& this.getPawn(i - 1, j).equals(Pawn.BLACK) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
						return 1;
					}
				}
			}
		}
		// case if the king is adjacent to the Castle, it must be surround on all the three free sides
		if(((StateTablut.KING_POSITION - 1) >= 0)
				&& this.getPawn(StateTablut.KING_POSITION, StateTablut.KING_POSITION - 1).equals(Pawn.KING)) {
			if(((StateTablut.KING_POSITION + 1) < this.getBoard().length) && ((StateTablut.KING_POSITION - 2) >= 0)
					&& ((StateTablut.KING_POSITION + 1) < this.getBoard().length)
					&& (this.getPawn(StateTablut.KING_POSITION, StateTablut.KING_POSITION - 2).equals(Pawn.BLACK)
					&& this.getPawn(StateTablut.KING_POSITION - 1, StateTablut.KING_POSITION - 1).equals(Pawn.BLACK)
					&& this.getPawn(StateTablut.KING_POSITION + 1, StateTablut.KING_POSITION - 1).equals(Pawn.BLACK))
					){
				return 1;
			}
			
		} else if(((StateTablut.KING_POSITION + 1) < this.getBoard().length)
				&& this.getPawn(StateTablut.KING_POSITION, StateTablut.KING_POSITION + 1).equals(Pawn.KING)) {
			if(((StateTablut.KING_POSITION + 2) < this.getBoard().length)
					&& ((StateTablut.KING_POSITION - 1) >= 0)
					&& (this.getPawn(StateTablut.KING_POSITION, StateTablut.KING_POSITION + 2).equals(Pawn.BLACK)
					&& this.getPawn(StateTablut.KING_POSITION + 1, StateTablut.KING_POSITION + 1).equals(Pawn.BLACK)
					&& this.getPawn(StateTablut.KING_POSITION - 1, StateTablut.KING_POSITION + 1).equals(Pawn.BLACK)) 
					) {
				return 1;
			}
		} else if(((StateTablut.KING_POSITION - 1) >= 0)
				&& this.getPawn(StateTablut.KING_POSITION - 1, StateTablut.KING_POSITION).equals(Pawn.KING)) {
			if((((StateTablut.KING_POSITION - 2) >= 0)
					&& ((StateTablut.KING_POSITION + 1) < this.getBoard().length)
					&& this.getPawn(StateTablut.KING_POSITION - 1, StateTablut.KING_POSITION - 1).equals(Pawn.BLACK)
					&& this.getPawn(StateTablut.KING_POSITION - 2, StateTablut.KING_POSITION).equals(Pawn.BLACK)
					&& this.getPawn(StateTablut.KING_POSITION - 1, StateTablut.KING_POSITION + 1).equals(Pawn.BLACK))
					) {
				return 1;
			}
			
		} else if(((StateTablut.KING_POSITION + 1) < this.getBoard().length)
				&& this.getPawn(StateTablut.KING_POSITION + 1, StateTablut.KING_POSITION).equals(Pawn.KING)) {
			if((((StateTablut.KING_POSITION + 2) < this.getBoard().length)
					&& ((StateTablut.KING_POSITION - 1) >= 0)
					&& this.getPawn(StateTablut.KING_POSITION + 1, StateTablut.KING_POSITION + 1).equals(Pawn.BLACK)
					&& this.getPawn(StateTablut.KING_POSITION + 2, StateTablut.KING_POSITION).equals(Pawn.BLACK)
					&& this.getPawn(StateTablut.KING_POSITION + 1, StateTablut.KING_POSITION - 1).equals(Pawn.BLACK))
					) {
				return 1;
			}
		}
		
		// case if the king is adjacent to a camp, it is sufficient to surround it with a checker on the opposite side of the camp.
		for(int i=0; i < this.getBoard().length; i++) {
			for(int j=0; j < this.getBoard().length; j++) {
				if(this.getPawn(i, j).equals(Pawn.KING)) {
					// normal case
					if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& this.getArea(i + 1, j).equals(Area.CAMPS) && this.getPawn(i - 1, j).equals(Pawn.BLACK)) {
						return 1;
					} else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& this.getArea(i - 1, j).equals(Area.CAMPS) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
						return 1;
					} else if(((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i, j - 1).equals(Area.CAMPS) && this.getPawn(i, j + 1).equals(Pawn.BLACK)) {
						return 1;
					} else if(((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i, j + 1).equals(Area.CAMPS) && this.getPawn(i, j - 1).equals(Pawn.BLACK)) {
						return 1;
					}
					
					// angle case
					if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i - 1, j).equals(Area.CAMPS) && this.getArea(i, j + 1).equals(Area.CAMPS)
							&& ((this.getPawn(i + 1, j).equals(Pawn.BLACK))
									|| (this.getPawn(i, j - 1).equals(Pawn.BLACK)))) {
						return 1;
					} else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i - 1, j).equals(Area.CAMPS) && this.getArea(i, j - 1).equals(Area.CAMPS)
							&& ((this.getPawn(i + 1, j).equals(Pawn.BLACK)) 
									|| (this.getPawn(i, j + 1).equals(Pawn.BLACK)))) {
						return 1;
					} else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i, j + 1).equals(Area.CAMPS) && this.getArea(i + 1, j).equals(Area.CAMPS)
							&& ((this.getPawn(i - 1, j).equals(Pawn.BLACK)) 
									|| (this.getPawn(i, j - 1).equals(Pawn.BLACK)))) {
						return 1;
					} else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i - 1, j).equals(Area.CAMPS) && this.getArea(i, j + 1).equals(Area.CAMPS)
							&& ((this.getPawn(i, j - 1).equals(Pawn.BLACK))
									|| (this.getPawn(i + 1, j).equals(Pawn.BLACK)))) {
						return 1;
					} else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i + 1, j).equals(Area.CAMPS) && this.getArea(i, j - 1).equals(Area.CAMPS)
							&& ((this.getPawn(i - 1, j).equals(Pawn.BLACK))
									|| (this.getPawn(i, j + 1).equals(Pawn.BLACK)))) {
						return 1;
					} else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i + 1, j).equals(Area.CAMPS) && this.getArea(i, j + 1).equals(Area.CAMPS)
							&& ((this.getPawn(i - 1, j).equals(Pawn.BLACK))
									|| (this.getPawn(i, j - 1).equals(Pawn.BLACK)))) {
						return 1;
					} else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i, j - 1).equals(Area.CAMPS) && this.getArea(i - 1, j).equals(Area.CAMPS)
							&& ((this.getPawn(i + 1, j).equals(Pawn.BLACK))
									|| (this.getPawn(i, j + 1).equals(Pawn.BLACK)))) {
						return 1;
					} else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
							&& ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
							&& this.getArea(i, j - 1).equals(Area.CAMPS) && this.getArea(i + 1, j).equals(Area.CAMPS)
							&& ((this.getPawn(i - 1, j).equals(Pawn.BLACK))
									|| (this.getPawn(i, j + 1).equals(Pawn.BLACK)))) {
						return 1;
					}
				}
			}
		}
		return 0;
	}

	public List<XYWho> getAllLegalMoves() {
		// all possible moves for white
		if(this.getTurn().equals(Turn.WHITE)) {
			List<XYWho> whiteLegalMoves = new ArrayList<>();
			List<XYWho> whitePositions = new ArrayList<>();
			XYWho buf;
			for (int i = 0; i < this.getBoard().length; i++) {
				for (int j = 0; j < this.getBoard().length; j++) {
					if (this.getPawn(i, j).equals(StateTablut.Pawn.WHITE) || this.getPawn(i, j).equals(StateTablut.Pawn.KING))  {
						buf = new XYWho(i, j, new int[]{i, j}, false);
						whitePositions.add(buf);
					}
				}
			}
			
			// for each (i, j) in white position, try every possible move
			for (XYWho whitePawn : whitePositions) {
				// move each pawn vertically
				for (int j = 0; j < this.getBoard().length; j++) {
					// (x, y - j) UP
					if(((whitePawn.getY() - j) >= 0) && this.getPawn(whitePawn.getX(), whitePawn.getY() - j) == Pawn.EMPTY 
							&& (this.getArea(whitePawn.getX(), whitePawn.getY() - j) != Area.CAMPS) 
							&& (this.getArea(whitePawn.getX(), whitePawn.getY() - j) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = whitePawn.getY() - 1; f > whitePawn.getY() - j; f--) {
							if(this.getPawn(whitePawn.getX(), f) != Pawn.EMPTY || this.getArea(whitePawn.getX(), f) == Area.CAMPS || this.getArea(whitePawn.getX(), f) == Area.CASTLE) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							whiteLegalMoves.add(new XYWho(whitePawn.getX(), whitePawn.getY() - j, new int[]{whitePawn.getX(), whitePawn.getY()}, false));
						}
					}
					// (x, y + j) DOWN
					if(((whitePawn.getY() + j) < this.getBoard().length) && this.getPawn(whitePawn.getX(), whitePawn.getY() + j) == Pawn.EMPTY 
							&& (this.getArea(whitePawn.getX(), whitePawn.getY() + j) != Area.CAMPS) 
							&& (this.getArea(whitePawn.getX(), whitePawn.getY() + j) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = whitePawn.getY() + 1; f < whitePawn.getY() + j; f++) {
							if(this.getPawn(whitePawn.getX(), f) != Pawn.EMPTY || this.getArea(whitePawn.getX(), f) == Area.CAMPS || this.getArea(whitePawn.getX(), f) == Area.CASTLE) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							whiteLegalMoves.add(new XYWho(whitePawn.getX(), whitePawn.getY() + j, new int[]{whitePawn.getX(), whitePawn.getY()}, false));
						}
					}
				}
				// move each pawn horizontally
				for (int i = 0; i < this.getBoard().length; i++) {
					// (x - i, y) LEFT
					if(((whitePawn.getX() - i) >= 0) && this.getPawn(whitePawn.getX() - i, whitePawn.getY()) == Pawn.EMPTY 
							&& (this.getArea(whitePawn.getX() - i, whitePawn.getY()) != Area.CAMPS) 
							&& (this.getArea(whitePawn.getX() - i, whitePawn.getY()) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = whitePawn.getX() - 1; f > whitePawn.getX() - i; f--) {
							if(this.getPawn(f, whitePawn.getY()) != Pawn.EMPTY || this.getArea(f, whitePawn.getY()) == Area.CAMPS || this.getArea(f, whitePawn.getY()) == Area.CASTLE) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							whiteLegalMoves.add(new XYWho(whitePawn.getX() - i, whitePawn.getY(), new int[]{whitePawn.getX(), whitePawn.getY()}, false));
						}
					}
					// (x + i, y) RIGHT
					if(((whitePawn.getX() + i) < this.getBoard().length) && this.getPawn(whitePawn.getX() + i, whitePawn.getY()) == Pawn.EMPTY 
							&& (this.getArea(whitePawn.getX() + i, whitePawn.getY()) != Area.CAMPS) 
							&& (this.getArea(whitePawn.getX() + i, whitePawn.getY()) != Area.CASTLE)) {
						int howManyEmptyPawns = 0;
						for (int f = whitePawn.getX() + 1; f < whitePawn.getX() + i; f++) {
							if(this.getPawn(f, whitePawn.getY()) != Pawn.EMPTY || this.getArea(f, whitePawn.getY()) == Area.CAMPS || this.getArea(f, whitePawn.getY()) == Area.CASTLE) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							whiteLegalMoves.add(new XYWho(whitePawn.getX() + i, whitePawn.getY(), new int[]{whitePawn.getX(), whitePawn.getY()}, false));
						}
					}
				}
					
			}
			
			return whiteLegalMoves;
			
			
		} else {
			// all possible moves for black
			List<XYWho> blackLegalMoves = new ArrayList<>();
			List<XYWho> blackPositions = new ArrayList<>();
			XYWho buf;
			for (int i = 0; i < this.getBoard().length; i++) {
				for (int j = 0; j < this.getBoard().length; j++) {
					if (this.getPawn(i, j).equalsPawn(StateTablut.Pawn.BLACK.toString()))  {
						if(this.getArea(i, j).equalsArea(Area.CAMPS.toString())) { // if a black is still in a camp, he can move into it
							buf = new XYWho(i, j, new int[]{i, j}, false);
							blackPositions.add(buf);
						} else if (this.getArea(i, j).equalsArea(Area.CAMPS.toString()) || this.getArea(i, j).equalsArea(Area.NORMAL.toString()) || this.getArea(i, j).equalsArea(Area.ESCAPES.toString())) {
							buf = new XYWho(i, j, new int[]{i, j}, true); // if a black is no more in a camp, he cannot enter in any camp anymore
							blackPositions.add(buf);
						}
					}
				}
			}
			// for each (i, j) in black position, try every possible move
			for (XYWho blackPawn : blackPositions) {
				// move each pawn vertically
				for (int j = 0; j < this.getBoard().length; j++) {
					// (x, y - j) UP
					if(((blackPawn.getY() - j) >= 0) && this.getPawn(blackPawn.getX(), blackPawn.getY() - j) == Pawn.EMPTY 
							&& (this.getArea(blackPawn.getX(), blackPawn.getY() - j) != Area.CASTLE)
							&& (this.getArea(blackPawn.getX(), blackPawn.getY() - j) != Area.CAMPS)) {
						int howManyEmptyPawns = 0;
						for (int f = blackPawn.getY() - 1; f > blackPawn.getY() - j; f--) {
							if(this.getPawn(blackPawn.getX(), f) != Pawn.EMPTY || (this.getArea(blackPawn.getX(), f) == Area.CAMPS && blackPawn.hasLeftTheCamp()) || this.getArea(blackPawn.getX(), f) == Area.CASTLE) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							// if a black is no more in a camp, he cannot enter in any camp anymore
							if((blackPawn.hasLeftTheCamp() && (this.getArea(blackPawn.getX(), blackPawn.getY() - j) != Area.CAMPS)) || (!blackPawn.hasLeftTheCamp())) {
								blackLegalMoves.add(new XYWho(blackPawn.getX(), blackPawn.getY() - j, new int[]{blackPawn.getX(), blackPawn.getY()}, blackPawn.hasLeftTheCamp()));
							}
						}
					}
					// (x, y + j) DOWN
					if(((blackPawn.getY() + j) < this.getBoard().length) && this.getPawn(blackPawn.getX(), blackPawn.getY() + j) == Pawn.EMPTY 
							&& (this.getArea(blackPawn.getX(), blackPawn.getY() + j) != Area.CASTLE)
							&& (this.getArea(blackPawn.getX(), blackPawn.getY() + j) != Area.CAMPS)) {
						int howManyEmptyPawns = 0;
						for (int f = blackPawn.getY() + 1; f < blackPawn.getY() + j; f++) {
							if(this.getPawn(blackPawn.getX(), f) != Pawn.EMPTY || (this.getArea(blackPawn.getX(), f) == Area.CAMPS && blackPawn.hasLeftTheCamp()) || this.getArea(blackPawn.getX(), f) == Area.CASTLE) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							if((blackPawn.hasLeftTheCamp() && (this.getArea(blackPawn.getX(), blackPawn.getY() + j) != Area.CAMPS)) || (!blackPawn.hasLeftTheCamp())) {
								blackLegalMoves.add(new XYWho(blackPawn.getX(), blackPawn.getY() + j, new int[]{blackPawn.getX(), blackPawn.getY()}, blackPawn.hasLeftTheCamp()));
							}
						}
					}
				}
				// move each pawn horizontally
				for (int i = 0; i < this.getBoard().length; i++) {
					// (x - i, y) LEFT
					if(((blackPawn.getX() - i) >= 0) && this.getPawn(blackPawn.getX() - i, blackPawn.getY()) == Pawn.EMPTY 
							&& (this.getArea(blackPawn.getX() - i, blackPawn.getY()) != Area.CASTLE)
							&& (this.getArea(blackPawn.getX() - i, blackPawn.getY()) != Area.CAMPS)) {
						int howManyEmptyPawns = 0;
						for (int f = blackPawn.getX() - 1; f > blackPawn.getX() - i; f--) {
							if(this.getPawn(f, blackPawn.getY()) != Pawn.EMPTY || (this.getArea(f, blackPawn.getY()) == Area.CAMPS && blackPawn.hasLeftTheCamp()) || this.getArea(f, blackPawn.getY()) == Area.CASTLE) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							if((blackPawn.hasLeftTheCamp() && (this.getArea(blackPawn.getX() - i, blackPawn.getY()) != Area.CAMPS)) || (!blackPawn.hasLeftTheCamp())) {
								blackLegalMoves.add(new XYWho(blackPawn.getX() - i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}, blackPawn.hasLeftTheCamp()));
							}
						}
						
					}
					// (x + i, y) RIGHT
					if(((blackPawn.getX() + i) < this.getBoard().length) && this.getPawn(blackPawn.getX() + i, blackPawn.getY()) == Pawn.EMPTY
							&& (this.getArea(blackPawn.getX() + i, blackPawn.getY()) != Area.CASTLE)
							&& (this.getArea(blackPawn.getX() + i, blackPawn.getY()) != Area.CAMPS)) {
						int howManyEmptyPawns = 0;
						for (int f = blackPawn.getX() + 1; f < blackPawn.getX() + i; f++) {
							if(this.getPawn(f, blackPawn.getY()) != Pawn.EMPTY || (this.getArea(f, blackPawn.getY()) == Area.CAMPS && blackPawn.hasLeftTheCamp()) || this.getArea(f, blackPawn.getY()) == Area.CASTLE) {
								howManyEmptyPawns++;
							}
						}
						if(howManyEmptyPawns == 0) {
							if((blackPawn.hasLeftTheCamp() && (this.getArea(blackPawn.getX() + i, blackPawn.getY()) != Area.CAMPS)) || (!blackPawn.hasLeftTheCamp())) {
								blackLegalMoves.add(new XYWho(blackPawn.getX() + i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}, blackPawn.hasLeftTheCamp()));
							}
						}
					}
				}
					
			}
			return blackLegalMoves;
		}
			
	} 

	public void initBoard() {

		// initialize pawns on board
		for (int i = 0; i < this.getBoard().length; i++) {
			for (int j = 0; j < this.getBoard().length; j++) {
				this.setPawn(i,  j, Pawn.EMPTY);
			}
		}
		this.setPawn(StateTablut.KING_POSITION,  StateTablut.KING_POSITION, Pawn.THRONE);
		this.setPawn(StateTablut.KING_POSITION,  StateTablut.KING_POSITION, Pawn.KING);
		
		this.setPawn(2,  4, Pawn.WHITE);
		this.setPawn(3,  4, Pawn.WHITE);
		this.setPawn(5,  4, Pawn.WHITE);
		this.setPawn(6,  4, Pawn.WHITE);
		this.setPawn(4,  2, Pawn.WHITE);
		this.setPawn(4,  3, Pawn.WHITE);
		this.setPawn(4,  5, Pawn.WHITE);
		this.setPawn(4,  6, Pawn.WHITE);
		
		
		this.setPawn(0,  3, Pawn.BLACK);
		this.setPawn(0,  4, Pawn.BLACK);
		this.setPawn(0,  5, Pawn.BLACK);
		this.setPawn(1,  4, Pawn.BLACK);
		this.setPawn(8,  3, Pawn.BLACK);
		this.setPawn(8,  4, Pawn.BLACK);
		this.setPawn(8,  5, Pawn.BLACK);
		this.setPawn(7,  4, Pawn.BLACK);
		this.setPawn(3,  0, Pawn.BLACK);
		this.setPawn(4,  0, Pawn.BLACK);
		this.setPawn(5,  0, Pawn.BLACK);
		this.setPawn(4,  1, Pawn.BLACK);
		this.setPawn(3,  8, Pawn.BLACK);
		this.setPawn(4,  8, Pawn.BLACK);
		this.setPawn(5,  8, Pawn.BLACK);
		this.setPawn(4,  7, Pawn.BLACK);
		
		// initialize area on board
		for (int i = 0; i < this.getBoardArea().length; i++) {
			for (int j = 0; j < this.getBoardArea().length; j++) {
				this.setArea(i, j, Area.NORMAL);
			}
		}

		this.setArea(4, 4, Area.CASTLE);
		
		//this.setArea(0, 0, Area.ESCAPES);
		this.setArea(0, 1, Area.ESCAPES);
		this.setArea(0, 2, Area.ESCAPES);
		this.setArea(0, 6, Area.ESCAPES);
		this.setArea(0, 7, Area.ESCAPES);
		//this.setArea(0, 8, Area.ESCAPES);
		this.setArea(1, 0, Area.ESCAPES);
		this.setArea(2, 0, Area.ESCAPES);
		this.setArea(6, 0, Area.ESCAPES);
		this.setArea(7, 0, Area.ESCAPES);
		//this.setArea(8, 0, Area.ESCAPES);
		this.setArea(8, 1, Area.ESCAPES);
		this.setArea(8, 2, Area.ESCAPES);
		this.setArea(8, 6, Area.ESCAPES);
		this.setArea(8, 7, Area.ESCAPES);
		//this.setArea(8, 8, Area.ESCAPES);
		this.setArea(1, 8, Area.ESCAPES);
		this.setArea(2, 8, Area.ESCAPES);
		this.setArea(6, 8, Area.ESCAPES);
		this.setArea(7, 8, Area.ESCAPES);

		this.setArea(0, 3, Area.CAMPS);
		this.setArea(0, 4, Area.CAMPS);
		this.setArea(0, 5, Area.CAMPS);
		this.setArea(1, 4, Area.CAMPS);
		this.setArea(8, 3, Area.CAMPS);
		this.setArea(8, 4, Area.CAMPS);
		this.setArea(8, 5, Area.CAMPS);
		this.setArea(7, 4, Area.CAMPS);
		this.setArea(3, 0, Area.CAMPS);
		this.setArea(4, 0, Area.CAMPS);
		this.setArea(5, 0, Area.CAMPS);
		this.setArea(4, 1, Area.CAMPS);
		this.setArea(3, 8, Area.CAMPS);
		this.setArea(4, 8, Area.CAMPS);
		this.setArea(5, 8, Area.CAMPS);
		this.setArea(4, 7, Area.CAMPS);
	}

	public StateTablut clone() {
		StateTablut result = new StateTablut(this.getCurrentDepth());

		Pawn oldboard[][] = this.getBoard();
		Pawn newboard[][] = result.getBoard();

		for (int i = 0; i < this.getBoard().length; i++) {
			for (int j = 0; j < this.getBoard().length; j++) {
				newboard[i][j] = oldboard[i][j];
			}
		}

		result.setBoard(newboard);
		result.setTurn(this.getTurn());
		result.setCurrentDepth(this.getCurrentDepth());
		return result;
	}
	
	public int getCurrentDepth() {
		return this.currentDepth;
	}
	
	public void setCurrentDepth(int currentDepth) {
		this.currentDepth = currentDepth;
	}
	
	public Pawn[][] getBoard() {
		return this.board;
	}

	public void setBoard(Pawn[][] board) {
		this.board = board;
	}
	
	public Area[][] getBoardArea() {
		return this.boardArea;
	}
	
	public void setBoardArea(Area[][] boardArea) {
		this.boardArea = boardArea;
	}
	
	public Turn getTurn() {
		return this.turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public Pawn getPawn(int row, int column) {
		return this.board[row][column];
	}

	public void setPawn(int row, int column, Pawn pawn) {
		this.board[row][column] = pawn;
	}
	
	public Area getArea(int row, int column) {
		return this.boardArea[row][column];
	}

	public void setArea(int row, int column, Area area) {
		this.boardArea[row][column] = area;
	}
	
	public void removePawn(int row, int column) {
		this.board[row][column] = Pawn.EMPTY;
	}

	private int[] getKingPosition() {
		for (int i = 0; i < this.getBoard().length; i++) {
			for (int j = 0; j < this.getBoard().length; j++) {
				if(this.getPawn(i, j) == Pawn.KING) {
					return new int[] {i,j};
				}
			}
		}
		return null;
	}
	
	public void printBoardArea() {
		for(int i=0; i < this.getBoardArea().length; i++) {
			for(int j=0; j < this.getBoardArea().length; j++) {
				System.out.print(this.getBoardArea()[i][j]+ "");
			}
			System.out.println("|");
		}
	}
	
	public void printBoard() {
		for(int i=0; i < this.getBoard().length; i++) {
			for(int j=0; j < this.getBoard().length; j++) {
				System.out.print(this.getBoard()[i][j]+ "|");
			}
			System.out.println("");
		}
	}
	
	@Override
	public String toString() {
		for(int i=0; i < this.getBoard().length; i++) {
			for(int j=0; j < this.getBoard().length; j++) {
				System.out.print(this.getBoard()[i][j]+ "|");
			}
			System.out.println("");
		}
		return "";
	}

	public String getBox(int row, int column) {
		String ret;
		char col = (char) (column + 97);
		ret = col + "" + (row + 1);
		return ret;
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
		if (this.getBoard() == null) {
			if (other.getBoard() != null)
				return false;
		} else {
			if (other.getBoard() == null)
				return false;
			if (this.getBoard().length != other.getBoard().length)
				return false;
			if (this.getBoard()[0].length != other.getBoard()[0].length)
				return false;
			for (int i = 0; i < other.getBoard().length; i++)
				for (int j = 0; j < other.getBoard()[i].length; j++)
					if (!this.getBoard()[i][j].equals(other.getBoard()[i][j]))
						return false;
		}
		if (this.getTurn()!= other.getTurn())
			return false;
		return true;
	}

}
