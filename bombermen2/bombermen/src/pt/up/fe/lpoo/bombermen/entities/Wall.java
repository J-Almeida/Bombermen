package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * The Class Wall.
 */
public class Wall extends Entity
{
    /** The Constant offsetWidth. */
    private static final float offsetWidth = (Constants.WALL_WIDTH - Constants.WALL_BOUNDING_WIDTH) / 2.f;

    /** The Constant offsetHeight. */
    private static final float offsetHeight = (Constants.WALL_HEIGHT - Constants.WALL_BOUNDING_HEIGHT) / 2.f;

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetBoundingRectangle()
     */
    @Override
    public Rectangle GetBoundingRectangle()
    {
        Rectangle rect = super.GetBoundingRectangle();
        rect.x += offsetWidth;
        rect.y += offsetHeight;
        return rect;
    }

    /**
     * Instantiates a new wall.
     *
     * @param guid the guid
     * @param hitPoints the hit points, -1 if undestroyable
     */
    public Wall(int guid, int hitPoints)
    {
        super(Entity.TYPE_WALL, guid);

        _hitPoints = hitPoints;

        BoundRect.setWidth(Constants.WALL_BOUNDING_WIDTH);
        BoundRect.setHeight(Constants.WALL_BOUNDING_HEIGHT);
    }

    /** The hit points. */
    private int _hitPoints;

    /** The texture regions. */
    public static TextureRegion Regions[][];

    /**
     * @return number of hitpoints, can be negative for undestroyable walls
     */
    public int GetHitPoints()
    {
        return _hitPoints;
    }

    /**
     * Checks if is undestroyable.
     *
     * @return true, if successful
     */
    public boolean IsUndestroyable()
    {
        return _hitPoints != -1;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
     */
    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(Regions[0][IsUndestroyable() ? 1 : 0], getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
