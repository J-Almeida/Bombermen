package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.BombermenServer;
import pt.up.fe.lpoo.bombermen.CollisionHandler;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;
import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The Class Bomb.
 */
public class Bomb extends Entity
{

    /** The c handler. */
    private static CollisionHandler<Bomb> cHandler = new CollisionHandler<Bomb>()
    {};

    /**
     * The Interface ExplodeHandler.
     */
    public interface ExplodeHandler
    {

        /**
         * On explode.
         */
        void OnExplode();
    }

    /** The explosion radius. */
    private int _explosionRadius;

    /** The bomb timer. */
    private int _bombTimer; // ms

    /** The radius. */
    private int _radius[] = { 0, 0, 0, 0 };

    /** The just created. */
    private boolean _justCreated = true;

    /** The creator guid. */
    private int _creatorGuid;

    /** The explode handler. */
    private ExplodeHandler _explodeHandler = null;

    /**
     * Gets the creator guid.
     *
     * @return the int
     */
    public int GetCreatorGuid()
    {
        return _creatorGuid;
    }

    /**
     * Sets the just created.
     *
     * @param val the val
     */
    public void SetJustCreated(boolean val)
    {
        _justCreated = false;
    }

    /**
     * Just created.
     *
     * @return true, if successful
     */
    public boolean JustCreated()
    {
        return _justCreated;
    }

    /**
     * Instantiates a new bomb.
     *
     * @param guid the guid
     * @param creatorGuid the creator guid
     * @param pos the pos
     * @param explosionRadius the explosion radius
     * @param sv the sv
     */
    public Bomb(int guid, int creatorGuid, Vector2 pos, int explosionRadius, BombermenServer sv)
    {
        super(TYPE_BOMB, guid, pos, sv);

        _creatorGuid = creatorGuid;

        _explosionRadius = explosionRadius;
        _bombTimer = 0;
    }

    /**
     * Gets the explosion radius.
     *
     * @return the int
     */
    public int GetExplosionRadius()
    {
        return _explosionRadius;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSpawnMessage()
     */
    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_BOMB(GetGuid(), _creatorGuid, GetX(), GetY());
    }

    /** The Constant size. */
    private static final Vector2 size = new Vector2(Constants.BOMB_WIDTH, Constants.BOMB_HEIGHT);

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

    /**
     * Trigger explosion.
     */
    public void TriggerExplosion()
    {
        _bombTimer += Constants.BOMB_TIMER;
    }

    /**
     * Adds the on explode handler.
     *
     * @param eh the eh
     */
    public void AddOnExplodeHandler(ExplodeHandler eh)
    {
        _explodeHandler = eh;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#Update(int)
     */
    @Override
    public void Update(int diff)
    {
        _bombTimer += diff;

        if (_bombTimer > Constants.BOMB_TIMER)
        {
            // destroy this bomb
            _server.SendAll(new SMSG_DESTROY(GetGuid()));
            _server.RemoveEntityNextUpdate(GetGuid());

            CalculateRadiuses();

            Vector2 pos = new Vector2(GetX() - (Constants.WALL_WIDTH - Constants.BOMB_WIDTH), GetY() - (Constants.WALL_HEIGHT - Constants.BOMB_HEIGHT));

            int id = _server.IncLastId();
            Explosion exCenter = new Explosion(id, _creatorGuid, pos, Direction.NONE, false, _server);
            _server.CreateEntityNextUpdate(exCenter);
            _server.SendAll(exCenter.GetSpawnMessage());

            for (int d : Direction.Directions)
            {
                for (int i = 1; i <= _radius[d]; ++i)
                {
                    id = _server.IncLastId();

                    pos.x = Direction.ApplyMovementX(GetX() - (Constants.WALL_WIDTH - Constants.BOMB_WIDTH), d, i * Constants.EXPLOSION_WIDTH);
                    pos.y = Direction.ApplyMovementY(GetY() - (Constants.WALL_HEIGHT - Constants.BOMB_HEIGHT), d, i * Constants.EXPLOSION_HEIGHT);

                    Explosion ex = new Explosion(id, _creatorGuid, pos, d, i == _radius[d], _server);
                    _server.CreateEntityNextUpdate(ex);
                    _server.SendAll(ex.GetSpawnMessage());
                }
            }

            if (_explodeHandler != null) _explodeHandler.OnExplode();
        }
    }

    /**
     * Calculate radiuses.
     */
    public void CalculateRadiuses()
    {
        boolean[] draw = { true, true, true, true };

        Rectangle r = new Rectangle(0, 0, Constants.EXPLOSION_WIDTH - 4, Constants.EXPLOSION_HEIGHT - 4); // Magic number 4 -> Bounding Box emulation (TODO: Implement bounding boxes)

        for (int d : Direction.Directions)
        {
            for (int i = 1; i <= _explosionRadius; ++i)
            {
                r.x = Direction.ApplyMovementX(GetX() - (Constants.WALL_WIDTH - Constants.BOMB_WIDTH), d, i * Constants.EXPLOSION_WIDTH) + 2; // Magic number 2 ->
                r.y = Direction.ApplyMovementY(GetY() - (Constants.WALL_HEIGHT - Constants.BOMB_HEIGHT), d, i * Constants.EXPLOSION_HEIGHT) + 2; // -> Bounding Box emulation

                if (draw[d])
                {
                    _radius[d]++;
                    for (Entity e : _server.GetEntities().values())
                    {
                        if (e.IsWall() && e.Collides(r))
                        {
                            Wall w = e.ToWall();
                            if (w.IsUndestroyable()) _radius[d]--;
                            draw[d] = false;
                            break;
                        }
                    }
                }

                if (!draw[d]) break;
            }
        }
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#OnDestroy()
     */
    @Override
    public void OnDestroy()
    {
    }
}
