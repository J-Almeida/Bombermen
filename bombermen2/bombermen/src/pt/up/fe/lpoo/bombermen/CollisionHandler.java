package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.entities.Bomb;
import pt.up.fe.lpoo.bombermen.entities.Explosion;
import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.entities.PowerUp;
import pt.up.fe.lpoo.bombermen.entities.Wall;

/**
 * The Class CollisionHandler.
 */
public abstract class CollisionHandler
{
    /**
     * Collide.
     *
     * @param entity the entity
     */
    public final void Collide(Entity entity)
    {
        switch (entity.GetType())
        {
            case Entity.TYPE_BOMB:
                BombHandler(entity.ToBomb());
                break;
            case Entity.TYPE_EXPLOSION:
                ExplosionHandler(entity.ToExplosion());
                break;
            case Entity.TYPE_PLAYER:
                PlayerHandler(entity.ToPlayer());
                break;
            case Entity.TYPE_POWER_UP:
                PowerUpHandler(entity.ToPowerUp());
                break;
            case Entity.TYPE_WALL:
                WallHandler(entity.ToWall());
                break;
            default:
                DefaultHandler(entity);
                break;
        }
    }

    /**
     * Player handler.
     *
     * @param e the player
     */
    protected abstract void PlayerHandler(Player e);

    /**
     * Explosion handler.
     *
     * @param e the explosion
     */
    protected abstract void ExplosionHandler(Explosion e);

    /**
     * Bomb handler.
     *
     * @param e the bomb
     */
    protected abstract void BombHandler(Bomb e);

    /**
     * Power up handler.
     *
     * @param e the powerup
     */
    protected abstract void PowerUpHandler(PowerUp e);

    /**
     * Wall handler.
     *
     * @param e the wall
     */
    protected abstract void WallHandler(Wall e);

    /**
     * Default handler.
     *
     * @param e the entity
     */
    protected abstract void DefaultHandler(Entity e);
}
