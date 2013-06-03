package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_WALL;

import com.badlogic.gdx.math.Vector2;

public class Wall extends Entity
{
    private static CollisionHandler<Wall> cHandler = new CollisionHandler<Wall>()
    {

        @Override
        protected void CollideBomb(Wall e1, Bomb b)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollidePlayer(Wall e1, Player p)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollideWall(Wall e1, Wall w)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollideExplosion(Wall e1, Explosion e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollidePowerUp(Wall e1, PowerUp p)
        {
            // TODO Auto-generated method stub

        }

    };

    public Wall(int guid, Vector2 pos)
    {
        super(TYPE_WALL, guid, pos);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_WALL(GetGuid());
    }

    private static final Vector2 size = new Vector2(Constants.CELL_SIZE, Constants.CELL_SIZE);

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
