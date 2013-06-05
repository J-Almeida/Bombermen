package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.utils.Direction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity
{
    public static Animation[] Animations = { null, null, null, null };
    
    private int _moveAnimationTimer = 0;
    
    @Override
    public void act(float delta)
    {
        _stateTime += delta;
        super.act(delta);
    }

    private float _stateTime;
    
    @Override
    public void setX(float x)
    {
        if (getX() < x)
            _direction = Direction.EAST;
        else if (getX() > x)
            _direction = Direction.WEST;
        super.setX(x);
        _moveAnimationTimer += 2;
    }

    @Override
    public void setY(float y)
    {
        if (getY() < y)
            _direction = Direction.NORTH;
        else if (getY() > y)
            _direction = Direction.SOUTH;
        super.setY(y);
        _moveAnimationTimer += 2;
    }

    Player(int guid, String name)
    {
        super(Entity.TYPE_PLAYER, guid);

        //_name = name;
        //_currentBombs = 0;
        //_maxBombs = Constants.INIT_NUM_MAX_BOMBS;
        //_bombRadius = Constants.INIT_BOMB_RADIUS;
        _speed = Constants.INIT_PLAYER_SPEED;
        _direction = Direction.SOUTH;
    }

    //private String _name;
    private int _currentBombs;
    private int _maxBombs;
    private int _bombRadius;
    private int _direction;
    private float _speed;
    public static TextureRegion Regions[][];

    public int GetCurrentBombs()
    {
        return _currentBombs;
    }

    public int GetMaxBombs()
    {
        return _maxBombs;
    }

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

    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        
        
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(Animations[_direction].getKeyFrame( ( _moveAnimationTimer > 0 ? _stateTime : 0), true), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        _moveAnimationTimer -= _moveAnimationTimer > 0 ? 1 : 0;

    }
}
