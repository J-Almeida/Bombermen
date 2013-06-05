package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor
{
    @Override
    public String toString()
    {
        return "[Entity - Type: " + _type + " Guid: " + _guid + " Position: " + getX() + ", " + getY() + " ]";
    }

    public static final int TYPE_PLAYER = 0;
    public static final int TYPE_BOMB = 1;
    public static final int TYPE_EXPLOSION = 2;
    public static final int TYPE_WALL = 3;
    public static final int TYPE_POWER_UP = 4;

    private int _type;
    private int _guid;

    Entity(int type, int guid)
    {
        _type = type;
        _guid = guid;
    }

    public int GetType()
    {
        return _type;
    }

    public int GetGuid()
    {
        return _guid;
    }
    public boolean IsPlayer()
    {
        return _type == TYPE_PLAYER;
    }

    public boolean IsBomb()
    {
        return _type == TYPE_BOMB;
    }

    public boolean IsExplosion()
    {
        return _type == TYPE_EXPLOSION;
    }

    public boolean IsWall()
    {
        return _type == TYPE_WALL;
    }

    public boolean IsPowerUp()
    {
        return _type == TYPE_POWER_UP;
    }

    public Player ToPlayer()
    {
        return IsPlayer() ? (Player) this : null;
    }

    public Bomb ToBomb()
    {
        return IsBomb() ? (Bomb) this : null;
    }

    public Explosion ToExplosion()
    {
        return IsExplosion() ? (Explosion) this : null;
    }

    public Wall ToWall()
    {
        return IsWall() ? (Wall) this : null;
    }

    public PowerUp ToPowerUp()
    {
        return IsPowerUp() ? (PowerUp) this : null;
    }

    public abstract void draw(SpriteBatch batch, float parentAlpha);
}
