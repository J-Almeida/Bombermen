package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.BombermenServer;
import pt.up.fe.lpoo.bombermen.CollisionHandler;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;

import com.badlogic.gdx.math.Vector2;

/**
 * The Class Explosion.
 */
public class Explosion extends Entity
{

    /** The timer. */
    private int _timer = 0;

    /** The creator guid. */
    private int _creatorGuid;

    /** The c handler. */
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

    /**
     * Instantiates a new explosion.
     *
     * @param guid the guid
     * @param creatorGuid the creator guid
     * @param pos the pos
     * @param dir the dir
     * @param end the end
     * @param sv the sv
     */
    public Explosion(int guid, int creatorGuid, Vector2 pos, int dir, boolean end, BombermenServer sv)
    {
        super(TYPE_EXPLOSION, guid, pos, sv);
        _direction = dir;
        _end = end;
        _creatorGuid = creatorGuid;
    }

    /** The direction. */
    private int _direction;

    /** The end. */
    private boolean _end;

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSpawnMessage()
     */
    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_EXPLOSION(GetGuid(), GetX(), GetY(), _direction, _end);
    }

    /** The Constant size. */
    private static final Vector2 size = new Vector2(Constants.EXPLOSION_WIDTH, Constants.EXPLOSION_HEIGHT);

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSize()
     */
    @Override
    public Vector2 GetSize()
    {
        return size;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#OnCollision(pt.up.fe.lpoo.bombermen.Entity)
     */
    @Override
    public void OnCollision(Entity e)
    {
        cHandler.OnCollision(this, e);
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#Update(int)
     */
    @Override
    public void Update(int diff)
    {
        _timer += diff;

        if (_timer >= 600) _server.RemoveEntityNextUpdate(GetGuid());
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#OnDestroy()
     */
    @Override
    public void OnDestroy()
    {
    }
}
