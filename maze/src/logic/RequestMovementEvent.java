package logic;

public class RequestMovementEvent extends Event
{
    public RequestMovementEvent(Direction direction)
    {
        super(EventType.RequestMovement);
        Direction = direction;
    }

    public final Direction Direction;
}
