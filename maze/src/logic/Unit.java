package logic;

import java.util.LinkedList;
import java.util.Queue;

import utils.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Class Unit.
 */
public abstract class Unit {

    /** The Constant DEFAULT_POSITION. */
    public final static Pair<Integer> DEFAULT_POSITION = Pair.IntN(-1, -1);

    /** The Type. */
    public final UnitType Type;

    /** The _alive. */
    protected boolean _alive;

    /** The _position. */
    protected Pair<Integer> _position = DEFAULT_POSITION;
    
    /** The _eventQueue */
    protected Queue<Event> _eventQueue = new LinkedList<Event>();
    
    public void pushEvent(Event val)
    {
    	_eventQueue.add(val);
    }
    
    /**
     * Instantiates a new unit.
     *
     * @param type the type
     */
    public Unit(UnitType type)
    {
        Type = type;
        _alive = true;
    }

    /**
     * Gets the position.
     *
     * @return the pair
     */
    public Pair<Integer> GetPosition()
    {
        return _position;
    }

    /**
     * Sets the position.
     *
     * @param pos the pos
     */
    public void SetPosition(Pair<Integer> pos)
    {
        _position = pos;
    }

    /**
     * Checks if is alive.
     *
     * @return true, if successful
     */
    public boolean IsAlive()
    {
        return _alive;
    }

    /**
     * Kill.
     */
    public void Kill()
    {
        _alive = false;
    }

    @Override
    public abstract String toString();

    /** Method called each iteration */
    public void Update() {};
}
