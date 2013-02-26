package pg1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import utils.Pair;
import utils.Utilities;

public class Maze
{
    private static final Pair<Integer> DEFAULT_POSITION = Pair.IntN(-1, -1);
    public Maze(int width, int height) // empty maze
    {
        _board = new Grid<Character>(width, height, ' ');
    }

    public void VisitDFS(Random r, CellPos cp)
    {
        cp.Cell.Visit();
        cp.Cell.SetValue(' ');
        List<Pair<CellPos>> nbrs = GetNeighbors(cp.Position);

        Utilities.RandElement<Pair<CellPos>> nb;

        do
        {
            nb = Utilities.RandomElement(r, nbrs);
            if (!nb.Element.first.Cell.WasVisited())
            {
                if (nb.Element.first.Position.first == 0 || nb.Element.first.Position.second == 0 || nb.Element.first.Position.first == _board.Width - 1 || nb.Element.first.Position.second == _board.Height - 1)
                {
                    int i = r.nextInt(4);
                    if (i > 0)
                    {
                        nb.Element.second.Cell.SetValue(' ');
                        nb.Element.second.Cell.Visit();
                    }
                }
                else
                {
                    nb.Element.second.Cell.SetValue(' ');
                    nb.Element.second.Cell.Visit();
                    VisitDFS(r, nb.Element.first);
                }
            }
            nbrs.remove(nb.Position);
        } while(!nbrs.isEmpty());
    }

    public Maze(int size, Random r) // random maze
    {
        _board = new Grid<Character>(size, size, 'X');

        Pair<Integer> initialPos = Utilities.RandomPairI(r, 1, _board.Width - 2, 1, _board.Height - 2);

        VisitDFS(r, new CellPos(_board.GetCell(initialPos), initialPos));

        SetHeroPosition(initialPos);
        SetRandomSwordPosition(r);
        SetRandomDragonPosition(r);
        SetRandomExitPosition(r);
    }

    private void SetRandomExitPosition(Random r)
    {
        List<CellPos> whitelst = new LinkedList<CellPos>();

        for (int x = 1; x < _board.Width - 1; x++)
        {
            int y = 1;
            Cell<Character> cell = _board.GetCell(Pair.IntN(x, y));
            if (cell.GetValue() == ' ')
                whitelst.add(new CellPos(_board.GetCell(Pair.IntN(x, y-1)), Pair.IntN(x, y-1)));
            
            y = _board.Height - 2;
            cell = _board.GetCell(Pair.IntN(x, y));
            if (cell.GetValue() == ' ')
                whitelst.add(new CellPos(_board.GetCell(Pair.IntN(x, y+1)), Pair.IntN(x, y+1)));
        }

        for (int y = 2; y < _board.Height - 2; y++)
        {
            int x = 1;
            Cell<Character> cell = _board.GetCell(Pair.IntN(x, y));
            if (cell.GetValue() == ' ')
                whitelst.add(new CellPos(_board.GetCell(Pair.IntN(x-1, y)), Pair.IntN(x-1, y)));
            
            x = _board.Width - 2;
            cell = _board.GetCell(Pair.IntN(x, y));
            if (cell.GetValue() == ' ')
                whitelst.add(new CellPos(_board.GetCell(Pair.IntN(x+1, y)), Pair.IntN(x+1, y)));
        }

        SetExitPosition(Utilities.RandomElement(r, whitelst).Element.Position);
    }

    private void SetRandomDragonPosition(Random r)
    {
        boolean success;
        List<Pair<CellPos>> lst = GetNeighbors(_heroPosition);
        List<Pair<Integer>> lstn = new LinkedList<Pair<Integer>>();
        
        for (Pair<CellPos> ele : lst)
        {
            lstn.add(ele.first.Position);
            lstn.add(ele.second.Position);
        }
        
        do
        {
        	Pair<Integer> p = Utilities.RandomPairI(r, 1, _board.Width - 2, 1, _board.Height - 2);
            
            success = !lstn.contains(p);
            
            if (success)
                success = SetDragonPosition(p);
        } while (!success);
    }

    private void SetRandomSwordPosition(Random r)
    {
        boolean success;
        do
        {
        	success = SetSwordPosition(Utilities.RandomPairI(r, 1, _board.Width - 2, 1, _board.Height - 2));
        } while (!success);
    }

    private static class CellPos
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
        int w = _board.Width - 1;
        int x = pos.first;
        int y = pos.second;

        if (y >= 2)
            l.add(new Pair<CellPos>(
                    new CellPos(_board.GetCell(Pair.IntN(x, y - 2)), Pair.IntN(x, y - 2)),    //Cell
                    new CellPos(_board.GetCell(Pair.IntN(x, y - 1)), Pair.IntN(x, y - 1))));  //Wall

        if (x >= 2)
            l.add(new Pair<CellPos>(
                    new CellPos(_board.GetCell(Pair.IntN(x - 2, y)), Pair.IntN(x - 2, y)),
                    new CellPos(_board.GetCell(Pair.IntN(x - 1, y)), Pair.IntN(x - 1, y))));

        if (x <= (w - 2))
            l.add(new Pair<CellPos>(
                    new CellPos(_board.GetCell(Pair.IntN(x + 2, y)), Pair.IntN(x + 2, y)),
                    new CellPos(_board.GetCell(Pair.IntN(x + 1, y)), Pair.IntN(x + 1, y))));

        if (y <= (w - 2))
            l.add(new Pair<CellPos>(
                    new CellPos(_board.GetCell(Pair.IntN(x, y + 2)), Pair.IntN(x, y + 2)),
                    new CellPos(_board.GetCell(Pair.IntN(x, y + 1)), Pair.IntN(x, y + 1))));


        return l;
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

    private boolean isAdjacent(Pair<Integer> pos1, Pair<Integer> pos2)
    {
        return (pos1.equals(Pair.IntN(pos2.first + 1, pos2.second))) ||
               (pos1.equals(Pair.IntN(pos2.first - 1, pos2.second))) ||
               (pos1.equals(Pair.IntN(pos2.first, pos2.second + 1))) ||
               (pos1.equals(Pair.IntN(pos2.first, pos2.second - 1)));
    }

    public boolean MoveHero(utils.Key direction)
    {
        boolean result = false;

        switch (direction)
        {
        case UP:
            result = SetHeroPosition(Pair.IntN(_heroPosition.first, _heroPosition.second - 1));
            break;
        case DOWN:
            result = SetHeroPosition(Pair.IntN(_heroPosition.first, _heroPosition.second + 1));
            break;
        case LEFT:
            result = SetHeroPosition(Pair.IntN(_heroPosition.first - 1, _heroPosition.second));
            break;
        case RIGHT:
            result = SetHeroPosition(Pair.IntN(_heroPosition.first + 1, _heroPosition.second));
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
            result = SetDragonPosition(Pair.IntN(_dragonPosition.first, _dragonPosition.second - 1));
            break;
        case DOWN:
            result = SetDragonPosition(Pair.IntN(_dragonPosition.first, _dragonPosition.second + 1));
            break;
        case LEFT:
            result = SetDragonPosition(Pair.IntN(_dragonPosition.first - 1, _dragonPosition.second));
            break;
        case RIGHT:
            result = SetDragonPosition(Pair.IntN(_dragonPosition.first + 1, _dragonPosition.second));
            break;
        }

        return result;
    }

    public boolean SetHeroPosition(Pair<Integer> pos)
    {
        if (!isValidPosition(pos) || _board.GetCellT(pos) == 'X' || (pos.equals(_exitPosition) && !IsHeroArmed()))
            return false;

        _heroPosition = pos;

        return true;
    }

    public boolean SetExitPosition(Pair<Integer> pos)
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
        if ((!isValidPosition(pos) && !pos.equals(DEFAULT_POSITION)) ||
            (!pos.equals(DEFAULT_POSITION) && _board.GetCellT(pos) == 'X'))
            return false;
        

        _dragonPosition = pos;

        return true;

    }

    public void Update()
    {
        if (_heroPosition.equals(_swordPosition))
            _swordPosition = DEFAULT_POSITION;
        
        if (isAdjacent(_heroPosition, _dragonPosition) || _dragonPosition.equals(_heroPosition))
        {
            if (IsHeroArmed())
                SetDragonPosition(DEFAULT_POSITION);
            else
                _heroAlive = false;
        }
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
