package pt.up.fe.pt.lpoo.bombermen.messages;

import pt.up.fe.pt.lpoo.bombermen.Entity;

import com.badlogic.gdx.math.Vector2;

public class SMSG_SPAWN_BOMB extends SMSG_SPAWN
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN_BOMB(int guid, Vector2 pos)
    {
        super(Entity.TYPE_BOMB, guid);

        x = pos.x;
        y = pos.y;
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN_BOMB]";
    }

    public final float x;
    public final float y;
}
