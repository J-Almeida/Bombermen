package logic;

import utils.Key;

public enum Direction
{
    North, // N - 0º
    Northeast, // NE - 45º
    East, // S - 90º
    Southeast, // SE - 135º
    South, // S - 180º
    Southwest, // SW - 225º
    West, // W - 270º
    Northwest; // NW - 315º

    public static Direction FromKey(Key k)
    {
        switch (k)
        {
            case DOWN:
                return Direction.South;
            case LEFT:
                return Direction.West;
            case RIGHT:
                return Direction.East;
            case UP:
                return Direction.North;
            default:
                return null;
        }
    }
}
