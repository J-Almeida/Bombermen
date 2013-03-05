package logic;

public class Collision extends Event
{
    public Collision(Unit other)
    {
        super(EventType.Colision);
        Other = other;
    }

    public final Unit Other;
}
