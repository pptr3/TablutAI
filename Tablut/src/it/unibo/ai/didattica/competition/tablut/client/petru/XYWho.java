package it.unibo.ai.didattica.competition.tablut.client.petru;


public class XYWho {

    private int x; // new x destination coordinate
    private int y; // new y destination coordinate
    private int[] who; // old coordinates
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
		this.hasLeftTheCamp = hasLeftTheCamp;
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
		return "(" + this.x + ", " + this.y + "), [" + this.who[0] + ", "+ this.who[1] +"]";
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + x;
		result = 43 * result + y;
		return result;
	}
}