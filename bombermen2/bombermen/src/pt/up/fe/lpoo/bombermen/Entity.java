package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.entities.Bomb;
import pt.up.fe.lpoo.bombermen.entities.Explosion;
import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.entities.PowerUp;
import pt.up.fe.lpoo.bombermen.entities.Wall;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Base class of all entities
 */
public abstract class Entity extends Actor
{

    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#toString()
     */
    @Override
    public String toString()
    {
        return "[Entity - Type: " + _type + " Guid: " + _guid + " Position: " + getX() + ", " + getY() + " ]";
    }

    /** The bounding rectangle. */
    protected Rectangle BoundRect = new Rectangle(0, 0, getWidth(), getHeight());

    /**
     * Gets the bounding rectangle.
     *
     * @return the rectangle
     */
    public Rectangle GetBoundingRectangle()
    {
        BoundRect.x = getX();
        BoundRect.y = getY();
        return BoundRect;
    }

    /**
     * Collides.
     *
     * @param e the entity
     * @return true, if successful
     */
    public boolean Collides(Entity e)
    {
        Rectangle my = GetBoundingRectangle();
        Rectangle other = e.GetBoundingRectangle();
        return my.overlaps(other);
    }

    /**
     * Collides.
     *
     * @param r the rectangle
     * @return true, if successful
     */
    public boolean Collides(Rectangle r)
    {
        return GetBoundingRectangle().overlaps(r);
    }

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

    /**
     * Instantiates a new entity.
     *
     * @param type the type
     * @param guid the guid
     */
    public Entity(int type, int guid)
    {
        _type = type;
        _guid = guid;
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
     * Cast to bomb.
     *
     * @return the bomb
     */
    public Bomb ToBomb()
    {
        return IsBomb() ? (Bomb) this : null;
    }

    /**
     * Cast to explosion.
     *
     * @return the explosion
     */
    public Explosion ToExplosion()
    {
        return IsExplosion() ? (Explosion) this : null;
    }

    /**
     * Cast to wall.
     *
     * @return the wall
     */
    public Wall ToWall()
    {
        return IsWall() ? (Wall) this : null;
    }

    /**
     * Cast to power up.
     *
     * @return the power up
     */
    public PowerUp ToPowerUp()
    {
        return IsPowerUp() ? (PowerUp) this : null;
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
     */
    public abstract void draw(SpriteBatch batch, float parentAlpha);
}
