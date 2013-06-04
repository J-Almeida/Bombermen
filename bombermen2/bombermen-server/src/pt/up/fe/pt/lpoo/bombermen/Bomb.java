package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;

import com.badlogic.gdx.math.Vector2;

public class Bomb extends Entity
{
    private static CollisionHandler<Bomb> cHandler = new CollisionHandler<Bomb>()
    {};

    private int _explosionRadius;
    private boolean _justCreated = true;
    private int _creatorGuid;
    
    public int GetCreatorGuid()
    {
        return _creatorGuid;
    }
    
    public void SetJustCreated(boolean val)
    {
        _justCreated = false;
    }
    
    public boolean JustCreated() 
    {
        return _justCreated;
    }

    Bomb(int guid, int creatorGuid, Vector2 pos, int explosionRadius, BombermenServer sv)
    {
        super(TYPE_BOMB, guid, pos, sv);

        _creatorGuid = creatorGuid;
        
        _explosionRadius = explosionRadius;
    }

    public int GetExplosionRadius()
    {
        return _explosionRadius;
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_BOMB(GetGuid(), GetX(), GetY());
    }

    private static final Vector2 size = new Vector2(Constants.BOMB_WIDTH, Constants.BOMB_HEIGHT);

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

    @Override
    public void Update(int diff)
    {
        // TODO Auto-generated method stub

    }
}
