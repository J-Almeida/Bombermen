package logic.events;

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

    public boolean IsRequestMovementEvent() { return Type == EventType.RequestMovement; }
    public boolean IsMovementEvent()        { return Type == EventType.Movement; }
    public boolean IsDeathEvent()           { return Type == EventType.Death; }
    public boolean IsSpawnEvent()           { return Type == EventType.Spawn; }
    public boolean IsExplodeEvent()         { return Type == EventType.Explode; }
    public boolean IsPickUpEvent()          { return Type == EventType.PickUp; }

    public RequestMovementEvent ToRequestMovementEvent() { return IsRequestMovementEvent() ? (RequestMovementEvent)this : null; }
    public MovementEvent        ToMovementEvent()        { return IsMovementEvent()        ? (MovementEvent)this : null; }
    public DeathEvent           ToDeathEvent()           { return IsDeathEvent()           ? (DeathEvent)this : null; }
    public SpawnEvent           ToSpawnEvent()           { return IsSpawnEvent()           ? (SpawnEvent)this : null; }
    public ExplodeEvent         ToExplodeEvent()         { return IsExplodeEvent()         ? (ExplodeEvent)this : null; }
    public PickUpEvent          ToPickUpEvent()          { return IsPickUpEvent()          ? (PickUpEvent)this : null; }


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
}
