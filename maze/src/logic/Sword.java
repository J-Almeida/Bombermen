package logic;

/**
 * The Class Sword.
 */
public class Sword extends Unit
{
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
    public void Update(Maze maze)
    {
        while (!_eventQueue.isEmpty())
        {
            _eventQueue.poll();
        }
    }

    @Override
    public void OnCollision(Unit other)
    {
        if (other.IsHero() || other.IsEagle())
            this.Kill();
    }
}
