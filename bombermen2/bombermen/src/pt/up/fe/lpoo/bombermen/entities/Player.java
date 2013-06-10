package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.screens.PlayScreen;
import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * The Class Player.
 */
public class Player extends Entity
{
    /** The Constant offsetWidth. */
    private static final float offsetWidth = (Constants.PLAYER_WIDTH - Constants.PLAYER_BOUNDING_WIDTH) / 2.f;

    /** The Constant offsetHeight. */
    private static final float offsetHeight = (Constants.PLAYER_HEIGHT - Constants.PLAYER_BOUNDING_HEIGHT) / 2.f;

    /** The score. */
    private int _score;

    /**
     * Sets the score.
     *
     * @param score the score
     */
    public void SetScore(int score)
    {
        _score = score;
    }

    /**
     * Gets the score.
     *
     * @return the int
     */
    public int GetScore()
    {
        return _score;
    }

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

    /** The Animations. */
    public static Animation[] Animations = { null, null, null, null };

    /** The font to draw player name and score. */
    private static BitmapFont font = new BitmapFont();

    /**
     * Sets the moving.
     *
     * @param dir the dir
     * @param val true if pressing key, false if releasing key
     */
    public void SetMoving(int dir, boolean val)
    {
        _moving[dir] = val;
    }

    /** The moving. */
    private boolean[] _moving = { false, false, false, false };

    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
     */
    @Override
    public void act(float delta)
    {
        _stateTime += delta;
        super.act(delta);

        delta *= 1000;

        Array<Entity> entities = PlayScreen._gameImpl.GetEntities();

        for (int i = 0; i < _moving.length; ++i)
        {
            if (!_moving[i]) continue;

            boolean moved = true;

            float x = getX();
            float y = getY();

            switch (i)
            {
                case Direction.NORTH:
                    setY(getY() + _speed * delta);
                    break;
                case Direction.SOUTH:
                    setY(getY() - _speed * delta);
                    break;
                case Direction.EAST:
                    setX(getX() + _speed * delta);
                    break;
                case Direction.WEST:
                    setX(getX() - _speed * delta);
                    break;
                default:
                    moved = false;
            }

            if (moved)
            {
                for (Entity e : entities)
                {
                    if (e.IsBomb() && e.ToBomb().JustCreated() && e.ToBomb().GetCreatorGuid() == GetGuid()) if (Collides(e))
                        break;
                    else
                        e.ToBomb().SetJustCreated(false);

                    if (!e.IsPlayer() && Collides(e))
                    {
                        moved = false;
                        setX(x);
                        setY(y);
                        break;
                    }

                }
            }
        }

    }

    /** The animation state time. */
    private float _stateTime;

    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#setX(float)
     */
    @Override
    public void setX(float x)
    {
        if (getX() < x)
            _direction = Direction.EAST;
        else if (getX() > x) _direction = Direction.WEST;
        super.setX(x);
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.scenes.scene2d.Actor#setY(float)
     */
    @Override
    public void setY(float y)
    {
        if (getY() < y)
            _direction = Direction.NORTH;
        else if (getY() > y) _direction = Direction.SOUTH;
        super.setY(y);
    }

    /**
     * Instantiates a new player.
     *
     * @param guid the guid
     * @param name the name
     * @param score the score
     */
    public Player(int guid, String name, int score)
    {
        super(Entity.TYPE_PLAYER, guid);

        _direction = Direction.SOUTH;
        setName(name);
        _score = score;

        BoundRect.setWidth(Constants.PLAYER_BOUNDING_WIDTH);
        BoundRect.setHeight(Constants.PLAYER_BOUNDING_HEIGHT);
    }

    /** The speed. */
    private float _speed = Constants.INIT_PLAYER_SPEED;

    /**
     * Sets the speed.
     *
     * @param val the val
     */
    public void SetSpeed(float val)
    {
        _speed = val;
    }

    /**
     * Gets the speed.
     *
     * @return the float
     */
    public float GetSpeed()
    {
        return _speed;
    }

    /** The direction. */
    private int _direction;

    /** The texture regions. */
    public static TextureRegion Regions[][];

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
     */
    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        boolean m = false;
        for (boolean m1 : _moving)
            m = m || m1;

        batch.draw(Animations[_direction].getKeyFrame((m ? _stateTime : 0), true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        String s = getName() + " (" + GetScore() + " pts)";

        font.draw(batch, s, getX() - Constants.PLAYER_WIDTH / 2f - s.length(), getY() + Constants.PLAYER_HEIGHT * 3f / 2f);

    }
}
