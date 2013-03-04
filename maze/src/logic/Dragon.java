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

    /**
     * Instantiates a new dragon.
     */
    public Dragon()
    {
        super(UnitType.Dragon);
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
        return !_asleep;
    }

    @Override
    public void Update()
    {
        if (_alive && _asleep)
        {
            _sleepTimer--;

            if (_sleepTimer == 0)
                _asleep = false;
        }
        else
        {
            if (RandomEngine.GetBool(25))
                SetToSleep(RandomEngine.GetInt(10, 20));
        }
    }
}
