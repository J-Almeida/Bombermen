package logic;

public class MovementEvent extends Event
{
    public MovementEvent(Unit actor, Direction direction)
    {
        super(EventType.Movement);
        Actor = actor;
        Direction = direction;
    }

    public final Unit Actor;
    public final Direction Direction;
}
