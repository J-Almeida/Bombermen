package logic;

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

    private Behaviour _db;

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
        return _asleep ? 'd' : 'D';
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
            Key dirKey = Key.toEnum(RandomEngine.GetInt(1, 4));
            Position newPos = _position.clone();

            Direction dir = Direction.FromKey(dirKey);
            Direction.ApplyMovement(newPos, dir);

            if (maze.IsPathPosition(newPos))
            {
                _position = newPos;
                maze.ForwardEventToUnits(new MovementEvent(this, dir));
            }
        }

        Hero h = maze.FindHero();
        if (Position.IsAdjacent(_position, h.GetPosition()))
        {
            this.OnCollision(h);
            h.OnCollision(this);
            h.PushEvent(new CollisionEvent(this));
            this.PushEvent(new CollisionEvent(h));
        }

        while (!_eventQueue.isEmpty())
        {
            Event event = _eventQueue.peek();
            if (event.IsCollisionEvent())
            {
                CollisionEvent ev = event.ToCollisionEvent();
                if (ev.Other.IsHero())
                {
                    if (!this.IsSleeping() && !ev.Other.ToHero().IsArmed())
                        ev.Other.Kill();
                }
                else if (ev.Other.IsEagle() && !this.IsSleeping())
                {
                    ev.Other.Kill();
                }
            }

            _eventQueue.poll();
        }
    }
}
