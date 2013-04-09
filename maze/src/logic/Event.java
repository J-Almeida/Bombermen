package logic;

/**
 * The Class Event.
 */
public abstract class Event
{
    /** The event type. */
    public final EventType Type;

    /**
     * Instantiates a new event.
     *
     * @param type the type
     */
    public Event(EventType type)
    {
        Type = type;
    }

    /**
     * Checks if is request movement event.
     *
     * @return true, if successful
     */
    public boolean IsRequestMovementEvent() { return Type == EventType.RequestMovement; }

    /**
     * Checks if is movement event.
     *
     * @return true, if successful
     */
    public boolean IsMovementEvent() { return Type == EventType.Movement; }

    /**
     * Checks if is send eagle event.
     *
     * @return true, if successful
     */
    public boolean IsSendEagleEvent() { return Type == EventType.SendEagle; }

    /**
     * Checks if is same type.
     *
     * @param other the other
     * @return true, if successful
     */
    public boolean IsSameType(Event other) { return Type == other.Type; }

    /**
     * Checks if is event type.
     *
     * @param evt the event type
     * @return true, if successful
     */
    public boolean Is(EventType evt) { return Type == evt; }

    /**
     * Cast to request movement event.
     *
     * @return the request movement event
     */
    public RequestMovementEvent ToRequestMovementEvent() { return IsRequestMovementEvent() ? (RequestMovementEvent)this : null; }

    /**
     * Cast to movement event.
     *
     * @return the movement event
     */
    public MovementEvent ToMovementEvent() { return IsMovementEvent() ? (MovementEvent)this : null; }

    /**
     * Cast to send eagle event.
     *
     * @return the send eagle event
     */
    public SendEagleEvent ToSendEagleEvent() { return IsSendEagleEvent() ? (SendEagleEvent)this : null; }
}
