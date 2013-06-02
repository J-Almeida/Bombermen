package pt.up.fe.pt.lpoo.bombermen;

import java.awt.Point;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;

public abstract class Entity
{
    public static final int TYPE_PLAYER = 0;
    public static final int TYPE_BOMB = 1;
    public static final int TYPE_EXPLOSION = 2;
    public static final int TYPE_WALL = 3;
    public static final int TYPE_POWER_UP = 4;

    private int _type;
    private int _guid;
    private Point _position;

    Entity(int type, int guid, Point pos)
    {
        _type = type;
        _guid = guid;
        _position = pos;
    }

    public int GetType()
    {
        return _type;
    }

    public int GetGuid()
    {
        return _guid;
    }

    public void SetX(int x)
    {
        _position.x = x;
    }

    public void SetY(int y)
    {
        _position.y = y;
    }

    public int GetX()
    {
        return _position.x;
    }

    public int GetY()
    {
        return _position.y;
    }

    public void SetPosition(int x, int y)
    {
        _position.x = x;
        _position.y = y;
    }

    public Point GetPosition()
    {
        return _position;
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

    public abstract SMSG_SPAWN GetSpawnMessage();
}
