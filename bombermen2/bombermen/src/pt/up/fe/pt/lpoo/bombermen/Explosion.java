package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.math.Vector2;

public class Explosion extends Entity
{
    Explosion(Vector2 pos, int guid, int strength)
    {
        super(Entity.TYPE_EXPLOSION, pos, guid);
        
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
