package logic;

public class SendEagleEvent extends Event
{
    public SendEagleEvent(Hero hero, Sword sword)
    {
        super(EventType.SendEagle);
        Hero = hero;
        Sword = sword;
    }

    public final Hero Hero;
    public final Sword Sword;
}
