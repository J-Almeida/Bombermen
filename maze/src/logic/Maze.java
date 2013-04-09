package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import model.Cell;
import model.Grid;
import model.Position;

// TODO: Auto-generated Javadoc
/**
 * Main class for the game - Maze
 */
public class Maze implements Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L; // default

    /** The Constant PATH. */
    static final Path PATH = new Path();

    /** The Constant WALL. */
    static final Wall WALL = new Wall();

    /** The Grid. */
    private final Grid<InanimatedObject> _board;

    /** The set of living objects. */
    private final Set<Unit> _livingObjects;

    /** The event queue */
    private transient PriorityQueue<UnitEventEntry> _eventQueue;

    /** The diff time used in Update */
    private int _diff = 0;

    /**
     * Sets the event queue.
     *
     * @param eq the event queue
     */
    public void SetEventQueue(PriorityQueue<UnitEventEntry> eq) { _eventQueue = eq; }

    /**
     * Instantiates an empty maze.
     *
     * @param width the width of the maze.
     * @param height the height of the maze.
     */
    public Maze(int width, int height)
    {
        _eventQueue = new PriorityQueue<UnitEventEntry>();
        _board = new Grid<InanimatedObject>(width, height, PATH);
        _livingObjects = new TreeSet<Unit>(new UnitComparator());
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public int GetWidth() { return _board.Width; }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public int GetHeight() { return _board.Height; }

    /**
     * Gets the number of path cells.
     *
     * @return the count
     */
    public int GetNumberOfPathCells()
    {
        int res = 0;
        for (int x = 0; x < _board.Width; x++)
            for (int y = 0; y < _board.Height; y++)
                if (_board.GetCellT(x, y).IsPath())
                    res++;

        return res;
    }

    /**
     * Gets the number of wall cells.
     *
     * @return the count
     */
    public int GetNumberOfWallCells()
    {
        return _board.Width * _board.Height - GetNumberOfPathCells();
    }

    /**
     * Gets the grid.
     *
     * @return the grid
     */
    public Grid<InanimatedObject> GetGrid() { return _board; }

    /**
     * Gets the set of living objects.
     *
     * @return the set of living objects.
     */
    public Set<Unit> GetLivingObjects() { return _livingObjects; }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        char[] result = _board.toString().toCharArray();

        for (Unit u : _livingObjects)
        {
            Position p = u.GetPosition();
            result[p.Y * (GetWidth() * 2) + p.X * 2] = u.GetSymbol();
        }

        return String.copyValueOf(result);
    }

    /**
     * Send eagle to sword.
     */
    public void SendEagleToSword()
    {
        Hero h = FindHero();
        Sword s = FindSword();
        Eagle e = FindEagle();

        PushEvent(e, new SendEagleEvent(h, s));
    }

    /**
     * Push event to unit
     *
     * @param u the unit to receive the event
     * @param ev the event
     */
    public void PushEvent(Unit u, Event ev)
    {
        _eventQueue.add(new UnitEventEntry(u, ev));
    }

    /**
     * Move hero.
     *
     * @param direction the direction
     */
    public void MoveHero(Direction direction)
    {
        PushEvent(FindHero(), new RequestMovementEvent(direction));
    }

    /**
     * Update with default timer (1 second)
     */
    public void Update()
    {
        Update(1000);
    }

    /**
     * Update.
     *
     * @param diff the difference between this call of update and previous (in milliseconds)
     */
    public void Update(int diff)
    {
        _diff += diff;

        // call unit updates
        if (_diff >= 1000)
        {
            for (Unit wo : _livingObjects)
                wo.Update(this);
            _diff = 0;
        }

        // handle events
        while (!_eventQueue.isEmpty())
            _eventQueue.poll().HandleEvent(this);

        // remove death units
        for (Iterator<Unit> uit = _livingObjects.iterator(); uit.hasNext();)
            if (!uit.next().IsAlive())
                uit.remove();
    }

    /**
     * Adds the world object to the maze (can be InanimatedObject or Unit)
     *
     * @param obj the object
     */
    public void AddWorldObject(WorldObject obj)
    {
        if (obj.IsInanimatedObject()) // add to board
            _board.SetCell(obj.GetPosition(), obj.ToInanimatedObject());
        else if (obj.IsUnit()) // add to living objects
            _livingObjects.add(obj.ToUnit());
    }

    /**
     * Checks if is finished.
     *
     * @return true, if successful
     */
    public boolean IsFinished()
    {
        Hero h = FindHero();
        return h == null || h.GetPosition().equals(FindExitPortal().GetPosition());
    }

    /**
     * Find hero.
     *
     * @return the hero
     */
    public Hero FindHero()
    {
        for (Unit wo : _livingObjects)
            if (wo.IsHero())
                return wo.ToHero();
        return null;
    }

    /**
     * Find eagle.
     *
     * @return the eagle
     */
    public Eagle FindEagle()
    {
        for (Unit wo : _livingObjects)
            if (wo.IsEagle())
                return wo.ToEagle();
        return null;
    }

    /**
     * Find sword.
     *
     * @return the sword
     */
    public Sword FindSword()
    {
        for (Unit wo : _livingObjects)
            if (wo.IsSword())
                return wo.ToSword();
        return null;
    }

    /**
     * Find dragons.
     *
     * @return the array list
     */
    public ArrayList<Dragon> FindDragons()
    {
        ArrayList<Dragon> r = new ArrayList<Dragon>();
        for (Unit wo : _livingObjects)
            if (wo.IsDragon())
                r.add(wo.ToDragon());
        return r;
    }

    /**
     * Find exit portal.
     *
     * @return the exit portal
     */
    public ExitPortal FindExitPortal()
    {
        for (int x = 0; x < GetWidth(); ++x)
        {
            for (int y = 0; y < GetHeight(); ++y)
            {
                if (_board.GetCellT(x, y).IsExitPortal())
                    return _board.GetCellT(x, y).ToExitPortal();
            }
        }

        return null;
    }

    /**
     * Checks if is empty position.
     *
     * @param p the position
     * @return true, if successful
     */
    public boolean IsEmptyPosition(Position p)
    {
        if (!IsPathPosition(p))
            return false;

        for (Unit u : _livingObjects)
            if (u.GetPosition().equals(p))
                return false;

        return true;
    }

    /**
     * Checks if is path position.
     *
     * @param p the position
     * @return true, if successful
     */
    public boolean IsPathPosition(Position p)
    {
        Cell<InanimatedObject> c = _board.GetCell(p);
        if (c != null)
        {
            InanimatedObject io = c.GetValue();
            if (io != null)
                return io.IsPath();
        }

        return false;
    }

    /**
     * Checks if is exit position.
     *
     * @param newPos the position
     * @return true, if successful
     */
    public boolean IsExitPosition(Position newPos)
    {
        ExitPortal ex = FindExitPortal();
        return ex != null && newPos != null && newPos.equals(ex.GetPosition());
    }

    /**
     * Gets the position.
     *
     * @param p the position
     * @return the world object
     */
    public WorldObject GetPosition(Position p)
    {
        for (Unit u : GetLivingObjects())
            if (u.GetPosition().equals(p))
                return u;

        return _board.GetCellT(p);
    }

    /**
     * Forward event to all units.
     *
     * @param ev the event
     */
    public void ForwardEventToUnits(Event ev)
    {
        for (Unit u : _livingObjects)
            PushEvent(u, ev);
    }
}
