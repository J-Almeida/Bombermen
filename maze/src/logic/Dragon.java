package logic;

import utils.RandomEngine;

/**
 * The Class Dragon.
 */
public class Dragon extends Unit
{
    /** Is dragon sleeping? */
    private boolean _asleep = false;
    
    /** Sleep countdown */
    private int _sleepTimer = 0;

    private DragonBehaviour _db;

    /**
     * Instantiates a new dragon.
     */
    public Dragon(DragonBehaviour db)
    {
        super(UnitType.Dragon);
        _db = db;
    }

    @Override
    public String toString()
    {
        return _asleep ? "d" : "D";
    }

    /**
     * Sets the Dragon to sleep.
     */
    public void SetToSleep(int time)
    {
        _asleep = true;
        _sleepTimer = time;
    }

    /**
     * Wakes up the Dragon.
     */
    public void WakeUp()
    {
        _asleep = false;
        _sleepTimer = 0;
    }

    /**
     * Checks if Dragon is sleeping.
     *
     * @return true, if Dragon is sleeping
     */
    public boolean IsSleeping()
    {
        return _asleep;
    }

    /**
     * Can move?
     *
     * @return true, if Dragon can move
     */
    public boolean CanMove()
    {
        if (_db == DragonBehaviour.Idle)
            return false;

        if (_asleep)
            return false;

        return true;
    }

    public boolean CanSleep()
    {
        if (_db != DragonBehaviour.Sleepy)
            return false;

        return true;
    }

    @Override
    public void Update()
    {
        if (CanSleep())
        {
            if (_alive && _asleep)
            {
                _sleepTimer--;

                if (_sleepTimer == 0)
                    _asleep = false;
            }
            else
            {
                // 25% chance of sleeping between 10 and 20 units of time
                if (RandomEngine.GetBool(25))
                    SetToSleep(RandomEngine.GetInt(10, 20));
            }
        }
        
        while (!_eventQueue.isEmpty())
    	{
    		if (_eventQueue.peek().Type == EventType.Colision)
    		{
				Colision<?> ev = (Colision<?>) _eventQueue.peek();
				if (ev.Other.Type == UnitType.Hero)
				{
					if (((Hero) ev.Other).IsArmed())
						this.Kill();
				}						
    		}
    		
    		_eventQueue.poll();
    	}
    }
}
