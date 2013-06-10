package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.BombermenServer;
import pt.up.fe.lpoo.bombermen.CollisionHandler;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_WALL;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The Class Wall.
 */
public class Wall extends Entity
{

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetBoundingRectangle()
     */
    @Override
    public Rectangle GetBoundingRectangle()
    {
        _boundingRectangle.x = GetX() + offsetWidth;
        _boundingRectangle.y = GetY() + offsetHeight;
        return _boundingRectangle;
    }

    /** The Constant offsetWidth. */
    private static final float offsetWidth = (Constants.WALL_WIDTH - Constants.WALL_BOUNDING_WIDTH) / 2.f;

    /** The Constant offsetHeight. */
    private static final float offsetHeight = (Constants.WALL_HEIGHT - Constants.WALL_BOUNDING_HEIGHT) / 2.f;

    /** The bounding rectangle. */
    private Rectangle _boundingRectangle = new Rectangle(0, 0, Constants.WALL_BOUNDING_WIDTH, Constants.WALL_BOUNDING_HEIGHT);

    /** The c handler. */
    private static CollisionHandler<Wall> cHandler = new CollisionHandler<Wall>()
    {};

    /** The hp. */
    private int _hp;

    /**
     * Instantiates a new wall.
     *
     * @param guid the guid
     * @param hp the hp
     * @param pos the pos
     * @param sv the sv
     */
    public Wall(int guid, int hp, Vector2 pos, BombermenServer sv)
    {
        super(TYPE_WALL, guid, pos, sv);

        _hp = hp;
    }

    /**
     * Checks if is undestroyable.
     *
     * @return true, if successful
     */
    public boolean IsUndestroyable()
    {
        return _hp == -1;
    }

    /**
     * Dec hp.
     *
     * @param val the val
     */
    public void DecHP(int val)
    {
        if (!IsUndestroyable())
        {
            _hp -= val;
            if (_hp < 0) _hp = 0;
        }
    }

    /**
     * Checks if is alive.
     *
     * @return true, if successful
     */
    public boolean IsAlive()
    {
        return IsUndestroyable() || _hp > 0;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSpawnMessage()
     */
    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_WALL(GetGuid(), _hp, GetX(), GetY());
    }

    /** The Constant Size. */
    public static final Vector2 Size = new Vector2(Constants.WALL_WIDTH, Constants.WALL_HEIGHT);

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSize()
     */
    @Override
    public Vector2 GetSize()
    {
        return Size;
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
        if (!IsUndestroyable() && _hp == 0) _server.RemoveEntityNextUpdate(GetGuid());
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#OnDestroy()
     */
    @Override
    public void OnDestroy()
    {
        if (MathUtils.random() < Constants.POWER_UP_SPAWN_CHANCE)
        {
            PowerUp pu = new PowerUp(_server.IncLastId(), new Vector2(GetX() + 0.05f * Constants.WALL_WIDTH, GetY() + 0.05f * Constants.WALL_HEIGHT), MathUtils.random(0, Constants.NUMBER_OF_POWER_UP_TYPES - 1), _server);

            SMSG_SPAWN spawnMessage = pu.GetSpawnMessage();

            _server.SendAll(spawnMessage);
            _server.CreateEntityNextUpdate(pu);
        }
    }
}
