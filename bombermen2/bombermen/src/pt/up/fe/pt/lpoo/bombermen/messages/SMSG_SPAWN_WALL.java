package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_WALL extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public final int HP;

    public SMSG_SPAWN_WALL(int guid, int hp, float x, float y)
    {
        super(Entity.TYPE_WALL, guid, x, y);
        HP = hp;
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_WALL - Guid: " + Guid + ", X: " + X + ", Y: " + Y + ", HP: " + HP + "]";
    }
}
