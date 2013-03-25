package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Grid;
import model.Position;

/**
 * The Class Maze.
 */
public class Maze
{
    static final Path PATH = new Path();
    static final Wall WALL = new Wall();
    /** The Grid. */
    private Grid<InanimatedObject> _board;
    private Map<Integer, Unit> _livingObjects;

    /** True if Game Over */
    private boolean _finished = false;

    /**
     * Instantiates an empty maze.
     *
     * @param width the width of the maze.
     * @param height the height of the maze.
     */
    public Maze(int width, int height)
    {
        _board = new Grid<InanimatedObject>(width, height, PATH);
        _livingObjects = new HashMap<Integer, Unit>();
    }

    public int GetWidth() { return _board.Width; }

    public int GetHeight() { return _board.Height; }

    public Grid<InanimatedObject> GetGrid() { return _board; }
    public Map<Integer, Unit> GetLivingObjects() { return _livingObjects; }

    @Override
    public String toString()
    {
        char[] result = _board.toString().toCharArray();

        for (Unit u : _livingObjects.values())
        {
            Position p = u.GetPosition();
            result[p.Y * (GetWidth() * 2) + p.X * 2] = u.GetSymbol();
        }

        return String.copyValueOf(result);
    }

    public void SendEagleToSword()
    {
        Hero h = FindHero();
        Eagle e = FindEagle();

        e.PushEvent(new SendEagleEvent(h));
    }

    public void MoveHero(Direction direction)
    {
        Hero hero = FindHero();
        hero.PushEvent(new RequestMovementEvent(direction));
    }

    public void Update()
    {
        for (Unit wo : _livingObjects.values())
            wo.Update(this);
    }

    public void AddWorldObject(WorldObject obj)
    {
        if (obj.IsInanimatedObject())
            _board.SetCell(obj.GetPosition(), obj.ToInanimatedObject());
        else if (obj.IsUnit())
            _livingObjects.put(obj.GetId(), obj.ToUnit());
    }

    public boolean IsFinished()
    {
        return _finished || (FindHero() == null);
    }

    public Hero FindHero()
    {
        for (Unit wo : _livingObjects.values())
            if (wo.IsHero())
                return wo.ToHero();
        return null;
    }

    public Eagle FindEagle()
    {
        for (Unit wo : _livingObjects.values())
            if (wo.IsEagle())
                return wo.ToEagle();
        return null;
    }

    public Sword FindSword()
    {
        for (Unit wo : _livingObjects.values())
            if (wo.IsSword())
                return wo.ToSword();
        return null;
    }

    public ArrayList<Dragon> FindDragons()
    {
        ArrayList<Dragon> r = new ArrayList<Dragon>();
        for (Unit wo : _livingObjects.values())
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
        InanimatedObject io = _board.GetCellT(p);
        if (!io.IsPath())
            return false;

        for (Unit u : _livingObjects.values())
            if (u.GetPosition().equals(p))
                return false;

        return true;
    }
}
