package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.entities.Bomb;
import pt.up.fe.lpoo.bombermen.entities.Explosion;
import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.entities.PowerUp;
import pt.up.fe.lpoo.bombermen.entities.Wall;

/**
 * The Class CollisionHandler.
 *
 * @param <T> the generic type
 */
public abstract class CollisionHandler<T extends Entity>
{
    /**
     * On collision.
     *
     * @param e1 the first entity
     * @param e2 the second entity
     */
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

    /**
     * Collide bomb.
     *
     * @param e1 the e1
     * @param b the b
     */
    protected void CollideBomb(T e1, Bomb b)
    {
    }

    /**
     * Collide player.
     *
     * @param e1 the e1
     * @param p the p
     */
    protected void CollidePlayer(T e1, Player p)
    {
    }

    /**
     * Collide wall.
     *
     * @param e1 the e1
     * @param w the w
     */
    protected void CollideWall(T e1, Wall w)
    {
    }

    /**
     * Collide explosion.
     *
     * @param e1 the e1
     * @param e the e
     */
    protected void CollideExplosion(T e1, Explosion e)
    {
    }

    /**
     * Collide power up.
     *
     * @param e1 the e1
     * @param p the p
     */
    protected void CollidePowerUp(T e1, PowerUp p)
    {
    }
}
