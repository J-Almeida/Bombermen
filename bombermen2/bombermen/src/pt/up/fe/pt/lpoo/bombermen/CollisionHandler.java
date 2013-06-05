package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.entities.Bomb;
import pt.up.fe.pt.lpoo.bombermen.entities.Explosion;
import pt.up.fe.pt.lpoo.bombermen.entities.Player;
import pt.up.fe.pt.lpoo.bombermen.entities.PowerUp;
import pt.up.fe.pt.lpoo.bombermen.entities.Wall;

public abstract class CollisionHandler
{
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

    protected abstract void PlayerHandler(Player e);

    protected abstract void ExplosionHandler(Explosion e);

    protected abstract void BombHandler(Bomb e);

    protected abstract void PowerUpHandler(PowerUp e);

    protected abstract void WallHandler(Wall e);

    protected abstract void DefaultHandler(Entity e);
}
