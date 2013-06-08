package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.Entity;

public class SMSG_SPAWN_PLAYER extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public final String Name;
    public final int Score;

    public SMSG_SPAWN_PLAYER(int guid, String name, int score, float x, float y)
    {
        super(Entity.TYPE_PLAYER, guid, x, y);
        Name = name;
        Score = score;
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_PLAYER - Guid: " + Guid + ", X: " + X + ", Y: " + Y + ", Name: " + Name + "]";
    }
}
