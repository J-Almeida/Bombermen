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
}
