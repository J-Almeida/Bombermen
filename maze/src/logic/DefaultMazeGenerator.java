package logic;

import model.Position;

/**
 * ConcreteBuilder - Default 10x10 maze.
 */
public class DefaultMazeGenerator extends MazeGenerator
{
    /** String representation of the maze. */
    private String[] _cells;

    /**
     * Instantiates a new default maze generator.
     *
     * @param cells the string representation of the maze.
     */
    public DefaultMazeGenerator(String[] cells)
    {
        _cells = cells.clone();
    }

    /**
     * Instantiates a new default maze generator.
     */
    public DefaultMazeGenerator()
    {
         this(new String[] {  "XXXXXXXXXX",
                              "XH       X",
                              "X XX X X X",
                              "XDXX X X X",
                              "X XX X X X",
                              "X      X S",
                              "X XX X X X",
                              "X XX X X X",
                              "XEXX     X",
                              "XXXXXXXXXX" });
    }

    /* (non-Javadoc)
     * @see logic.MazeGenerator#BuildMaze()
     */
    @Override
    public void BuildMaze()
    {
        for (int x = 0; x < _size; x++) // width
        {
            for (int y = 0; y < _size; y++) // height
            {
                char c = _cells[y].charAt(x);

                switch (c)
                {
                    case 'H':
                    {
                        if (_maze.FindHero() != null)
                            throw new IllegalArgumentException();

                        Hero h = new Hero();
                        h.SetPosition(new Position(x, y));
                        _maze.AddWorldObject(h);
                        break;
                    }
                    case 'A':
                    {
                        if (_maze.FindHero() != null || _maze.FindSword() != null)
                            throw new IllegalArgumentException();

                        Hero h = new Hero();
                        h.SetPosition(new Position(x, y));
                        h.EquipSword(true);
                        _maze.AddWorldObject(h);
                        break;
                    }
                    case 'E':
                    {
                        if (_maze.FindSword() != null)
                            throw new IllegalArgumentException();

                        if (_maze.FindHero() != null)
                            if (_maze.FindHero().IsArmed())
                                throw new IllegalArgumentException();

                        Sword s = new Sword();
                        s.SetPosition(new Position(x, y));
                        _maze.AddWorldObject(s);
                        break;
                    }
                    case 'F':
                    {
                        if (_maze.FindSword() != null)
                            throw new IllegalArgumentException();

                        if (_maze.FindHero() != null)
                            if (_maze.FindHero().IsArmed())
                                throw new IllegalArgumentException();

                        Dragon d = new Dragon(_dragonBehaviour);
                        d.SetPosition(new Position(x, y));

                        Sword s = new Sword();
                        s.SetPosition(new Position(x, y));

                        _maze.AddWorldObject(d);
                        _maze.AddWorldObject(s);
                        break;
                    }
                    case 'D':
                    {
                        if (_maze.FindDragons().size() > _dragonCount)
                            throw new IllegalArgumentException();

                        Dragon d = new Dragon(_dragonBehaviour);
                        d.SetPosition(new Position(x, y));
                        _maze.AddWorldObject(d);
                        break;
                    }
                    case 'S':
                    {
                        if (_maze.FindExitPortal() != null)
                            throw new IllegalArgumentException();

                        ExitPortal e = new ExitPortal();
                        e.SetPosition(new Position(x, y));
                        _maze.AddWorldObject(e);
                        break;
                    }
                    case 'X':
                    {
                        Wall w = new Wall();
                        w.SetPosition(new Position(x, y));
                        _maze.AddWorldObject(w);
                        break;
                    }
                    case ' ':
                    {
                        Path p = new Path();
                        p.SetPosition(new Position(x, y));
                        _maze.AddWorldObject(p);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
    }
}
