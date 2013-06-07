package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PowerUp extends Entity
{
    @Override
    public void act(float delta)
    {
        _stateTime += delta;
    }

    public PowerUp(int guid, Animation anim)
    {
        super(Entity.TYPE_POWER_UP, guid);

        _stateTime = 0;
        _animation = anim;

        BoundRect.setWidth(Constants.POWER_UP_WIDTH);
        BoundRect.setHeight(Constants.POWER_UP_HEIGHT);
    }

    private float _stateTime;
    public static TextureRegion Regions[][];
    public Animation _animation = null;

    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(_animation.getKeyFrame(_stateTime, true), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
