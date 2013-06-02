package pt.up.fe.pt.lpoo.bombermen.messages;

import java.awt.Point;

import pt.up.fe.pt.lpoo.bombermen.Entity;

public class SMSG_SPAWN_PLAYER extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_PLAYER(int guid, String name, Point pos)
    {
        super(Entity.TYPE_PLAYER, guid);
        Name = name;
        x = pos.x;
        y = pos.y;
    }

    public final String Name;
    public final int x;
    public final int y;

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_PLAYER]";
    }

}
