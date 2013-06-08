package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.BombermenServer;
import pt.up.fe.lpoo.bombermen.CollisionHandler;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;

import com.badlogic.gdx.math.Vector2;

public class Explosion extends Entity
{
    private int _timer = 0;
    private int _creatorGuid;
    private static CollisionHandler<Explosion> cHandler = new CollisionHandler<Explosion>()
    {
        @Override
        protected void CollidePowerUp(Explosion e1, PowerUp p)
        {
            if (!p.JustCreated()) p.Kill();
        }

        @Override
        protected void CollideBomb(Explosion e1, Bomb b)
        {
            b.TriggerExplosion();
        }

        @Override
        protected void CollidePlayer(Explosion e1, Player p)
        {
            p.Kill();
            if (e1._creatorGuid != p.GetGuid()) e1._server.ChangePlayerScore(e1._creatorGuid, Constants.PLAYER_KILL_SCORE);
        }

        @Override
        protected void CollideWall(Explosion e1, Wall w)
        {
            w.DecHP(1);
            if (!w.IsAlive()) e1._server.ChangePlayerScore(e1._creatorGuid, Constants.WALL_DESTROY_SCORE);
        }
    };

    public Explosion(int guid, int creatorGuid, Vector2 pos, int dir, boolean end, BombermenServer sv)
    {
        super(TYPE_EXPLOSION, guid, pos, sv);
        _direction = dir;
        _end = end;
        _creatorGuid = creatorGuid;
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

        if (_timer >= 600) _server.RemoveEntityNextUpdate(GetGuid());
    }

    @Override
    public void OnDestroy()
    {
    }
}
