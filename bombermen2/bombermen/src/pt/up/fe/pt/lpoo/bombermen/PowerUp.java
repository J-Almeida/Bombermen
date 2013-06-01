package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class PowerUp extends Entity
{
    public static final int TYPE_BOMB_UP = 0; // increase amount of bombs by 1
    public static final int TYPE_SKATE = 1; // increase speed by 1
    public static final int TYPE_KICK = 2; // send bomb sliding across the stage until it collides with a wall/player/bomb
    public static final int TYPE_PUNCH = 3; // knock them away (out of screen or screen wrap to the other side)
    public static final int TYPE_FIRE = 4; // increase bomb radius
    public static final int TYPE_SKULL = 5; // debuffs (bomberman.wikia.com/wiki/Skull)
    public static final int TYPE_FULL_FIRE = 6; // biggest possible explosion radius
    
    public static final int NUMBER_OF_TYPES = 7;

    public PowerUp(Vector2 pos, int guid, int type)
    {
        super(Entity.TYPE_POWER_UP, pos, guid);

        type = _type;
    }

    private int _type;

    public void HandlePowerUp(Player p)
    {
        Gdx.app.debug("PowerUp", "Handling PowerUp [Guid: " + GetGuid() + ", Owner: " + p.GetGuid() + "]");

        switch (_type)
        {
            case TYPE_BOMB_UP:
                p.UpdateMaxBombs(1);
                break;
            case TYPE_FIRE:
                p.UpdateBombRadius(1);
                break;
            case TYPE_FULL_FIRE:
                p.SetBombRadius(Integer.MAX_VALUE);
                break;
            case TYPE_KICK:
                Gdx.app.debug("PowerUp", "Kick not implemented.");
                break;
            case TYPE_PUNCH:
                Gdx.app.debug("PowerUp", "Punch not implemented.");
                break;
            case TYPE_SKATE:
                Gdx.app.debug("PowerUp", "Skate not implemented.");
                break;
            case TYPE_SKULL:
                Gdx.app.debug("PowerUp", "Skull not implemented.");
                break;
            default:
                Gdx.app.error("PowerUp", "PowerUp type " + _type + " not valid.");
        }
    }

    @Override
    public void OnCollision(Entity other)
    {
        if (other.IsPlayer())
        {
            Player p = other.ToPlayer();
            HandlePowerUp(p);
            Gdx.app.log("PowerUp", "PowerUp picked-up, removed itself.");
            Game.Instance().GetWorld().RemoveEntity(this);
        }
    }

    @Override
    public void OnDestroy()
    {
    }

    @Override
    public void OnExplode(Explosion e)
    {
        Gdx.app.log("PowerUp", "PowerUp exploded, removed itself.");
        Game.Instance().GetWorld().RemoveEntity(this);
    }
}
