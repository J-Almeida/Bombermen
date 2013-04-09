package logic;

/**
 * The Class MovementEvent.
 */
public class MovementEvent extends Event
{
    /**
     * Instantiates a new movement event.
     *
     * @param actor the actor
     * @param direction the direction
     */
    public MovementEvent(Unit actor, Direction direction)
    {
        super(EventType.Movement);
        Actor = actor;
        Direction = direction;
    }

    /** The Actor. */
    public final Unit Actor;

    /** The Direction. */
    public final Direction Direction;
}
