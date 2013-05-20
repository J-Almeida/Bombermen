package utils;

import java.awt.Point;

/**
 * Enum to define possible movement directions.
 */
public enum Direction
{
    //None,
    North(0), /** N - 0º */
    //Northeast, /** NE - 45º */
    East(1), /** S - 90º */
    //Southeast, /** SE - 135º */
    South(2), /** S - 180º */
    //Southwest, /** SW - 225º */
    West(3); /** W - 270º */
    //Northwest; /**  NW - 315º */

    Direction(int i)
    {
        Index = i;
    }

    public final int Index;

    /**
     * Convert Key to Direction
     *
     * @param k the key
     * @return the direction
     */
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

    /**
     * Apply movement in a certain direction.
     *
     * @param p the current position (to be modified)
     * @param d the direction to go
     * @param inc movement to apply
     */
    public static void ApplyMovement(Point p, Direction d, int inc)
    {
        if (p == null || d == null)
            return;

        switch (d)
        {
            case East:
                p.x += inc;
                break;
            case North:
                p.y -= inc;
                break;
            /*case Northeast:
                p.x += inc;
                p.y -= inc;
                break;
            case Northwest:
                p.x -= inc;
                p.y -= inc;
                break;*/
            case South:
                p.y += inc;
                break;
            /*case Southeast:
                p.x += inc;
                p.y += inc;
                break;
            case Southwest:
                p.x -= inc;
                p.y += inc;
                break;*/
            case West:
                p.x -= inc;
                break;
            default:
                break;
        }
    }

    public static Point ApplyMovementToPoint(Point p, Direction d, int inc)
    {
        switch (d)
        {
        case East:
            return new Point(p.x + inc, p.y);
        case North:
            return new Point(p.x, p.y - inc);
        case South:
            return new Point(p.x, p.y + inc);
        case West:
            return new Point(p.x - inc, p.y);
        default:
            return null;
        }
    }

    public static Direction InverseDirection(Direction d)
    {
        switch (d)
        {
            case East:
                return West;
            /*case None:
                return None;*/
            case North:
                return South;
            /*case Northeast:
                return Southwest;
            case Northwest:
                return Southeast;*/
            case South:
                return North;
            /*case Southeast:
                return Northwest;
            case Southwest:
                return Northeast;*/
            case West:
                return East;
            default:
                return null;
        }
    }
}
