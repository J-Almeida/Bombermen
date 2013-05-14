package utils;

import java.awt.geom.Point2D;

/**
 * Enum to define possible movement directions.
 */
public enum Direction
{
    None,
    North, /** N - 0º */
    Northeast, /** NE - 45º */
    East, /** S - 90º */
    Southeast, /** SE - 135º */
    South, /** S - 180º */
    Southwest, /** SW - 225º */
    West, /** W - 270º */
    Northwest; /**  NW - 315º */

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
    public static void ApplyMovement(Point2D p, Direction d, double inc)
    {
        if (d == null || d == Direction.None)
            return;
        
        // System.out.println("p: " + p + " d: " + d + "inc: " + inc);
        
        switch (d)
        {
            case East:
                p.setLocation(p.getX() + inc, p.getY());
                break;
            case North:
                p.setLocation(p.getX(), p.getY() - inc);
                break;
            case Northeast:
                p.setLocation(p.getX() + inc * Math.sqrt(2)/2, p.getY() - inc * Math.sqrt(2)/2);
                break;
            case Northwest:
                p.setLocation(p.getX() - inc * Math.sqrt(2)/2, p.getY() - inc * Math.sqrt(2)/2);
                break;
            case South:
                p.setLocation(p.getX(), p.getY() + inc);
                break;
            case Southeast:
                p.setLocation(p.getX() + inc * Math.sqrt(2)/2, p.getY() + inc * Math.sqrt(2)/2);
                break;
            case Southwest:
                p.setLocation(p.getX() - inc * Math.sqrt(2)/2, p.getY() + inc * Math.sqrt(2)/2);
                break;
            case West:
                p.setLocation(p.getX() - inc, p.getY());
                break;
            default:
                break;
        }
    }
    
    public static Direction InverseDirection(Direction d)
    {
        switch (d)
        {
            case East:
                return West;
            case None:
                return None;
            case North:
                return South;
            case Northeast:
                return Southwest;
            case Northwest:
                return Southeast;
            case South:
                return North;
            case Southeast:
                return Northwest;
            case Southwest:
                return Northeast;
            case West:
                return East;
            default:
                return null;
        }
    }
}
