package logic;

/**
 * The Class Hero.
 */
public class Hero extends Unit
{
    /** Has sword? */
    private boolean _armed;

    /**
     * Instantiates a new hero.
     */
    public Hero()
    {
        super(UnitType.Hero);
    }

    /**
     * Checks if hero is armed.
     *
     * @return true, if hero has sword
     */
    public boolean IsArmed()
    {
        return _armed;
    }

    /**
     * Equip sword.
     */
    public void EquipSword()
    {
        _armed = true;
    }

    /**
     * Unequip sword.
     */
    public void UnequipSword()
    {
        _armed = false;
    }

    @Override
    public char GetSymbol()
    {
        return _armed ? 'A' : 'H';
    }

    @Override
    public void Update()
    {
        while (!_eventQueue.isEmpty())
        {
            if (_eventQueue.peek().Type == EventType.Collision)
            {
                Collision ev = (Collision)_eventQueue.peek();
                if (ev.Other.Type == UnitType.Sword || (ev.Other.Type == UnitType.Eagle))
                {
                    this.EquipSword();
                }
                else if (ev.Other.Type == UnitType.Dragon)
                {
                    if (this.IsArmed())
                        ev.Other.Kill();
                }
            }
            _eventQueue.poll();
        }
    }
}
