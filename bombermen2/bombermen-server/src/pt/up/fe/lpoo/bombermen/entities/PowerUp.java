package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.BombermenServer;
import pt.up.fe.lpoo.bombermen.CollisionHandler;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.messages.SMSG_POWER_UP;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;

import com.badlogic.gdx.math.Vector2;

/**
 * The Class PowerUp.
 */
public class PowerUp extends Entity
{

    /** The collision handler. */
    private static CollisionHandler<PowerUp> cHandler = new CollisionHandler<PowerUp>()
    {
        @Override
        protected void CollidePlayer(PowerUp e1, Player p)
        {
            e1.HandlePowerUp(p);
            e1._server.ChangePlayerScore(p.GetGuid(), Constants.POWER_UP_SCORE);
        }
    };

    /** The power up type. */
    private int _powerUpType;

    /** The just created. */
    private boolean _justCreated;

    /** The timer. */
    private int _timer;

    /**
     * Instantiates a new power up.
     *
     * @param guid the guid
     * @param pos the pos
     * @param powerUpType the power up type
     * @param sv the sv
     */
    public PowerUp(int guid, Vector2 pos, int powerUpType, BombermenServer sv)
    {
        super(TYPE_POWER_UP, guid, pos, sv);

        _powerUpType = powerUpType;
        _justCreated = true;
        _timer = 0;
    }

    /**
     * Sets the just created.
     *
     * @param b the b
     */
    public void SetJustCreated(boolean b)
    {
        _justCreated = b;
    }

    /**
     * Just created.
     *
     * @return true, if successful
     */
    public boolean JustCreated()
    {
        return _justCreated;
    }

    /**
     * Kill.
     */
    public void Kill()
    {
        _server.RemoveEntityNextUpdate(GetGuid());
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSpawnMessage()
     */
    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_POWER_UP(GetGuid(), GetX(), GetY(), _powerUpType);
    }

    /** The Constant size. */
    private static final Vector2 size = new Vector2(Constants.POWER_UP_WIDTH, Constants.POWER_UP_HEIGHT);

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSize()
     */
    @Override
    public Vector2 GetSize()
    {
        return size;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#OnCollision(pt.up.fe.lpoo.bombermen.Entity)
     */
    @Override
    public void OnCollision(Entity e)
    {
        cHandler.OnCollision(this, e);
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#Update(int)
     */
    @Override
    public void Update(int diff)
    {
        _timer += diff;
        if (_timer > 600)
        {
            _justCreated = false;
            _timer = 0;
        }
    }

    /**
     * Handle power up.
     *
     * @param p the player
     */
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
                p.UpdateSpeed(18f / 1000); // half cell per second
                break;
            case Constants.POWER_UP_TYPE_SKULL:
                System.out.println("Skull not implemented.");
                break;
            default:
                System.out.println("PowerUp type " + _powerUpType + " not valid.");
        }

        _server.RemoveEntityNextUpdate(GetGuid());
        _server.SendTo(p.GetGuid(), new SMSG_POWER_UP(_powerUpType));
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#OnDestroy()
     */
    @Override
    public void OnDestroy()
    {
    }
}
