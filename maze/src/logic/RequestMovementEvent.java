package logic;

/**
 * The Class RequestMovementEvent.
 */
public class RequestMovementEvent extends Event
{
    /**
     * Instantiates a new request movement event.
     *
     * @param direction the direction
     */
    public RequestMovementEvent(Direction direction)
    {
        super(EventType.RequestMovement);
        Direction = direction;
    }

    /** The Direction. */
    public final Direction Direction;
}
