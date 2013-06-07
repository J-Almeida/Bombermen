package pt.up.fe.lpoo.utils;

public class Direction
{
    public static final int NONE = -1;
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    public static final int Directions[] = { NORTH, EAST, SOUTH, WEST };

    public static float ApplyMovementX(float x, int dir, float inc)
    {
        switch (dir)
        {
            case EAST:
                return x + inc;
            case WEST:
                return x - inc;
            default:
                return x;
        }
    }

    public static float ApplyMovementY(float y, int dir, float inc)
    {
        switch (dir)
        {
            case NORTH:
                return y + inc;
            case SOUTH:
                return y - inc;
            default:
                return y;
        }
    }

    private Direction()
    {
    }
}
