package logic;

public class CollisionEvent extends Event
{
    public CollisionEvent(Unit other)
    {
        super(EventType.Collision);
        Other = other;
    }

    public final Unit Other;
}
