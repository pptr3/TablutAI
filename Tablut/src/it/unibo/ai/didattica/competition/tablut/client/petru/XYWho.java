package it.unibo.ai.didattica.competition.tablut.client.petru;


public class XYWho {
	
	public enum Direction {
		North, South, East, West
	}

    private int x; // new x coordinate where to move the pawn with index (x, y) 
    private int y;
    private int[] who;
    boolean hasLeftTheCamp;

	/**
	 * Constructs and initializes a location at the specified (<em>x</em>,
	 * <em>y</em>) location in the coordinate space.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public XYWho(int x, int y, int[] who, boolean hasLeftTheCamp) {
		this.x = x;
		this.y = y;
		this.who = who;
		this.hasLeftTheCamp = false;
	}

	/**
	 * Returns the X coordinate of the location in integer precision.
	 * 
	 * @return the X coordinate of the location in double precision.
	 */
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public int[] getWho() {
		return this.who;
	}
	
	public boolean hasLeftTheCamp() {
		return this.hasLeftTheCamp;
	}


	@Override
	public boolean equals(Object o) {
		if (null != o && getClass() == o.getClass()) {
			XYWho loc = (XYWho) o;
			return x == loc.getX() && y == loc.getY();
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + x;
		result = 43 * result + y;
		return result;
	}
}