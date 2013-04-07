package logic;

/**
 * The Class Sword.
 */
public class Sword extends Unit
{
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new sword.
     */
    public Sword()
    {
        super(UnitType.Sword);
    }

    @Override
    public char GetSymbol()
    {
        return 'E';
    }

    @Override
    public void Update(Maze maze) { }

    @Override
    public void OnCollision(Unit other)
    {
        if (other.IsHero() || other.IsEagle())
            this.Kill();
    }


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
                    ev.Actor.ToEagle().EquipSword();
            }
        }

    }
}
