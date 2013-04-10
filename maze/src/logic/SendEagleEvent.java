package logic;

/**
 * Event used when the Hero wants the Eagle to get sword
 */
public class SendEagleEvent extends Event
{
    /**
     * Instantiates a new send eagle event.
     *
     * @param hero the hero
     * @param sword the sword
     */
    public SendEagleEvent(Hero hero, Sword sword)
    {
        super(EventType.SendEagle);
        Hero = hero;
        Sword = sword;
    }

    /** The Hero. */
    public final Hero Hero;

    /** The Sword. */
    public final Sword Sword;
}
