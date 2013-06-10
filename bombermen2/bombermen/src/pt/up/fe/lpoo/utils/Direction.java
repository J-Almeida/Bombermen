package pt.up.fe.lpoo.utils;

/**
 * The Class Direction.
 */
public class Direction
{

    /** The Constant NONE. */
    public static final int NONE = -1;

    /** The Constant NORTH. */
    public static final int NORTH = 0;

    /** The Constant EAST. */
    public static final int EAST = 1;

    /** The Constant SOUTH. */
    public static final int SOUTH = 2;

    /** The Constant WEST. */
    public static final int WEST = 3;

    /** The Constant Directions. */
    public static final int Directions[] = { NORTH, EAST, SOUTH, WEST };

    /**
     * Apply movement x.
     *
     * @param x the x
     * @param dir the dir
     * @param inc the inc
     * @return the float
     */
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

    /**
     * Apply movement y.
     *
     * @param y the y
     * @param dir the dir
     * @param inc the inc
     * @return the float
     */
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

    /**
     * Instantiates a new direction.
     */
    private Direction()
    {
    }
}
