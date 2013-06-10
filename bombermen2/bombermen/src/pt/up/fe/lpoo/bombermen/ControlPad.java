package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * ControlPad for Android
 */
public class ControlPad implements Disposable
{
    /** The Constant PLACE_BOMB. */
    public static final int PLACE_BOMB = 4;

    /** The pad texture. */
    private Texture _padTexture;

    /** The bomb button texture. */
    private Texture _bombButtonTexture;

    /** The pad sprite. */
    private Sprite _padSprite;

    /** The bomb button sprite. */
    private Sprite _bombButtonSprite;

    /**
     * Instantiates a new control pad.
     */
    public ControlPad()
    {
        _padTexture = Assets.GetTexture("dpad");
        _padSprite = new Sprite(_padTexture);
        _padSprite.setOrigin(0, 0);

        _padSprite.setPosition(0, 0);

        _bombButtonTexture = Assets.GetTexture("bombButton");
        _bombButtonSprite = new Sprite(_bombButtonTexture);
        _bombButtonSprite.setOrigin(_bombButtonTexture.getWidth(), 0);
        _bombButtonSprite.setPosition(Constants.DEFAULT_WIDTH - _bombButtonTexture.getWidth(), 0);
    }

    /**
     * Gets the width.
     *
     * @return the float
     */
    public float GetWidth()
    {
        return _padSprite.getWidth();
    }

    /**
     * Gets the height.
     *
     * @return the float
     */
    public float GetHeight()
    {
        return _padSprite.getHeight();
    }

    /**
     * Sets the size.
     *
     * @param width the width
     * @param height the height
     */
    public void SetSize(float width, float height)
    {
        _padSprite.setSize(width, height);
        _bombButtonSprite.setSize(width * Constants.DEFAULT_PAD_BUTTON_SIZE_MULT, height * Constants.DEFAULT_PAD_BUTTON_SIZE_MULT);
        _bombButtonSprite.setPosition(Constants.DEFAULT_WIDTH - _bombButtonSprite.getWidth(), 0);
    }

    /**
     * Change size.
     *
     * @param dW the d w
     * @param dH the d h
     */
    public void ChangeSize(float dW, float dH)
    {
        SetSize(_padSprite.getWidth() + dW, _padSprite.getHeight() + dH);
    }

    /**
     * Click.
     *
     * @param pos the pos
     * @return the int
     */
    public int Click(Vector3 pos)
    {
        if (_padSprite.getBoundingRectangle().contains(pos.x, pos.y))
        {
            float x1 = pos.x - _padSprite.getX();
            float y1 = pos.y - _padSprite.getY();

            float y2 = _padSprite.getHeight() / 3;
            float x2 = _padSprite.getWidth() / 3;

            if (x1 > x2 && x1 < (2 * x2))
            {
                if (y1 < y2)
                    return Direction.SOUTH;
                else if (y1 > 2 * y2) return Direction.NORTH;
            }
            else if (y1 > y2 && y1 < (2 * y2)) return x1 > x2 ? Direction.EAST : Direction.WEST;
        }
        else if (_bombButtonSprite.getBoundingRectangle().contains(pos.x, pos.y)) { return PLACE_BOMB; }

        return Direction.NONE;
    }

    /**
     * Draw.
     *
     * @param spriteBatch the sprite batch
     */
    public void draw(SpriteBatch spriteBatch)
    {
        _padSprite.draw(spriteBatch);
        _bombButtonSprite.draw(spriteBatch);
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.utils.Disposable#dispose()
     */
    @Override
    public void dispose()
    {
        _padTexture.dispose();
        _bombButtonTexture.dispose();
    }
}
