package logic;

import java.awt.Point;

import logic.events.Event;

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
    }
}
