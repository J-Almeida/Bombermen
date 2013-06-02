package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_POWER_UP extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_POWER_UP(int guid)
    {
        super(Entity.TYPE_POWER_UP, guid);
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_POWER_UP]";
    }
}
