package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bomb extends Entity
{
    public static TextureRegion Regions[][] = null;
    public static Animation Animation = null;

    private float _stateTime;

    Bomb(int guid)
    {
        super(Entity.TYPE_BOMB, guid);
        _stateTime = 0;
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
    public void act(float delta)
    {
        super.act(delta);
        _stateTime += delta;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(Animation.getKeyFrame(_stateTime, true), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
