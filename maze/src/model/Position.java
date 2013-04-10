package model;

import java.io.Serializable;

import logic.Direction;

/**
 * Represents a position in space (2D).
 */
public class Position implements Cloneable, Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new position.
     *
     * @param x the x
     * @param y the y
     */
    public Position(int x, int y) { X = x; Y = y; }

    public Position() { X = -1; Y = -1; }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object rhs)
    {
        if (rhs == null || !(rhs instanceof Position))
            return false;

        Position other = (Position) rhs;

        return this.X == other.X && this.Y == other.Y;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return 1/2 * (X + Y)*(X + Y + 1) + Y; // Cantor pairing function
    }

    /** The x. */
    public int X;

    /** The y. */
    public int Y;

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Position clone()
    {
        return new Position(this.X, this.Y);
    }

    /**
     * Gets the direction from.
     *
     * @param other the other
     * @return the direction
     */
    public Direction GetDirectionFrom(Position other)
    {
        int dX = this.X - other.X;
        int dY = this.Y - other.Y;
        double m = Math.abs((double)dY / (double)dX);

        if (m >= 0. && m < 1.)
            return dX < 0 ? Direction.West : Direction.East;
        else
            return dY < 0 ? Direction.North : Direction.South;
    }
    /**
     * Checks if is adjacent.
     * Does not include self position.
     *
     * @param pos1 the first position
     * @param pos2 the second position
     * @return true, if is adjacent
     */
    public static boolean IsAdjacent(Position pos1, Position pos2)
    {
        return pos1.equals(new Position(pos2.X + 1, pos2.Y))
                || pos1.equals(new Position(pos2.X - 1, pos2.Y))
                || pos1.equals(new Position(pos2.X, pos2.Y + 1))
                || pos1.equals(new Position(pos2.X, pos2.Y - 1));
    }
}
