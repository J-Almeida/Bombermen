package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.utils.Direction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion extends Entity
{
    public static TextureRegion Regions[][] = null;

    private int _direction;
    private boolean _end;
    private int _intensity;
    private int _explosionTimer;

    Explosion(int guid, int direction, boolean end)
    {
        super(Entity.TYPE_EXPLOSION, guid);

        _direction = direction;
        _end = end;
        _intensity = 0;
        _explosionTimer = 0;
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
