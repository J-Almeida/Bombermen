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

    public Maze(int width, int height, Random r) // random maze
    {
        this(width, width);

        assert width == height : "square maze is required for random generation";

        // 1. Start with a grid full of walls.
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                _board.SetCell(Pair.IntN(i, j), 'X');
            }
        }

        List<CellPos> walls = new LinkedList<CellPos>();

        // 2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
        Pair<Integer> initP = Pair.IntN(RandomBetween(r, 1, width - 2), RandomBetween(r, 1, width - 2));
        Cell<Character> initialCell = _board.GetCell(initP);
        initialCell.SetValue(' ');
        walls.addAll(GetWallsOfCell(initP.first, initP.second));

        System.out.println(toString()); // debug

        // 3. While there are walls in the list
        while (!walls.isEmpty())
        {
            System.out.println(toString()); // debug

            // 3.1. Pick a random wall from the list
            RandElement<CellPos> w = RandomElement(r, walls);

            if (w.Element.Position.second != 1)
            {
                // 3.1. If the cell on the opposite (????) side isn't in the maze yet:
                Cell<Character> currentCell = _board.GetCell(Pair.IntN(w.Element.Position.first, w.Element.Position.second - 1));
                if (currentCell.GetValue() != ' ')
                {
                    // 3.1.1. Make the wall a passage and mark the cell on the opposite side as part of the maze.
                    w.Element.Cell.SetValue(' ');
                    currentCell.SetValue(' ');

                    // 3.1.2. Add the neighboring walls of the cell to the wall list.
                    List<CellPos> nbs = GetWallsOfCell(w.Element.Position.first, w.Element.Position.second - 1);
                    for (CellPos cp : nbs)
                        if (!walls.contains(cp))
                            walls.add(cp);
                }
                // 3.2 If the cell on the opposite side already was in the maze, remove the wall from the list.
                else
                    walls.remove(w.Position);
            }
        }
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

    public List<CellPos> GetWallsOfCell(int x, int y)
    {
        List<CellPos> l = new LinkedList<CellPos>();

        int w = _board.Width;

      //if (x != 1       && y != 1       && _board.GetCellT(Pair.IntN(x - 1, y - 1)) == 'X') l.add(new CellPos(_board.GetCell(Pair.IntN(x - 1, y - 1)), Pair.IntN(x - 1, y - 1)));
          if (y != 1                       && _board.GetCellT(Pair.IntN(x    , y - 1)) == 'X') l.add(new CellPos(_board.GetCell(Pair.IntN(x    , y - 1)), Pair.IntN(x    , y - 1)));
      //if (x != (w - 1) && y != 1       && _board.GetCellT(Pair.IntN(x + 1, y - 1)) == 'X') l.add(new CellPos(_board.GetCell(Pair.IntN(x + 1, y - 1)), Pair.IntN(x + 1, y - 1)));
          if (x != 1                       && _board.GetCellT(Pair.IntN(x - 1, y    )) == 'X') l.add(new CellPos(_board.GetCell(Pair.IntN(x - 1, y    )), Pair.IntN(x - 1, y    )));
          if (x != (w - 1)                 && _board.GetCellT(Pair.IntN(x + 1, y    )) == 'X') l.add(new CellPos(_board.GetCell(Pair.IntN(x + 1, y    )), Pair.IntN(x + 1, y    )));
      //if (x != 1       && y != (w - 1) && _board.GetCellT(Pair.IntN(x - 1, y + 1)) == 'X') l.add(new CellPos(_board.GetCell(Pair.IntN(x - 1, y + 1)), Pair.IntN(x - 1, y + 1)));
          if (y != (w - 1)                 && _board.GetCellT(Pair.IntN(x    , y + 1)) == 'X') l.add(new CellPos(_board.GetCell(Pair.IntN(x    , y + 1)), Pair.IntN(x    , y + 1)));
      //if (x != (w - 1) && y != (w - 1) && _board.GetCellT(Pair.IntN(x + 1, y + 1)) == 'X') l.add(new CellPos(_board.GetCell(Pair.IntN(x + 1, y + 1)), Pair.IntN(x + 1, y + 1)));
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

  //public static Cell<Character> RandomWall(Random r, List<Cell<Character>> l)
  //{
  //     while (true) // mhmmm....
  //     {
  //         Cell<Character> c = RandomElement(r, l);
  //         if (c.GetValue() == ' ')
  //             return c;
  //     }
  //}

    public Maze(int width, int height, String[] cells) // maze defined with list of strings
    {
        this(width, height);

        for (int i = 0; i < width; i++)            // Para cada coluna
            for (int j = 0; j < height; j++)    // Para cada linha
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
