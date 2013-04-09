package logic;

import java.util.LinkedList;
import java.util.List;

import model.Cell;
import model.Position;
import utils.Pair;
import utils.Utilities;

/**
 * Builder.
 */
public abstract class MazeGenerator
{
    /** The maze size. */
    protected int _size = 10;

    /** The dragon count. */
    protected int _dragonCount = 2;

    /** The dragon behaviour. */
    protected Dragon.Behaviour _dragonBehaviour = Dragon.Behaviour.Sleepy;

    /** The maze. */
    protected Maze _maze;

    /**
     * Gets the maze.
     *
     * @return the maze
     */
    public Maze GetMaze() { return _maze; }

    /**
     * Creates the new maze.
     */
    public void CreateNewMaze() { _maze = new Maze(_size, _size); }

    /**
     * Sets the maze size.
     *
     * @param size the size
     */
    public void SetMazeSize(int size) { _size = size; }

    /**
     * Sets the number of dragons.
     *
     * @param num the num
     */
    public void SetNumberOfDragons(int num)
    {
        final int maxDragons = (int) (0.5 * _size * _size - 1.2463 * _size - 0.5273) - 8; // average number of free cells minus 8 cells around hero that cannot be used
        if (num > maxDragons)
            _dragonCount = maxDragons;
        else
            _dragonCount = num;
    }

    /**
     * Sets the dragons behaviour.
     *
     * @param db the db
     */
    public void SetDragonsBehaviour(Dragon.Behaviour db) { _dragonBehaviour = db; }

    /**
     * Builds the maze.
     */
    public abstract void BuildMaze();

    /**
     * Sets the random exit position.
     */
    protected void SetRandomExitPosition()
    {
        List<CellPos> whitelst = new LinkedList<CellPos>();

        for (int x = 1; x < _maze.GetWidth() - 1; x++)
        {
            int y = 1;
            Cell<InanimatedObject> cell = _maze.GetGrid().GetCell(new Position(x, y));
            if (cell.GetValue().IsPath())
                whitelst.add(new CellPos(_maze.GetGrid().GetCell(new Position(x, y-1)), new Position(x, y-1)));

            y = _maze.GetHeight() - 2;
            cell = _maze.GetGrid().GetCell(new Position(x, y));
            if (cell.GetValue().IsPath())
                whitelst.add(new CellPos(_maze.GetGrid().GetCell(new Position(x, y+1)), new Position(x, y+1)));
        }

        for (int y = 2; y < _maze.GetHeight() - 2; y++)
        {
            int x = 1;
            Cell<InanimatedObject> cell = _maze.GetGrid().GetCell(new Position(x, y));
            if (cell.GetValue().IsPath())
                whitelst.add(new CellPos(_maze.GetGrid().GetCell(new Position(x-1, y)), new Position(x-1, y)));

            x = _maze.GetWidth() - 2;
            cell = _maze.GetGrid().GetCell(new Position(x, y));
            if (cell.GetValue().IsPath())
                whitelst.add(new CellPos(_maze.GetGrid().GetCell(new Position(x+1, y)), new Position(x+1, y)));
        }

        ExitPortal exit = new ExitPortal();
        exit.SetPosition(Utilities.RandomElement(whitelst).Element.Pos);
        _maze.AddWorldObject(exit);
    }

    /**
     * Sets the random dragons position.
     */
    protected void SetRandomDragonsPosition()
    {
        for (int i = 0; i < _dragonCount; i++)
        {
            Dragon d = new Dragon(_dragonBehaviour);
            boolean success;
            List<Pair<CellPos>> lst = GetNeighbors(_maze.FindHero().GetPosition());
            List<Position> lstn = new LinkedList<Position>();
            lstn.add(_maze.FindHero().GetPosition());

            for (Pair<CellPos> ele : lst)
            {
                lstn.add(ele.first.Pos);
                lstn.add(ele.second.Pos);
            }

            do
            {
                Position p = Utilities.RandomPosition(1, _maze.GetWidth() - 2, 1, _maze.GetHeight() - 2);

                success = !lstn.contains(p);

                if (success)
                {
                    if (_maze.IsPathPosition(p))
                    {
                        d.SetPosition(p);
                        _maze.AddWorldObject(d);
                        success = true;
                    }
                    else
                        success = false;
                }
            } while (!success);
        }
    }

    /**
     * Sets the random sword position.
     */
    protected void SetRandomSwordPosition()
    {
        boolean success;
        do
        {
            Position p = Utilities.RandomPosition(1, _maze.GetWidth() - 2, 1, _maze.GetHeight() - 2);
            if (_maze.IsEmptyPosition(p))
            {
                Sword s = new Sword();
                s.SetPosition(p);
                _maze.AddWorldObject(s);
                success = true;
            }
            else
                success = false;
        } while (!success);
    }

    /**
     * Gets the neighbors.
     *
     * @param pos the position
     * @return the list of ("cell", 2nd - "wall")s
     */
    protected List<Pair<CellPos>> GetNeighbors(Position pos)
    {
        List<Pair<CellPos>> l = new LinkedList<Pair<CellPos>>();
        int w = _maze.GetWidth() - 1;
        int x = pos.X;
        int y = pos.Y;

        if (y >= 2)
            l.add(new Pair<CellPos>(
                    new CellPos(_maze.GetGrid().GetCell(new Position(x, y - 2)), new Position(x, y - 2)),    //Cell
                    new CellPos(_maze.GetGrid().GetCell(new Position(x, y - 1)), new Position(x, y - 1))));  //Wall

        if (x >= 2)
            l.add(new Pair<CellPos>(
                    new CellPos(_maze.GetGrid().GetCell(new Position(x - 2, y)), new Position(x - 2, y)),
                    new CellPos(_maze.GetGrid().GetCell(new Position(x - 1, y)), new Position(x - 1, y))));

        if (x <= w - 2)
            l.add(new Pair<CellPos>(
                    new CellPos(_maze.GetGrid().GetCell(new Position(x + 2, y)), new Position(x + 2, y)),
                    new CellPos(_maze.GetGrid().GetCell(new Position(x + 1, y)), new Position(x + 1, y))));

        if (y <= w - 2)
            l.add(new Pair<CellPos>(
                    new CellPos(_maze.GetGrid().GetCell(new Position(x, y + 2)), new Position(x, y + 2)),
                    new CellPos(_maze.GetGrid().GetCell(new Position(x, y + 1)), new Position(x, y + 1))));

        return l;
    }

    /**
     * Helper class to represent a cell and its position.
     */
    protected static class CellPos
    {
        /**
         * Instantiates a new CellPos.
         *
         * @param c the cell
         * @param pos the position
         */
        CellPos(Cell<InanimatedObject> c, Position pos)
        {
            Cell = c;
            Pos = pos;
        }

        /** The Cell. */
        Cell<InanimatedObject> Cell;

        /** The Position. */
        Position Pos;
    }

    /**
     * Helper class to represent a CellPos and its neighbours.
     */
    protected static class CellNeighbors
    {
        /** The cell-position pair. */
        public CellPos cp;

        /** List of neighbours. */
        public List<Pair<CellPos>> nbrs;
    }
}
