package logic;

import model.Position;
import utils.Key;

/**
 * Enum to define possible movement directions.
 */
public enum Direction
{
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
     */
    public static void ApplyMovement(Position p, Direction d)
    {
        switch (d)
        {
            case East:
                p.X++;
                break;
            case North:
                p.Y--;
                break;
            case Northeast:
                p.X++;
                p.Y--;
                break;
            case Northwest:
                p.X--;
                p.Y--;
                break;
            case South:
                p.Y++;
                break;
            case Southeast:
                p.X++;
                p.Y++;
                break;
            case Southwest:
                p.X--;
                p.Y++;
                break;
            case West:
                p.X--;
                break;
        }
    }
}
