package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;

import com.badlogic.gdx.math.Vector2;

public class Explosion extends Entity
{
    private int _timer = 0;
    
    private static CollisionHandler<Explosion> cHandler = new CollisionHandler<Explosion>()
    {
        @Override
        protected void CollideBomb(Explosion e1, Bomb b)
        {
            b.TriggerExplosion();
        }

        @Override
        protected void CollidePlayer(Explosion e1, Player p)
        {
            p.Kill();
        }

        @Override
        protected void CollideWall(Explosion e1, Wall w)
        {
            w.DecHP(1);
        }
    };

    public Explosion(int guid, Vector2 pos, int dir, boolean end, BombermenServer sv)
    {
        super(TYPE_EXPLOSION, guid, pos, sv);
        _direction = dir;
        _end = end;
    }

    private int _direction;
    private boolean _end;

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_EXPLOSION(GetGuid(), GetX(), GetY(), _direction, _end);
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
        _timer += diff;
        
        if (_timer >= 600)
            _server.RemoveEntityNextUpdate(GetGuid());
    }
}
