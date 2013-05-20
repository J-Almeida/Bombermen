package logic.events;

import utils.Direction;

public class MovementEvent extends Event
{
    public final Direction Direction;

    public MovementEvent(Direction direction)
    {
        super(EventType.Movement);

        Direction = direction;
    }
}
