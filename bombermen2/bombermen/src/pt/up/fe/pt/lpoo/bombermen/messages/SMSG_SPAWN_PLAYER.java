package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_PLAYER extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_PLAYER(int guid, String name, float x, float y)
    {
        super(Entity.TYPE_PLAYER, guid, x, y);
        Name = name;
    }

    public final String Name;

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_PLAYER]";
    }
}
