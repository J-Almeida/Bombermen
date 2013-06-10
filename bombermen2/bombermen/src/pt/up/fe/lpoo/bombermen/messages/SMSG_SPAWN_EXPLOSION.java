package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.Entity;

/**
 * The Class SMSG_SPAWN_EXPLOSION.
 */
public class SMSG_SPAWN_EXPLOSION extends SMSG_SPAWN
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Direction. */
    public final int Direction;

    /** The End. */
    public final boolean End;

    /**
     * Instantiates a new smsg spawn explosion.
     *
     * @param guid the guid
     * @param x the x
     * @param y the y
     * @param direction the direction
     * @param end the end
     */
    public SMSG_SPAWN_EXPLOSION(int guid, float x, float y, int direction, boolean end)
    {
        super(Entity.TYPE_EXPLOSION, guid, x, y);
        Direction = direction;
        End = end;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_EXPLOSION - Guid: " + Guid + ", X: " + X + ", Y: " + Y + ", Direction: " + Direction + ", End: " + End + "]";
    }
}
