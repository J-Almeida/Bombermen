package logic;

public abstract class Event
{
    public final EventType Type;

    public Event(EventType type)
    {
        Type = type;
    }

    public boolean IsCollisionEvent() { return Type == EventType.Collision; }
    public boolean IsRequestMovementEvent() { return Type == EventType.RequestMovement; }
    public boolean IsMovementEvent() { return Type == EventType.Movement; }
    public boolean IsSendEagleEvent() { return Type == EventType.SendEagle; }
    public boolean IsSameType(Event other) { return Type == other.Type; }
    public boolean IsOfType(EventType evt) { return Type == evt; }
    
    public CollisionEvent ToCollisionEvent() { return IsCollisionEvent() ? (CollisionEvent)this : null; }
    public RequestMovementEvent ToRequestMovementEvent() { return IsRequestMovementEvent() ? (RequestMovementEvent)this : null; }
    public MovementEvent ToMovementEvent() { return IsMovementEvent() ? (MovementEvent)this : null; }
    public SendEagleEvent ToSendEagleEvent() { return IsSendEagleEvent() ? (SendEagleEvent)this : null; }   
}
