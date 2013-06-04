package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends Entity
{
    Wall(int guid, int hitPoints)
    {
        super(Entity.TYPE_WALL, guid);

        _hitPoints = hitPoints;
    }

    private int _hitPoints;
    public static TextureRegion Regions[][];

    public boolean IsUndestroyable()
    {
        return _hitPoints != -1;
    }

    @Override
    public void OnCollision(Entity other)
    {
    }

    @Override
    public void OnDestroy()
    {
        // @TODO: Spawn random powerup
    }

    @Override
    public void OnExplode(Explosion e)
    {
        /*
        if (IsUndestroyable()) return;

        int dmg = e.GetStrength();

        _hitPoints -= dmg;
        if (_hitPoints < 0) _hitPoints = 0;
        */
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(Regions[0][IsUndestroyable() ? 1 : 0], getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
