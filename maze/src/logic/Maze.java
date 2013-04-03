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

/**
 * The Class Maze.
 */
public class Maze implements Serializable
{
    private static final long serialVersionUID = 1L; // default

    static final Path PATH = new Path();
    static final Wall WALL = new Wall();
    /** The Grid. */
    private final Grid<InanimatedObject> _board;
    private final Set<Unit> _livingObjects;
    private final PriorityQueue<UnitEvent> _eventQueue;

    /**
     * Instantiates an empty maze.
     *
     * @param width the width of the maze.
     * @param height the height of the maze.
     */
    public Maze(int width, int height)
    {
        _board = new Grid<InanimatedObject>(width, height, PATH);
        _livingObjects = new TreeSet<Unit>(new UnitComparator());
        _eventQueue = new PriorityQueue<UnitEvent>();
    }

    public int GetWidth() { return _board.Width; }

    public int GetHeight() { return _board.Height; }

    public int GetNumberOfPathCells()
    {
    	int res = 0;
    	for (int x = 0; x < _board.Width; x++)
    		for (int y = 0; y < _board.Height; y++)
    			if (_board.GetCellT(x, y).equals(PATH))
    				res++;

    	return res;
    }

    public int GetNumberOfWallCells()
    {
    	return _board.Width * _board.Height - GetNumberOfPathCells();
    }

    public Grid<InanimatedObject> GetGrid() { return _board; }
    public Set<Unit> GetLivingObjects() { return _livingObjects; }

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

    public void SendEagleToSword()
    {
        Hero h = FindHero();
        Sword s = FindSword();
        Eagle e = FindEagle();

        PushEvent(e, new SendEagleEvent(h, s));
    }

    public void PushEvent(Unit u, Event ev)
    {
        _eventQueue.add(new UnitEvent(u, ev));
    }

    public void MoveHero(Direction direction)
    {
        PushEvent(FindHero(), new RequestMovementEvent(direction));
    }

    public void Update()
    {
        for (Unit wo : _livingObjects)
            wo.Update(this);

        while (!_eventQueue.isEmpty())
            _eventQueue.poll().HandleEvent(this);

        for (Iterator<Unit> uit = _livingObjects.iterator(); uit.hasNext();)
            if (!uit.next().IsAlive())
                uit.remove();
    }

    public void AddWorldObject(WorldObject obj)
    {
        if (obj.IsInanimatedObject())
            _board.SetCell(obj.GetPosition(), obj.ToInanimatedObject());
        else if (obj.IsUnit())
            _livingObjects.add(obj.ToUnit());
    }

    public boolean IsFinished()
    {
        Hero h = FindHero();
        return h == null || h.GetPosition().equals(FindExitPortal().GetPosition());
    }

    public Hero FindHero()
    {
        for (Unit wo : _livingObjects)
            if (wo.IsHero())
                return wo.ToHero();
        return null;
    }

    public Eagle FindEagle()
    {
        for (Unit wo : _livingObjects)
            if (wo.IsEagle())
                return wo.ToEagle();
        return null;
    }

    public Sword FindSword()
    {
        for (Unit wo : _livingObjects)
            if (wo.IsSword())
                return wo.ToSword();
        return null;
    }

    public ArrayList<Dragon> FindDragons()
    {
        ArrayList<Dragon> r = new ArrayList<Dragon>();
        for (Unit wo : _livingObjects)
            if (wo.IsDragon())
                r.add(wo.ToDragon());
        return r;
    }

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

    public boolean IsEmptyPosition(Position p)
    {
        if (!IsPathPosition(p))
            return false;

        for (Unit u : _livingObjects)
            if (u.GetPosition().equals(p))
                return false;

        return true;
    }

    public boolean IsPathPosition(Position p)
    {
        Cell<InanimatedObject> c = _board.GetCell(p);

        return c != null ? c.GetValue().IsPath() : null;
    }

    public void ForwardEventToUnits(Event ev)
    {
        for (Unit u : _livingObjects)
            PushEvent(u, ev);
    }

    public boolean IsExitPosition(Position newPos)
    {
        ExitPortal ex = FindExitPortal();
        return ex != null && newPos != null && newPos.equals(ex.GetPosition());
    }
}
