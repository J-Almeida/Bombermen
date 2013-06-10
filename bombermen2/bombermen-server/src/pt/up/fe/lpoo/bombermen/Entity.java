package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.entities.Bomb;
import pt.up.fe.lpoo.bombermen.entities.Explosion;
import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.entities.PowerUp;
import pt.up.fe.lpoo.bombermen.entities.Wall;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The Class Entity.
 */
public abstract class Entity
{
    /** The Constant TYPE_PLAYER. */
    public static final int TYPE_PLAYER = 0;

    /** The Constant TYPE_BOMB. */
    public static final int TYPE_BOMB = 1;

    /** The Constant TYPE_EXPLOSION. */
    public static final int TYPE_EXPLOSION = 2;

    /** The Constant TYPE_WALL. */
    public static final int TYPE_WALL = 3;

    /** The Constant TYPE_POWER_UP. */
    public static final int TYPE_POWER_UP = 4;

    /** The type. */
    private int _type;

    /** The guid. */
    private int _guid;

    /** The bounding rectangle. */
    protected Rectangle _rect;

    /** The server. */
    protected final BombermenServer _server;

    /**
     * Gets the bounding rectangle.
     *
     * @return the rectangle
     */
    public Rectangle GetBoundingRectangle()
    {
        return _rect;
    }

    /**
     * Instantiates a new entity.
     *
     * @param type the type
     * @param guid the guid
     * @param pos the pos
     * @param sv the sv
     */
    public Entity(int type, int guid, Vector2 pos, BombermenServer sv)
    {
        _type = type;
        _guid = guid;
        _server = sv;
        Vector2 size = this.GetSize();
        _rect = new Rectangle(pos.x, pos.y, size.x, size.y);
    }

    /**
     * Collides.
     *
     * @param e the e
     * @return true, if successful
     */
    public boolean Collides(Entity e)
    {
        return GetBoundingRectangle().overlaps(e.GetBoundingRectangle());
    }

    /**
     * Collides.
     *
     * @param r the r
     * @return true, if successful
     */
    public boolean Collides(Rectangle r)
    {
        return GetBoundingRectangle().overlaps(r);
    }

    /**
     * Gets the type.
     *
     * @return the int
     */
    public int GetType()
    {
        return _type;
    }

    /**
     * Gets the guid.
     *
     * @return the int
     */
    public int GetGuid()
    {
        return _guid;
    }

    /**
     * Sets the x.
     *
     * @param x the x
     */
    public void SetX(float x)
    {
        _rect.x = x;
    }

    /**
     * Sets the y.
     *
     * @param y the y
     */
    public void SetY(float y)
    {
        _rect.y = y;
    }

    /**
     * Gets the x.
     *
     * @return the float
     */
    public float GetX()
    {
        return _rect.x;
    }

    /**
     * Gets the y.
     *
     * @return the float
     */
    public float GetY()
    {
        return _rect.y;
    }

    /**
     * Sets the position.
     *
     * @param x the x
     * @param y the y
     */
    public void SetPosition(float x, float y)
    {
        _rect.x = x;
        _rect.y = y;
    }

    /**
     * Gets the position.
     *
     * @return the vector2
     */
    public Vector2 GetPosition()
    {
        return new Vector2(_rect.x, _rect.y);
    }

    /**
     * Checks if is player.
     *
     * @return true, if successful
     */
    public boolean IsPlayer()
    {
        return _type == TYPE_PLAYER;
    }

    /**
     * Checks if is bomb.
     *
     * @return true, if successful
     */
    public boolean IsBomb()
    {
        return _type == TYPE_BOMB;
    }

    /**
     * Checks if is explosion.
     *
     * @return true, if successful
     */
    public boolean IsExplosion()
    {
        return _type == TYPE_EXPLOSION;
    }

    /**
     * Checks if is wall.
     *
     * @return true, if successful
     */
    public boolean IsWall()
    {
        return _type == TYPE_WALL;
    }

    /**
     * Checks if is power up.
     *
     * @return true, if successful
     */
    public boolean IsPowerUp()
    {
        return _type == TYPE_POWER_UP;
    }

    /**
     * To player.
     *
     * @return the player
     */
    public Player ToPlayer()
    {
        return IsPlayer() ? (Player) this : null;
    }

    /**
     * To bomb.
     *
     * @return the bomb
     */
    public Bomb ToBomb()
    {
        return IsBomb() ? (Bomb) this : null;
    }

    /**
     * To explosion.
     *
     * @return the explosion
     */
    public Explosion ToExplosion()
    {
        return IsExplosion() ? (Explosion) this : null;
    }

    /**
     * To wall.
     *
     * @return the wall
     */
    public Wall ToWall()
    {
        return IsWall() ? (Wall) this : null;
    }

    /**
     * To power up.
     *
     * @return the power up
     */
    public PowerUp ToPowerUp()
    {
        return IsPowerUp() ? (PowerUp) this : null;
    }

    /**
     * Update.
     *
     * @param diff the diff
     */
    public abstract void Update(int diff);

    /**
     * Gets the spawn message.
     *
     * @return the smsg spawn
     */
    public abstract SMSG_SPAWN GetSpawnMessage();

    /**
     * On collision.
     *
     * @param e the e
     */
    public abstract void OnCollision(Entity e);

    /**
     * Gets the size.
     *
     * @return the vector2
     */
    public abstract Vector2 GetSize();

    /**
     * On destroy.
     */
    public abstract void OnDestroy();
}
