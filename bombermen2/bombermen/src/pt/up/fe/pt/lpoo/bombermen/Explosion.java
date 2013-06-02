package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Explosion extends Entity
{
    Explosion(Sprite sprite, int guid, int strength)
    {
        super(Entity.TYPE_EXPLOSION, sprite, guid);

        _strength = strength;
    }

    private int _strength; // Damage in number of hitpoints

    public int GetStrength()
    {
        return _strength;
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
}
