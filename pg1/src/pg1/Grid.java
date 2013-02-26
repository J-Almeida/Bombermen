package pg1;

public class Grid<T>
{
    private static final char DEFAULT_SEPARATOR = ' ';

    public char Seperator = DEFAULT_SEPARATOR;

    public final int Width;
    public final int Height;

    @SuppressWarnings("unchecked")
    public Grid(int width, int height, T obj_init)
    {
        _cells = new Cell[height][width];

        for (int j = 0; j < _cells.length; j++)
        {
            for (int i = 0; i < _cells[j].length; i++)
                _cells[j][i] = new Cell<T>(obj_init);
        }

        Width = width;
        Height = height;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < _cells.length; i++)
        {
            for (int j = 0; j < _cells[i].length; j++)
            {
                s.append(_cells[i][j].toString());
                if (j != _cells[i].length - 1) s.append(Seperator);
            }
            if (i != _cells.length - 1) s.append("\n");
        }

        return s.toString();
    }

    public Cell<T> GetCell(utils.Pair<Integer> pos)
    {
        int column = pos.first;
        int line = pos.second;

        if (line >= 0 && line < _cells.length && column >= 0 && column < _cells[line].length)
            return _cells[line][column];
        return null;
    }

    public T GetCellT(utils.Pair<Integer> pos)
    {
        Cell<T> c = GetCell(pos);
        if (c == null)
            return null;
        return c.GetValue();
    }

    public void SetCell(utils.Pair<Integer> pos, T val)
    {
        int column = pos.first;
        int line = pos.second;
        if (line >= 0 && line < _cells.length && column >= 0 && column < _cells[line].length)
            _cells[line][column].SetValue(val);
    }

    private Cell<T> [][]_cells;
}
