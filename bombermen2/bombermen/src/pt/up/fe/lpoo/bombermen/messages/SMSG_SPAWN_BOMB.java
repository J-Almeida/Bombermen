package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.Entity;

/**
 * The Class SMSG_SPAWN_BOMB.
 */
public class SMSG_SPAWN_BOMB extends SMSG_SPAWN
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new smsg spawn bomb.
     *
     * @param guid the guid
     * @param creatorGuid the creator guid
     * @param x the x
     * @param y the y
     */
    public SMSG_SPAWN_BOMB(int guid, int creatorGuid, float x, float y)
    {
        super(Entity.TYPE_BOMB, guid, x, y);
        CreatorGuid = creatorGuid;
    }

    /** The Creator guid. */
    public final int CreatorGuid;

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_BOMB - Guid: " + Guid + ", X: " + X + ", Y: " + Y + "]";
    }
}
