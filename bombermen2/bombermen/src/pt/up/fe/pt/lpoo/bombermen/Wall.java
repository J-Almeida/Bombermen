package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.g2d.Sprite;

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
}
