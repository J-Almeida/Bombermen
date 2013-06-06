package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_BOMB extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_BOMB(int guid, int creatorGuid, float x, float y)
    {
        super(Entity.TYPE_BOMB, guid, x, y);
        CreatorGuid = creatorGuid;
    }
    
    public final int CreatorGuid;
    
    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_BOMB - Guid: " + Guid + ", X: " + X + ", Y: " + Y + "]";
    }
}
