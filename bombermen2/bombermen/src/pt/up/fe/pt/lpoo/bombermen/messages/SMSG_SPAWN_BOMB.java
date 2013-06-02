package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_BOMB extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_BOMB(int guid, int creatorId)
    {
        super(Entity.TYPE_BOMB, guid);

        CreatorId = creatorId;
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_BOMB]";
    }

    public final int CreatorId;
}
