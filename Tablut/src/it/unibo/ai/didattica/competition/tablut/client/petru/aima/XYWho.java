package it.unibo.ai.didattica.competition.tablut.client.petru.aima;

import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

public class XYWho {
	
	public enum Direction {
		North, South, East, West
	}

    private int x; // new x coordinate where to move the pawn with index (x, y) 
    private int y;
    private int[] who;

	/**
	 * Constructs and initializes a location at the specified (<em>x</em>,
	 * <em>y</em>) location in the coordinate space.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public XYWho(int x, int y, int[] who) {
		this.x = x;
		this.y = y;
		this.who = who;
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

	/**
	 * Returns the location one unit left of this location.
	 * 
	 * @return the location one unit left of this location.
	 */
	public XYWho west() {
		return new XYWho(x - 1, y, this.who);
	}

	/**
	 * Returns the location one unit right of this location.
	 * 
	 * @return the location one unit right of this location.
	 */
	public XYWho east() {
		return new XYWho(x + 1, y, this.who);
	}

	/**
	 * Returns the location one unit ahead of this location.
	 * 
	 * @return the location one unit ahead of this location.
	 */
	public XYWho north() {
		return new XYWho(x, y - 1,this.who);
	}

	/**
	 * Returns the location one unit behind, this location.
	 * 
	 * @return the location one unit behind this location.
	 */
	public XYWho south() {
		return new XYWho(x, y + 1, this.who);
	}

	/**
	 * Returns the location one unit left of this location.
	 * 
	 * @return the location one unit left of this location.
	 */
	public XYWho left() {
		return west();
	}

	/**
	 * Returns the location one unit right of this location.
	 * 
	 * @return the location one unit right of this location.
	 */
	public XYWho right() {
		return east();
	}

	/**
	 * Returns the location one unit above this location.
	 * 
	 * @return the location one unit above this location.
	 */
	public XYWho up() {
		return north();
	}

	/**
	 * Returns the location one unit below this location.
	 * 
	 * @return the location one unit below this location.
	 */
	public XYWho down() {
		return south();
	}

	/**
	 * Returns the location one unit from this location in the specified
	 * direction.
	 * 
	 * @return the location one unit from this location in the specified
	 *         direction.
	 */
	public XYWho locationAt(Direction direction) {
		if (direction.equals(Direction.North)) {
			return north();
		}
		if (direction.equals(Direction.South)) {
			return south();
		}
		if (direction.equals(Direction.East)) {
			return east();
		}
		if (direction.equals(Direction.West)) {
			return west();
		} else {
			throw new RuntimeException("Unknown direction " + direction);
		}
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