package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity
{
    public static final int TYPE_PLAYER = 0;
    public static final int TYPE_BOMB = 1;
    public static final int TYPE_EXPLOSION = 2;
    public static final int TYPE_WALL = 3;
    public static final int TYPE_POWER_UP = 4;

    private int _type;
    private Vector2 _position;
    private int _guid;
    
    Entity(int type, Vector2 pos, int guid)
    {
        _type = type;
        _position = pos;
        _guid = guid;
    }
    
    public int GetType() { return _type; }
    public Vector2 GetPosition() { return _position; }
    public int GetGuid() { return _guid; }
    
    public boolean IsPlayer() { return _type == TYPE_PLAYER; }
    public boolean IsBomb() { return _type == TYPE_BOMB; }
    public boolean IsExplosion() { return _type == TYPE_EXPLOSION; }
    public boolean IsWall() { return _type == TYPE_WALL; }
    public boolean IsPowerUp() { return _type == TYPE_POWER_UP; }
    
    public Player ToPlayer() { return IsPlayer() ? (Player)this : null; }
    public Bomb ToBomb() { return IsBomb() ? (Bomb)this : null; }
    public Explosion ToExplosion() { return IsExplosion() ? (Explosion)this : null; }
    public Wall ToWall() { return IsWall() ? (Wall)this : null; }
    public PowerUp ToPowerUp() { return IsPowerUp() ? (PowerUp)this : null; }

    public abstract void OnCollision(Entity other);
    public abstract void OnDestroy();
    public abstract void OnExplode(Explosion e);
}
