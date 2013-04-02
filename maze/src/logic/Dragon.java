package logic;

import logic.Eagle.EagleState;
import model.Position;
import utils.Key;
import utils.RandomEngine;

/**
 * The Class Dragon.
 */
public class Dragon extends Unit
{
	/** Valid values for dragon behaviour */
    public enum Behaviour
    {
        /** No movement at all */
        Idle,
        /** Random movement */
        RandomMovement,
        /** Random movement with ability to sleep */
        Sleepy
    }

    /** Is dragon sleeping? */
    private boolean _asleep = false;

    /** Sleep countdown */
    private int _sleepTimer = 0;

    private final Behaviour _db;

	private boolean _onSword = false;

    /**
     * Instantiates a new dragon.
     */
    public Dragon(Behaviour db)
    {
        super(UnitType.Dragon);
        _db = db;
    }

    @Override
    public char GetSymbol()
    {
        return _asleep ?
        			(_onSword ? 'f' : 'd') :
        			(_onSword ? 'F' : 'D') ;
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
        if (_db == Behaviour.Idle)
            return false;

        if (_asleep)
            return false;

        return true;
    }

    public boolean CanSleep()
    {
        if (_db != Behaviour.Sleepy)
            return false;

        return true;
    }

    @Override
    public void Update(Maze maze)
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
                // 15% chance of sleeping between 10 and 20 units of time
                if (RandomEngine.GetBool(15))
                    SetToSleep(RandomEngine.GetInt(10, 20));
            }
        }

        if (CanMove())
        {
            Key dirKey = Key.toEnum(RandomEngine.GetInt(0, 4));

            if (dirKey != null)
            {
	            Position newPos = _position.clone();

	            Direction dir = Direction.FromKey(dirKey);
	            Direction.ApplyMovement(newPos, dir);

	            if (maze.IsPathPosition(newPos))
	            {
	                _position = newPos;
	                maze.ForwardEventToUnits(new MovementEvent(this, dir));

	                Sword s = maze.FindSword();
	                _onSword  = s != null && _position.equals(s.GetPosition());
	            }
            }
        }
    }

    @Override
	public void HandleEvent(Maze maze, Event event)
    {
        if (event.IsRequestMovementEvent())
        {
            RequestMovementEvent ev = event.ToRequestMovementEvent();

            Position newPos = _position.clone();
            Direction.ApplyMovement(newPos, ev.Direction);

            if (maze.IsPathPosition(newPos))
            {
                _position = newPos;
                maze.ForwardEventToUnits(new MovementEvent(this, ev.Direction));
            }
        }
        else if (event.IsMovementEvent() && !this.IsSleeping())
    	{
    		MovementEvent ev = event.ToMovementEvent();
    		if (ev.Actor.IsHero() || ev.Actor.IsEagle())
    		{
	    		if (Position.IsAdjacent(_position, ev.Actor.GetPosition()) || _position.equals(ev.Actor.GetPosition()))
	    		{
	    			if (ev.Actor.IsEagle() && ev.Actor.ToEagle().GetState() != EagleState.OnFlight && ev.Actor.ToEagle().GetState() != EagleState.OnFlightBack)
	    			{
	    				ev.Actor.Kill();
	    			}
	    			else if (ev.Actor.IsHero())
	    			{
	    				if (ev.Actor.ToHero().IsArmed())
	    					this.Kill();
	    				else
	    					ev.Actor.Kill();
	    			}
	    		}
    		}
    	}
	}
}
