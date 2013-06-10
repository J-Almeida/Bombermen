package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.Entity;

/**
 * The Class SMSG_SPAWN_POWER_UP.
 */
public class SMSG_SPAWN_POWER_UP extends SMSG_SPAWN
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Type. */
    public int Type;

    /**
     * Instantiates a new smsg spawn power up.
     *
     * @param guid the guid
     * @param x the x
     * @param y the y
     * @param powerUpType the power up type
     */
    public SMSG_SPAWN_POWER_UP(int guid, float x, float y, int powerUpType)
    {
        super(Entity.TYPE_POWER_UP, guid, x, y);
        Type = powerUpType;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_POWER_UP - Guid: " + Guid + ", X: " + X + ", Y: " + Y + ", Type: " + Type + "]";
    }
}
