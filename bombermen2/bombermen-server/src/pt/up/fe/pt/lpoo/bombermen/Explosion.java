package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;

import com.badlogic.gdx.math.Vector2;

public class Explosion extends Entity
{
    private static CollisionHandler<Explosion> cHandler = new CollisionHandler<Explosion>()
    {

        @Override
        protected void CollideBomb(Explosion e1, Bomb b)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollidePlayer(Explosion e1, Player p)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollideWall(Explosion e1, Wall w)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollideExplosion(Explosion e1, Explosion e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollidePowerUp(Explosion e1, PowerUp p)
        {
            // TODO Auto-generated method stub

        }

    };

    public Explosion(int guid, Vector2 pos)
    {
        super(TYPE_EXPLOSION, guid, pos);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_EXPLOSION(GetGuid(), GetX(), GetY());
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
