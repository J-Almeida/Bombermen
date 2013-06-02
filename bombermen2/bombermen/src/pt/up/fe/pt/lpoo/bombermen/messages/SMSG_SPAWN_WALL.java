package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_WALL extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_WALL(int guid)
    {
        super(Entity.TYPE_WALL, guid);
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_WALL]";
    }
}
