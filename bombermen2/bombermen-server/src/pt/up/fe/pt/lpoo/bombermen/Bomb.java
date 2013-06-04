package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;

import com.badlogic.gdx.math.Vector2;

public class Bomb extends Entity
{
    private static CollisionHandler<Bomb> cHandler = new CollisionHandler<Bomb>()
    {
    };

    Bomb(int guid, Vector2 pos)
    {
        super(TYPE_BOMB, guid, pos);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_BOMB(GetGuid(), GetX(), GetY());
    }

    private static final Vector2 size = new Vector2(0.9f * Constants.CELL_SIZE, 0.9f * Constants.CELL_SIZE);

    @Override
    public Vector2 GetSize()
    {
        return size;
    }

    @Override
    public void OnCollision(Entity e)
    {
        cHandler.OnCollision(this, e);
    }

}
