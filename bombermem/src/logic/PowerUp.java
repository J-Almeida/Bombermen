package logic;

import java.awt.Point;

import logic.events.Event;
import logic.events.PickUpEvent;

public abstract class PowerUp extends WorldObject
{
    public enum PowerUpType
    {
        BombUp(0), // increase amount of bombs by 1
        Skate(1), // increase speed by 1
        Kick(2), // send bomb sliding across the stage until it collides with a wall/player/bomb
        Punch(3), // knock them away (out of screen or screen wrap to the other side)
        Fire(4), // increase bomb radius
        Skull(5), // bad effects (bomberman.wikia.com/wiki/Skull)
        FullFire(6); // biggest possible explosion radius

        public final int Index;
        PowerUpType(int index) { Index = index; }
    }

    public final PowerUpType Type;
    protected int _timer = 0;
    protected boolean _handled = false;
    private boolean _alive = true;

    public PowerUp(int guid, Point pos, PowerUpType type)
    {
        super(WorldObjectType.PowerUp, guid, pos);

        Type = type;
    }

    @Override
    public void Update(GameState gs, int diff)
    {
        _timer += diff;

        if (_timer >= 10000)
            _timer = 0;
    }

    @Override
    public boolean IsAlive()
    {
        return _alive;
    }

    @Override
    public void HandleEvent(GameState gs, WorldObject src, Event event)
    {
        if (event.IsExplodeEvent())
        {
            _alive = false;
        }
        else if (event.IsPickUpEvent())
        {
            _alive = false;

            PickUpEvent ev = event.ToPickUpEvent();
            if (!_handled)
            {
                Handle(ev);
                _handled = true;
            }
        }
    }

    public void Handle(PickUpEvent p)
    {
        System.out.println(this.Guid + " - PowerUp Handle (for " + p.Player.Guid + ")");

        switch (Type)
        {
        case BombUp:
            p.Player.MaxBombs++;
            break;
        case Fire:
            p.Player.BombRadius++;
            break;
        case FullFire:
            p.Player.BombRadius = 50; // TODO: change to real max
            break;
        case Kick:
            System.out.println("Kick powerup not implemented.");
            break;
        case Punch:
            System.out.println("Punch powerup not implemented.");
            break;
        case Skate:
            System.out.println("Skate powerup not implemented.");
            break;
        case Skull:
            System.out.println("Skull powerup not implemented.");
            break;
        default:
            break;

        }
    }
}
