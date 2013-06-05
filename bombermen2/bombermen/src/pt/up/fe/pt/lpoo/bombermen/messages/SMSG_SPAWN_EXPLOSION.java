package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_EXPLOSION extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public final int Direction;
    public final boolean End;

    public SMSG_SPAWN_EXPLOSION(int guid, float x, float y, int direction, boolean end)
    {
        super(Entity.TYPE_EXPLOSION, guid, x, y);
        Direction = direction;
        End = end;
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_EXPLOSION - Guid: " + Guid + ", X: " + X + ", Y: " + Y + ", Direction: " + Direction + ", End: " + End + "]";
    }
}
