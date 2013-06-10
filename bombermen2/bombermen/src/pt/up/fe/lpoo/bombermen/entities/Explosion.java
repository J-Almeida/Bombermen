package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The Class Explosion.
 */
public class Explosion extends Entity
{
    /** The texture regions. */
    public static TextureRegion Regions[][] = null;

    /** The direction. */
    private int _direction;

    /** The end. */
    private boolean _end;

    /** The intensity. */
    private int _intensity;

    /** The explosion timer. */
    private int _explosionTimer;

    /**
     * Instantiates a new explosion.
     *
     * @param guid the guid
     * @param direction the direction
     * @param end the end
     */
    public Explosion(int guid, int direction, boolean end)
    {
        super(Entity.TYPE_EXPLOSION, guid);

        _direction = direction;
        _end = end;
        _intensity = 0;
        _explosionTimer = 0;

        BoundRect.setWidth(Constants.EXPLOSION_WIDTH);
        BoundRect.setHeight(Constants.EXPLOSION_HEIGHT);
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);

        _explosionTimer += delta * 1000;

        if (_explosionTimer < 67)
            _intensity = 0;
        else if (_explosionTimer < 134)
            _intensity = 1;
        else if (_explosionTimer < 200)
            _intensity = 2;
        else if (_explosionTimer < 267)
            _intensity = 3;
        else if (_explosionTimer < 334)
            _intensity = 4;
        else if (_explosionTimer < 400)
            _intensity = 3;
        else if (_explosionTimer < 467)
            _intensity = 2;
        else if (_explosionTimer < 534)
            _intensity = 1;
        else if (_explosionTimer < 600)
            _intensity = 0;
        else
            remove();
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
     */
    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        int col = -1;
        int line = _intensity;

        switch (_direction)
        {
            case Direction.NONE:
                col = 2;
                break;
            case Direction.NORTH:
                col = _end ? 5 : 6;
                break;
            case Direction.SOUTH:
                col = _end ? 7 : 8;
                break;
            case Direction.EAST:
                col = _end ? 3 : 4;
                break;
            case Direction.WEST:
                col = _end ? 0 : 1;
                break;
        }

        batch.draw(Regions[line][col], getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
