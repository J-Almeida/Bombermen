package logic.events;

import logic.Bomb;

public class ExplodeEvent extends Event
{
    public final Bomb Bomb;

    public ExplodeEvent(Bomb bomb)
    {
        super(EventType.Explode);

        Bomb = bomb;
    }
}
