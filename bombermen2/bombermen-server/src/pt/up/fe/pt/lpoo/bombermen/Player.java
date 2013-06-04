package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity
{
    private static CollisionHandler<Player> cHandler = new CollisionHandler<Player>()
    {

        @Override
        protected void CollideBomb(Player e1, Bomb b)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollidePlayer(Player e1, Player p)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollideWall(Player e1, Wall w)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollideExplosion(Player e1, Explosion e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        protected void CollidePowerUp(Player e1, PowerUp p)
        {
            // TODO Auto-generated method stub

        }

    };

    Player(int guid, String name, Vector2 pos)
    {
        super(TYPE_PLAYER, guid, pos);
        _name = name;
    }

    public String GetName()
    {
        return _name;
    }

    public void SetName(String name)
    {
        _name = name;
    }

    private String _name;

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_PLAYER(GetGuid(), GetName(), GetX(), GetY());
    }

    private static final Vector2 size = new Vector2(18.f * Constants.CELL_SIZE * 0.9f / 26, Constants.CELL_SIZE * 0.9f);

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
