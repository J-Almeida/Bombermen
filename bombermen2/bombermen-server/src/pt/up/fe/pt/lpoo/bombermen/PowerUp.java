package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;

import com.badlogic.gdx.math.Vector2;

public class PowerUp extends Entity
{
    private static CollisionHandler<PowerUp> cHandler = new CollisionHandler<PowerUp>()
    {

        @Override
        protected void CollideBomb(PowerUp e1, Bomb b)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollidePlayer(PowerUp e1, Player p)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollideWall(PowerUp e1, Wall w)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollideExplosion(PowerUp e1, Explosion e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollidePowerUp(PowerUp e1, PowerUp p)
        {
            // TODO Auto-generated method stub

        }

    };

    public PowerUp(int guid, Vector2 pos)
    {
        super(TYPE_POWER_UP, guid, pos);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_POWER_UP(GetGuid());
    }

    private static final Vector2 size = new Vector2(0.9f * Constants.CELL_SIZE, 0.9f * Constants.CELL_SIZE);

    @Override
    public Vector2 GetSize()
    {
        return size;
    }

    @Override
    public void OnCollision(Entity e)
    {
        cHandler.OnCollision(this, e);

    }
}
