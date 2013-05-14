package logic.events;

import utils.Direction;

public class RequestMovementEvent extends Event
{
    public final Direction Direction;

    public RequestMovementEvent(Direction dir)
    {
        super(EventType.RequestMovement);

        Direction = dir;
    }
}
