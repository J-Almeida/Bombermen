package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wall extends Entity
{
    Wall(Sprite sprite, int guid, int hitPoints)
    {
        super(Entity.TYPE_WALL, sprite, guid);

        _hitPoints = hitPoints;
    }

    private int _hitPoints;

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
        if (IsUndestroyable()) return;

        int dmg = e.GetStrength();

        _hitPoints -= dmg;
        if (_hitPoints < 0) _hitPoints = 0;
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        batch.draw(_regions[0][IsUndestroyable() ? 1 : 0], _sprite.getX(), _sprite.getY(), Constants.CELL_SIZE, Constants.CELL_SIZE);
    }
}
