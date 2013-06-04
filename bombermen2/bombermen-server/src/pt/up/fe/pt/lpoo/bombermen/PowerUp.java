package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;

import com.badlogic.gdx.math.Vector2;

public class PowerUp extends Entity
{
    private static CollisionHandler<PowerUp> cHandler = new CollisionHandler<PowerUp>()
    {
    };

    public PowerUp(int guid, Vector2 pos, BombermenServer sv)
    {
        super(TYPE_POWER_UP, guid, pos, sv);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_POWER_UP(GetGuid(), GetX(), GetY());
    }

    private static final Vector2 size = new Vector2(Constants.POWER_UP_WIDTH, Constants.POWER_UP_HEIGHT);

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

    @Override
    public void Update(int diff)
    {
    }
}
