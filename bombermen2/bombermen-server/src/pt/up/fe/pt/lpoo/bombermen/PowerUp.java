package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;

import com.badlogic.gdx.math.Vector2;

public class PowerUp extends Entity
{
    //private static CollisionHandler<PowerUp> cHandler = new CollisionHandler<PowerUp>()
    //{
    //};

    private int _powerUpType;

    public PowerUp(int guid, Vector2 pos, int powerUpType, BombermenServer sv)
    {
        super(TYPE_POWER_UP, guid, pos, sv);

        _powerUpType = powerUpType;
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_POWER_UP(GetGuid(), GetX(), GetY(), _powerUpType);
    }

    private static final Vector2 size = new Vector2(Constants.POWER_UP_WIDTH, Constants.POWER_UP_HEIGHT);

    @Override
    public Vector2 GetSize()
    {
        return size;
    }

    @Override
    public void OnCollision(Entity e)
    {
        //cHandler.OnCollision(this, e);

        if (e.IsPlayer())
        {
            Player p = e.ToPlayer();
            HandlePowerUp(p);
            System.out.println("PowerUp picked-up, removed itself.");
            _server.RemoveEntityNextUpdate(GetGuid());
        }
        else if (e.IsExplosion())
        {
            System.out.println("PowerUp exploded, removed itself.");
            _server.RemoveEntityNextUpdate(GetGuid());
        }
    }

    @Override
    public void Update(int diff)
    {
    }

    public void HandlePowerUp(Player p)
    {
        System.out.println("Handling PowerUp [Guid: " + GetGuid() + ", Owner: " + p.GetGuid() + "]");

        switch (_powerUpType)
        {
            case Constants.POWER_UP_TYPE_BOMB_UP:
                p.UpdateMaxBombs(1);
                break;
            case Constants.POWER_UP_TYPE_FIRE:
                p.UpdateExplosionRadius(1);
                break;
            case Constants.POWER_UP_TYPE_FULL_FIRE:
                p.SetExplosionRadius(Integer.MAX_VALUE);
                break;
            case Constants.POWER_UP_TYPE_KICK:
                System.out.println("Kick not implemented.");
                break;
            case Constants.POWER_UP_TYPE_PUNCH:
                System.out.println("Punch not implemented.");
                break;
            case Constants.POWER_UP_TYPE_SKATE:
                System.out.println("Skate not implemented.");
                break;
            case Constants.POWER_UP_TYPE_SKULL:
                System.out.println("Skull not implemented.");
                break;
            default:
                System.out.println("PowerUp type " + _powerUpType + " not valid.");
        }
    }
}
