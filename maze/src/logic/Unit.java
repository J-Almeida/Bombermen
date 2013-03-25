package logic;

import java.util.LinkedList;
import java.util.Queue;

// TODO: Auto-generated Javadoc
/**
 * The Class Unit.
 */
public abstract class Unit extends WorldObject{

    public final UnitType Type;

    /** The _alive. */
    protected boolean _alive;

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

    /** Method called each iteration
     * @param maze */
    public abstract void Update(Maze maze);

    public boolean IsHero() { return Type == UnitType.Hero; }
    public boolean IsDragon() { return Type == UnitType.Dragon; }
    public boolean IsSword() { return Type == UnitType.Sword; }
    public boolean IsEagle() { return Type == UnitType.Eagle; }

    public Hero ToHero() { return IsHero() ? (Hero)this : null; }
    public Dragon ToDragon() { return IsDragon() ? (Dragon)this : null; }
    public Sword ToSword() { return IsSword() ? (Sword)this : null; }
    public Eagle ToEagle() { return IsEagle() ? (Eagle)this : null; }
}
