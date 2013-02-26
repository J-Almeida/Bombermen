package pg1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import utils.Pair;

public class Maze
{
    private static final Pair<Integer> DEFAULT_POSITION = new Pair<Integer>(-1, -1);
    public Maze(int width, int height) // empty maze
    {
        _board = new Grid<Character>(width, height, ' ');
    }

    public void VisitDFS(Random r, CellPos cp)
    {
        System.out.println(toString()); // debug
        System.out.println();

        cp.Cell.Visit();
        List<Pair<CellPos>> nbrs = GetNeighbors(cp.Position);
        Pair<CellPos> nb = RandomElement(r, nbrs).Element;

        if (!nb.first.Cell.WasVisited())
        {
            nb.second.Cell.SetValue(' ');
            VisitDFS(r, nb.first);
        }
    }

    public Maze(int width, int height, Random r) // random maze
    {
        this(width, width);

        assert width == height : "square maze is required for random generation";

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < width; y++)
            {
                /* checkboard
                if ((x == 0 || y == 0 || x == width - 1 || y == width - 1) ||
                        ((x % 2) != 0 && (y % 2) == 0) ||
                        ((x % 2) == 0 && (y % 2) != 0))
                    _board.SetCell(Pair.IntN(x, y), 'X');
                else
                    _board.SetCell(Pair.IntN(x, y), ' ');
                */
                if ((x == 0 || y == 0 || x == width - 1 || y == width - 1) ||
                        ((x % 2) == 0 || (y % 2) == 0))
                    _board.SetCell(Pair.IntN(x, y), 'X');
                else
                    _board.SetCell(Pair.IntN(x, y), ' ');
            }
        }

        Pair<Integer> exitPos = Pair.IntN(1, 1);
        Cell<Character> exitCell = _board.GetCell(exitPos);

        CellPos exit = new CellPos(exitCell, exitPos);

        VisitDFS(r, exit);
    }

    public static class CellPos
    {
        CellPos(Cell<Character> c, Pair<Integer> pos)
        {
            Cell = c;
            Position = pos;
        }

        Cell<Character> Cell;
        Pair<Integer> Position;
    }

    // 1s - "cell", 2nd - "wall"
    public List<Pair<CellPos>> GetNeighbors(Pair<Integer> pos)
    {
        List<Pair<CellPos>> l = new LinkedList<Pair<CellPos>>();
        int w = _board.Width;
        int x = pos.first;
        int y = pos.second;

        if (y != 2 && y != 1)             l.add(new Pair<CellPos>(new CellPos(_board.GetCell(Pair.IntN(x    , y - 2)), Pair.IntN(x    , y - 2)), new CellPos(_board.GetCell(Pair.IntN(x    , y - 1)), Pair.IntN(x    , y - 1))));
        if (x != 2 && x != 1)             l.add(new Pair<CellPos>(new CellPos(_board.GetCell(Pair.IntN(x - 2, y    )), Pair.IntN(x - 2, y    )), new CellPos(_board.GetCell(Pair.IntN(x - 1, y    )), Pair.IntN(x - 1, y    ))));
        if (x != (w - 2) && x != (w - 1)) l.add(new Pair<CellPos>(new CellPos(_board.GetCell(Pair.IntN(x + 2, y    )), Pair.IntN(x + 2, y    )), new CellPos(_board.GetCell(Pair.IntN(x + 1, y    )), Pair.IntN(x + 1, y    ))));
        if (y != (w - 2) && y != (w - 1)) l.add(new Pair<CellPos>(new CellPos(_board.GetCell(Pair.IntN(x    , y + 2)), Pair.IntN(x    , y + 2)), new CellPos(_board.GetCell(Pair.IntN(x    , y + 1)), Pair.IntN(x    , y + 1))));

        for (int i = 0; i < l.size(); )
        {
            if (l.get(i).first.Cell.GetValue() != ' ')
                l.remove(i);
            else
                i++;
        }

        return l;
    }

    // @TODO: move me
    public static int RandomBetween(Random r, int min, int max)
    {
        return r.nextInt(max - min + 1) + min;
    }

    public static class RandElement<T>
    {
        RandElement(int pos, T ele)
        {
            Position = pos;
            Element = ele;
        }
        int Position;
        T Element;
    }

    // @TODO: move me
    public static <T> RandElement<T> RandomElement(Random r, List<T> l)
    {
        int i = r.nextInt(l.size());
        return new RandElement<T>(i, l.get(i));
    }

    public Maze(int width, int height, String[] cells) // maze defined with list of strings
    {
        this(width, height);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
            {
                char c = cells[j].charAt(i);
                if (c != 'X' && c != ' ')
                    c = ' ';

                _board.SetCell(Pair.IntN(i, j), c);
            }
    }

    @Override
    public String toString()
    {
        _board.SetCell(_heroPosition, IsHeroArmed() ? 'A' : 'H');
        _board.SetCell(_exitPosition, 'S');
        if (_swordPosition.equals(_dragonPosition))
            _board.SetCell(_swordPosition, 'F');
        else
        {
            _board.SetCell(_swordPosition, 'E');
            _board.SetCell(_dragonPosition, 'D');
        }

        String res = _board.toString();

        _board.SetCell(_heroPosition, ' ');
        _board.SetCell(_exitPosition, ' ');
        _board.SetCell(_swordPosition, ' ');
        _board.SetCell(_dragonPosition, ' ');

        return res;
    }

    private boolean isValidPosition(Pair<Integer> pos)
    {
        return (pos.first >= 0) && (pos.second >= 0) && (pos.first < _board.Width) && (pos.second < _board.Height);
    }

    private boolean isAdjacent(Pair<Integer> pos1, Pair<Integer> pos2) {
        return (pos1.equals(new Pair<Integer>(pos2.first + 1, pos2.second))) ||
               (pos1.equals(new Pair<Integer>(pos2.first - 1, pos2.second))) ||
               (pos1.equals(new Pair<Integer>(pos2.first, pos2.second + 1))) ||
               (pos1.equals(new Pair<Integer>(pos2.first, pos2.second - 1)));
    }

    public boolean MoveHero(utils.Key direction)
    {
        boolean result = false;

        switch (direction)
        {
        case UP:
            result = SetHeroPosition(new Pair<Integer>(_heroPosition.first, _heroPosition.second - 1));
            break;
        case DOWN:
            result = SetHeroPosition(new Pair<Integer>(_heroPosition.first, _heroPosition.second + 1));
            break;
        case LEFT:
            result = SetHeroPosition(new Pair<Integer>(_heroPosition.first - 1, _heroPosition.second));
            break;
        case RIGHT:
            result = SetHeroPosition(new Pair<Integer>(_heroPosition.first + 1, _heroPosition.second));
            break;
        }


        return result;
    }

    public boolean MoveDragon(utils.Key direction)
    {
        boolean result = false;

        if (!IsDragonAlive())
            return true;

        switch (direction)
        {
        case UP:
            result = SetDragonPosition(new Pair<Integer>(_dragonPosition.first, _dragonPosition.second - 1));
            break;
        case DOWN:
            result = SetDragonPosition(new Pair<Integer>(_dragonPosition.first, _dragonPosition.second + 1));
            break;
        case LEFT:
            result = SetDragonPosition(new Pair<Integer>(_dragonPosition.first - 1, _dragonPosition.second));
            break;
        case RIGHT:
            result = SetDragonPosition(new Pair<Integer>(_dragonPosition.first + 1, _dragonPosition.second));
            break;
        }

        return result;
    }

    public boolean SetHeroPosition(Pair<Integer> pos)
    {
        if (!isValidPosition(pos))
            return false;

        char prevCell = _board.GetCellT(pos);
        if (prevCell == 'X' )
            return false;

        if (pos.equals(_exitPosition))
        {
            if (IsHeroArmed())
                _finished = true;
            else
                return false;
        }

        if (pos.equals(_swordPosition))
            _swordPosition = DEFAULT_POSITION;

        if (isAdjacent(pos, _dragonPosition) || pos.equals(_dragonPosition))
        {
            if (IsHeroArmed())
                SetDragonPosition(DEFAULT_POSITION);
            else
                _heroAlive = false;
        }
        _heroPosition = pos;

        return true;
    }

    public boolean SetExit(Pair<Integer> pos)
    {
        if (!isValidPosition(pos))
            return false;

        _exitPosition = pos;

        return true;
    }

    public boolean SetSwordPosition(Pair<Integer> pos)
    {
        if (!isValidPosition(pos) && !pos.equals(DEFAULT_POSITION))
            return false;

        _swordPosition = pos;

        return true;
    }

    public boolean SetDragonPosition(Pair<Integer> pos)
    {
        if (!isValidPosition(pos) && !pos.equals(DEFAULT_POSITION))
            return false;

        if (!pos.equals(DEFAULT_POSITION) && _board.GetCellT(pos) == 'X' )
            return false;

        if (isAdjacent(_heroPosition, pos) || pos.equals(_heroPosition))
        {
            if (IsHeroArmed())
                SetDragonPosition(DEFAULT_POSITION);
            else
                _heroAlive = false;
        }

        _dragonPosition = pos;

        return true;

    }

    public boolean IsFinished() { return _finished || !_heroAlive; }
    public boolean IsHeroArmed() { return _swordPosition.equals(DEFAULT_POSITION); }
    public boolean IsHeroAlive() { return _heroAlive; }
    public boolean IsDragonAlive() { return !_dragonPosition.equals(DEFAULT_POSITION); }
    private Grid<Character> _board;

    private Pair<Integer> _heroPosition = DEFAULT_POSITION;
    private Pair<Integer> _swordPosition = DEFAULT_POSITION;
    private Pair<Integer> _exitPosition = DEFAULT_POSITION;
    private Pair<Integer> _dragonPosition = DEFAULT_POSITION;

    private boolean _finished = false;
    private boolean _heroAlive = true;
}
