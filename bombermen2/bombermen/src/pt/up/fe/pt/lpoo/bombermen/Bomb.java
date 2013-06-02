package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bomb extends Entity
{
    Bomb(Sprite sprite, int guid, Player owner, int radius, int strength, int time)
    {
        super(Entity.TYPE_BOMB, sprite, guid);

        _radius = radius;
        _strength = strength;
        _time = time;
        _owner = owner;
    }

    private int _radius; // Number of cells in each direction
    private int _strength; // Damage in number of hitpoints
    private int _time; // Time to explode
    private Player _owner; // Player who planted the bomb

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
        _owner.UpdateCurrentBombs(-1);
        Gdx.app.debug("Bomb", "Bomb exploded");
    }

    @Override
    public void OnExplode(Explosion e)
    {
    }
}
