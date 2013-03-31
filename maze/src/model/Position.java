package model;

public class Position
{
    public Position() {    }
    public Position(int x, int y) { X = x; Y = y; }

    @Override
    public boolean equals(Object rhs)
    {
        if (rhs == null || !(rhs instanceof Position))
            return false;

        Position other = (Position) rhs;

        return this.X == other.X && this.Y == other.Y;
    }

    public int X = -1;
    public int Y = -1;

    @Override
    public Position clone()
    {
        return new Position(this.X, this.Y);
    }

    /**
     * Checks if is adjacent.
     *
     * @param pos1 the pos1
     * @param pos2 the pos2
     * @return true, if is adjacent
     */
    public static boolean IsAdjacent(Position pos1, Position pos2)
    {
        return (pos1.equals(new Position(pos2.X + 1, pos2.Y)))
                || (pos1.equals(new Position(pos2.X - 1, pos2.Y)))
                || (pos1.equals(new Position(pos2.X, pos2.Y + 1)))
                || (pos1.equals(new Position(pos2.X, pos2.Y - 1)));
    }
}
