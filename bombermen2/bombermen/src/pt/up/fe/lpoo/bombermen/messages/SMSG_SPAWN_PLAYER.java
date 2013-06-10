package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.Entity;

/**
 * The Class SMSG_SPAWN_PLAYER.
 */
public class SMSG_SPAWN_PLAYER extends SMSG_SPAWN
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Name. */
    public final String Name;

    /** The Score. */
    public final int Score;

    /**
     * Instantiates a new smsg spawn player.
     *
     * @param guid the guid
     * @param name the name
     * @param score the score
     * @param x the x
     * @param y the y
     */
    public SMSG_SPAWN_PLAYER(int guid, String name, int score, float x, float y)
    {
        super(Entity.TYPE_PLAYER, guid, x, y);
        Name = name;
        Score = score;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_PLAYER - Guid: " + Guid + ", X: " + X + ", Y: " + Y + ", Name: " + Name + "]";
    }
}
