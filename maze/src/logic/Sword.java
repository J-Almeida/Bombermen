package logic;

/**
 * The Class Sword.
 */
public class Sword extends Unit
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new sword.
     */
    public Sword()
    {
        super(UnitType.Sword);
    }

    /* (non-Javadoc)
     * @see logic.WorldObject#GetSymbol()
     */
    @Override
    public char GetSymbol()
    {
        return 'E';
    }

    /* (non-Javadoc)
     * @see logic.Unit#HandleEvent(logic.Maze, logic.Event)
     */
    @Override
    public void HandleEvent(Maze maze, Event event)
    {
        if (event.IsMovementEvent())
        {
            MovementEvent ev = event.ToMovementEvent();
            if ((ev.Actor.IsHero() || ev.Actor.IsEagle()) && _position.equals(ev.Actor.GetPosition()))
            {
                this.Kill();
                if (ev.Actor.IsHero())
                    ev.Actor.ToHero().EquipSword(true);
                else if (ev.Actor.IsEagle())
                    ev.Actor.ToEagle().EquipSword(true);
            }
        }
    }
}
