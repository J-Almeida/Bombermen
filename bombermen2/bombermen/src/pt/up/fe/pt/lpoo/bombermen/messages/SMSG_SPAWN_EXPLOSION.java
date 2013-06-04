package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_EXPLOSION extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_EXPLOSION(int guid, float x, float y)
    {
        super(Entity.TYPE_EXPLOSION, guid, x, y);
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_EXPLOSION]";
    }
}
