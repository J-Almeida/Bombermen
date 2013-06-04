package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;

import com.badlogic.gdx.math.Vector2;

public class Explosion extends Entity
{
    private static CollisionHandler<Explosion> cHandler = new CollisionHandler<Explosion>()
    {
    };

    public Explosion(int guid, Vector2 pos, BombermenServer sv)
    {
        super(TYPE_EXPLOSION, guid, pos, sv);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_EXPLOSION(GetGuid(), GetX(), GetY());
    }

    private static final Vector2 size = new Vector2(Constants.EXPLOSION_WIDTH, Constants.EXPLOSION_HEIGHT);

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
        // TODO Auto-generated method stub
        
    }

}
