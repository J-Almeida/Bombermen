package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ControlPad
{
    public static final int DIR_NONE = -1;
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LEFT = 2;
    public static final int DIR_RIGHT = 3;

    private Texture _texture;
    private Sprite _sprite;

    public ControlPad()
    {
        _texture = new Texture(Gdx.files.internal("data/dpad.png"));
        _sprite = new Sprite(_texture);
        _sprite.setOrigin(0, 0);
        _sprite.setPosition(0, 0);

    }

    public float GetWidth()
    {
        return _sprite.getWidth();
    }

    public float GetHeight()
    {
        return _sprite.getHeight();
    }

    public void SetSize(float width, float height)
    {
        _sprite.setSize(width, height);
    }

    public void ChangeSize(float dW, float dH)
    {
        _sprite.setSize(_sprite.getWidth() + dW, _sprite.getHeight() + dH);
    }

    public int Click(Vector3 pos)
    {
        if (!_sprite.getBoundingRectangle().contains(pos.x, pos.y)) return DIR_NONE;

        float x1 = pos.x - _sprite.getX();
        float y1 = pos.y - _sprite.getY();

        float y2 = _sprite.getHeight() / 3;
        float x2 = _sprite.getWidth() / 3;

        if (x1 > x2 && x1 < (2 * x2))
        {
            if (y1 < y2)
                return DIR_DOWN;
            else if (y1 > 2 * y2) return DIR_UP;
        }
        else if (y1 > y2 && y1 < (2 * y2)) return x1 > x2 ? DIR_RIGHT : DIR_LEFT;

        return DIR_NONE;

    }

    @Override
    protected void finalize() throws Throwable
    {
        _texture.dispose();
        super.finalize();
    }

    public void draw(SpriteBatch spriteBatch)
    {
        _sprite.draw(spriteBatch);
    }
}
