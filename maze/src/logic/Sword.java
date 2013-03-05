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
    public String toString() {
        return "E";
    }

    @Override
    public void Update()
    {
        while (!_eventQueue.isEmpty())
        {
            if (_eventQueue.peek().Type == EventType.Colision)
            {
                Collision ev = (Collision)_eventQueue.peek();
                if (ev.Other.Type == UnitType.Hero || ev.Other.Type == UnitType.Eagle)
                {
                    this.Kill();
                }
            }

            _eventQueue.poll();
        }
    }
}
