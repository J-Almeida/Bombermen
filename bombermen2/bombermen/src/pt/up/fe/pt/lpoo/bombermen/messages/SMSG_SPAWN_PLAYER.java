package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

import com.badlogic.gdx.math.Vector2;

public class SMSG_SPAWN_PLAYER extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_PLAYER(int guid, String name, Vector2 pos)
    {
        super(Entity.TYPE_PLAYER, guid);
        Name = name;
        x = pos.x;
        y = pos.y;
    }

    public final String Name;
    public final float x;
    public final float y;

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_PLAYER]";
    }

}
