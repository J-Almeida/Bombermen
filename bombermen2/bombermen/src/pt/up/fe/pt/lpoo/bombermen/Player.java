package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity
{
    Player(Vector2 pos, int guid, String name)
    {
        super(Entity.TYPE_PLAYER, pos, guid);

        _name = name;
        _currentBombs = 0;
        _maxBombs = Constants.INIT_NUM_MAX_BOMBS;
        _bombRadius = Constants.INIT_BOMB_RADIUS;
        _speed = Constants.INIT_PLAYER_SPEED;
    }

    private String _name;
    private int _currentBombs;
    private int _maxBombs;
    private int _bombRadius;
    private int _direction;
    private float _speed;

    public float GetSpeed()
    {
        return _speed;
    }
    
    public int GetBombRadius()
    {
        return _bombRadius;
    }

    public void UpdateCurrentBombs(int inc)
    {
        _currentBombs += inc;
    }

    public void UpdateMaxBombs(int inc)
    {
        _maxBombs += inc;
    }

    public void UpdateBombRadius(int inc)
    {
        _bombRadius += inc;
    }

    public void SetBombRadius(int val)
    {
        _bombRadius = val;
    }

    @Override
    public void OnCollision(Entity other)
    {
    }

    @Override
    public void OnDestroy()
    {
    }

    @Override
    public void OnExplode(Explosion e)
    {
    }
}
