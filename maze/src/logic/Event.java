package logic;

public abstract class Event
{
    public final EventType Type;

    public Event(EventType type)
    {
        Type = type;
    }
}
