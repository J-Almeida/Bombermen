package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The Class PowerUp.
 */
public class PowerUp extends Entity
{
    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
     */
    @Override
    public void act(float delta)
    {
        _stateTime += delta;
    }

    /**
     * Instantiates a new power up.
     *
     * @param guid the guid
     * @param anim the animation
     */
    public PowerUp(int guid, Animation anim)
    {
        super(Entity.TYPE_POWER_UP, guid);

        _stateTime = 0;
        _animation = anim;

        BoundRect.setWidth(Constants.POWER_UP_WIDTH);
        BoundRect.setHeight(Constants.POWER_UP_HEIGHT);
    }

    /** The state time. */
    private float _stateTime;

    /** The texture regions. */
    public static TextureRegion Regions[][];

    /** The animation. */
    private Animation _animation = null;

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
     */
    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(_animation.getKeyFrame(_stateTime, true), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
