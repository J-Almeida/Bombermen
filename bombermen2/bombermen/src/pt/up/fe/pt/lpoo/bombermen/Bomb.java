package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bomb extends Entity
{
    Bomb(Sprite sprite, int guid)
    {
        super(Entity.TYPE_BOMB, sprite, guid);
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
    public void draw(SpriteBatch batch)
    {
        batch.draw(_regions[0][0], _sprite.getX(), _sprite.getY());
    }
}
