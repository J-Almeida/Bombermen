package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.Entity;

/**
 * The Class SMSG_SPAWN_WALL.
 */
public class SMSG_SPAWN_WALL extends SMSG_SPAWN
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The hp. */
    public final int HP;

    /**
     * Instantiates a new smsg spawn wall.
     *
     * @param guid the guid
     * @param hp the hp
     * @param x the x
     * @param y the y
     */
    public SMSG_SPAWN_WALL(int guid, int hp, float x, float y)
    {
        super(Entity.TYPE_WALL, guid, x, y);
        HP = hp;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_WALL - Guid: " + Guid + ", X: " + X + ", Y: " + Y + ", HP: " + HP + "]";
    }
}
