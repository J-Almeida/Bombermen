package logic;

import java.util.LinkedList;
import java.util.Queue;

import model.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class Unit.
 */
public abstract class Unit extends WorldObject{

	public final UnitType Type;
	
    /** The Constant DEFAULT_POSITION. */
    public final static Position DEFAULT_POSITION = new Position();

    /** The _alive. */
    protected boolean _alive;

    /** The _position. */
    protected Position _position = DEFAULT_POSITION;

    /** The _eventQueue */
    protected Queue<Event> _eventQueue = new LinkedList<Event>();

    /**
     * Instantiates a new unit.
     *
     * @param type the type
     */
    public Unit(UnitType type)
    {
    	super(WorldObjectType.Unit);
        Type = type;
        _alive = true;
    }

    /**
     * Gets the position.
     *
     * @return the pair
     */
    public Position GetPosition()
    {
        return _position;
    }

    /**
     * Sets the position.
     *
     * @param pos the pos
     */
    public void SetPosition(Position pos)
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

    /**
     * Adds event to Unit's queue
     *
     * @param val the event
     */
    public void PushEvent(Event val)
    {
        _eventQueue.add(val);
    }

    @Override
    public abstract String toString();

    /** Method called each iteration */
    public void Update() {};
}
