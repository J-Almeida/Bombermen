package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.Assets;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The Class Bomb.
 */
public class Bomb extends Entity
{
    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#remove()
     */
    @Override
    public boolean remove()
    {
        if (super.remove())
        {
            Assets.PlaySound("bomb_explosion");
            return true;
        }

        return false;
    }

    /** The Texture regions. */
    public static TextureRegion Regions[][] = null;

    /** The Animation. */
    public static Animation Animation = null;

    /** The animation state time. */
    private float _stateTime;

    /** The creator guid. */
    private int _creatorGuid;

    /**
     * Instantiates a new bomb.
     *
     * @param guid the guid
     * @param creatorGuid the creator guid
     */
    public Bomb(int guid, int creatorGuid)
    {
        super(Entity.TYPE_BOMB, guid);
        _stateTime = 0;
        _creatorGuid = creatorGuid;

        BoundRect.setWidth(Constants.EXPLOSION_WIDTH);
        BoundRect.setHeight(Constants.EXPLOSION_HEIGHT);
    }

    /**
     * Gets the creator guid.
     *
     * @return the int
     */
    public int GetCreatorGuid()
    {
        return _creatorGuid;
    }

    /** True if bomb was just created. */
    private boolean _justCreated = true;

    /**
     * Sets the just created.
     *
     * @param val the val
     */
    public void SetJustCreated(boolean val)
    {
        _justCreated = val;
    }

    /**
     * Just created.
     *
     * @return true, if successful
     */
    public boolean JustCreated()
    {
        return _justCreated;
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);
        _stateTime += delta;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
     */
    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(Animation.getKeyFrame(_stateTime, true), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
