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

    @Override
    public String toString()
    {
        return _armed ? "A" : "H";
    }
    
    @Override
    public void Update()
    {
    	while (!_eventQueue.isEmpty())
    	{
    		if (_eventQueue.peek().Type == EventType.Colision)
    		{
				Colision<?> ev = (Colision<?>) _eventQueue.peek();
				if (ev.Other.Type == UnitType.Sword)
				{
					this.EquipSword();
				}
				else if (ev.Other.Type == UnitType.Dragon)
				{
					if (!((Dragon)ev.Other).IsSleeping() && !this.IsArmed())
						this.Kill();
					
				}						
			}
    		_eventQueue.poll();
    	}
    }
}
