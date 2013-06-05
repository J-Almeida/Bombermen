package pt.up.fe.pt.lpoo.bombermen.entities;

import pt.up.fe.pt.lpoo.bombermen.Entity;
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

    public Player(int guid, String name)
    {
        super(Entity.TYPE_PLAYER, guid);

        _direction = Direction.SOUTH;
    }

    private int _direction;
    public static TextureRegion Regions[][];

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
