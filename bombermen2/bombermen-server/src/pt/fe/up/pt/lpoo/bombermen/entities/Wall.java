package pt.fe.up.pt.lpoo.bombermen.entities;

import pt.up.fe.pt.lpoo.bombermen.BombermenServer;
import pt.up.fe.pt.lpoo.bombermen.CollisionHandler;
import pt.up.fe.pt.lpoo.bombermen.Constants;
import pt.up.fe.pt.lpoo.bombermen.Entity;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_WALL;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Wall extends Entity
{
    @Override
    public Rectangle GetBoundingRectangle()
    {
        _boundingRectangle.x = GetX() + offsetWidth;
        _boundingRectangle.y = GetY() + offsetHeight;
        return _boundingRectangle;
    }

    private static final float offsetWidth = (Constants.WALL_WIDTH - Constants.WALL_BOUNDING_WIDTH) / 2.f;
    private static final float offsetHeight = (Constants.WALL_HEIGHT - Constants.WALL_BOUNDING_HEIGHT) / 2.f;

    private Rectangle _boundingRectangle = new Rectangle(0, 0, Constants.WALL_BOUNDING_WIDTH, Constants.WALL_BOUNDING_HEIGHT);

    private static CollisionHandler<Wall> cHandler = new CollisionHandler<Wall>()
    {
    };

    private int _hp;

    public Wall(int guid, int hp, Vector2 pos, BombermenServer sv)
    {
        super(TYPE_WALL, guid, pos, sv);

        _hp = hp;
    }

    public boolean IsUndestroyable()
    {
        return _hp == -1;
    }

    public void DecHP(int val)
    {
        if (!IsUndestroyable())
        {
            _hp -= val;
            if (_hp < 0) _hp = 0;
        }
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_WALL(GetGuid(), _hp, GetX(), GetY());
    }

    public static final Vector2 Size = new Vector2(Constants.WALL_WIDTH, Constants.WALL_HEIGHT);

    @Override
    public Vector2 GetSize()
    {
        return Size;
    }

    @Override
    public void OnCollision(Entity e)
    {
        cHandler.OnCollision(this, e);
    }

    @Override
    public void Update(int diff)
    {
        if (!IsUndestroyable() && _hp == 0)
            _server.RemoveEntityNextUpdate(GetGuid());
    }

    @Override
    public void OnDestroy()
    {
        if (MathUtils.random() < Constants.POWER_UP_SPAWN_CHANCE)
        {
            PowerUp pu = new PowerUp(_server.IncLastId(),
                    new Vector2(GetX() + 0.05f * Constants.WALL_WIDTH, GetY() + 0.05f * Constants.WALL_HEIGHT),
                    MathUtils.random(0, Constants.NUMBER_OF_POWER_UP_TYPES - 1), _server);

            SMSG_SPAWN spawnMessage = pu.GetSpawnMessage();

            _server.SendAll(spawnMessage);
            _server.CreateEntityNextUpdate(pu);
        }
    }
}
