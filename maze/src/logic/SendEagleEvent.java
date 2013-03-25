package logic;

public class SendEagleEvent extends Event
{
    public SendEagleEvent(Hero hero)
    {
        super(EventType.SendEagle);
        Hero = hero;
    }

    public final Hero Hero;
}
