package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.entities.Bomb;
import pt.up.fe.lpoo.bombermen.entities.Explosion;
import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.entities.PowerUp;
import pt.up.fe.lpoo.bombermen.entities.Wall;

public abstract class CollisionHandler<T extends Entity>
{
    public final void OnCollision(T e1, Entity e2)
    {
        switch (e2.GetType())
        {
            case Entity.TYPE_BOMB:
                CollideBomb(e1, e2.ToBomb());
                break;
            case Entity.TYPE_PLAYER:
                CollidePlayer(e1, e2.ToPlayer());
                break;
            case Entity.TYPE_WALL:
                CollideWall(e1, e2.ToWall());
                break;
            case Entity.TYPE_EXPLOSION:
                CollideExplosion(e1, e2.ToExplosion());
                break;
            case Entity.TYPE_POWER_UP:
                CollidePowerUp(e1, e2.ToPowerUp());
                break;
        }
    }

    protected void CollideBomb(T e1, Bomb b)
    {
    }

    protected void CollidePlayer(T e1, Player p)
    {
    }

    protected void CollideWall(T e1, Wall w)
    {
    }

    protected void CollideExplosion(T e1, Explosion e)
    {
    }

    protected void CollidePowerUp(T e1, PowerUp p)
    {
    }
}
