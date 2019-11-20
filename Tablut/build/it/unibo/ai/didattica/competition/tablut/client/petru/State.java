package it.unibo.ai.didattica.competition.tablut.client.petru;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Abstract class for a State of a game We have a representation of the board
 * and the turn
 * 
 * @author Andrea Piretti
 *
 */
public abstract class State {

	/**
	 * Turn represent the player that has to move or the end of the game(A win
	 * by a player or a draw)
	 * 
	 * @author A.Piretti
	 */
	public enum Turn {
		WHITE("W"), BLACK("B"), WHITEWIN("WW"), BLACKWIN("BW"), DRAW("D");
		private final String turn;

		private Turn(String s) {
			this.turn = s;
		}

		public boolean equalsTurn(String otherName) {
			return (otherName == null) ? false : this.turn.equals(otherName);
		}

		public String toString() {
			return this.turn;
		}
	}

	/**
	 * 
	 * Pawn represents the content of a box in the board
	 * 
	 * @author A.Piretti
	 *
	 */
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
	public static final int KING_POSITION = 4; // this 4 is meant as the initial king position which is [4, 4]
	
	private Pawn board[][];
	private Area boardArea[][];
	private Turn turn;
	private boolean boolBlackCamps[][];


	public State() {
		super();
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
	
	public boolean getBoolBlackCamps(int row, int column) {
		return this.boolBlackCamps[row][column];
	}

	public void setBoolBlackCamps(int row, int column, boolean hasBlackLeftTheCamp) {
		this.boolBlackCamps[row][column] = hasBlackLeftTheCamp;
	}
	
	public boolean hasBlackLeftTheCamp(XYWho xywho) {
		// XYWho has destination coordinates and coordinates of the black pawn that has moved. Then update with setBoolBlackCamps()
		return false;
	}
	
	
	public void removePawn(int row, int column) {
		this.board[row][column] = Pawn.EMPTY;
	}

	public boolean hasTheKingMoved() {
		return this.getKingPosition()[0] == KING_POSITION;
	}
	
	public int[] getKingPosition() {
		for (int i = 0; i < this.getBoard().length; i++) {
			for (int j = 0; j < this.getBoard().length; j++) {
				if(this.getPawn(i, j) == Pawn.KING) {
					return new int[] {i,j};
				}
			}
		}
		return null;
	}
	
	/**
	 * Counts the number of checkers of a specific color on the board. Note: the king is not taken into account for white, it must be checked separately
	 * @param color The color of the checker that will be counted. It is possible also to use EMPTY to count empty cells.
	 * @return The number of cells of the board that contains a checker of that color.
	 */
	public int getNumberOf(Pawn color) {
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == color)
					count++;
			}
		}
		return count;
	}
	
	public void printBoardArea() {
		for(int i=0; i < this.getBoardArea().length; i++) {
			for(int j=0; j < this.getBoardArea().length; j++) {
				System.out.print(this.getBoardArea()[i][j]+ "  ");
			}
			System.out.println("\n");
		}
	}
	
	public void printBoard() {
		for(int i=0; i < this.getBoard().length; i++) {
			for(int j=0; j < this.getBoard().length; j++) {
				System.out.print(this.getBoard()[i][j]+ "  ");
			}
			System.out.println("\n");
		}
	}
	
	
	public String boardString() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				result.append(this.board[i][j].toString());
				if (j == 8) {
					result.append("\n");
				}
			}
		}
		return result.toString();
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		// board
		result.append("");
		result.append(this.boardString());

		result.append("-------");
		result.append("\n");

		// TURNO
		result.append(this.turn.toString()+"\n");
		result.append("-------");
		return result.toString();
	}

	public String toLinearString() {
		StringBuffer result = new StringBuffer();

		// board
		result.append("");
		result.append(this.boardString().replace("\n", ""));
		result.append(this.turn.toString());

		return result.toString();
	}
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		State other = (State) obj;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.board == null) ? 0 : this.board.hashCode());
		result = prime * result + ((this.turn == null) ? 0 : this.turn.hashCode());
		return result;
	}

	public String getBox(int row, int column) {
		String ret;
		char col = (char) (column + 97);
		ret = col + "" + (row + 1);
		return ret;
	}

	public State clone() {
		Class<? extends State> stateclass = this.getClass();
		Constructor<? extends State> cons = null;
		State result = null;
		try {
			cons = stateclass.getConstructor(stateclass);
			result = cons.newInstance(new Object[0]);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

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
}
