package model;

/**
 * The Class Grid.
 *
 * @param <T> the underlying Cell type
 */
public class Grid<T>
{
    /** The Constant DEFAULT_SEPARATOR. */
    private static final char DEFAULT_SEPARATOR = ' ';

    /** The _cells. */
    private Cell<T>[][] _cells;

    /** The Separator. */
    public char Separator = DEFAULT_SEPARATOR;

    /** The Width. */
    public final int Width;

    /** The Height. */
    public final int Height;

    /**
     * Instantiates a new grid.
     *
     * @param width the width
     * @param height the height
     * @param obj_init the obj_init
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < _cells.length; i++)
        {
            for (int j = 0; j < _cells[i].length; j++)
            {
                s.append(_cells[i][j].toString());
                if (j != _cells[i].length - 1) s.append(Separator);
            }
            if (i != _cells.length - 1) s.append("\n");
        }

        return s.toString();
    }

    /**
     * Gets the cell.
     *
     * @param pos the pos
     * @return the cell
     */
    public Cell<T> GetCell(utils.Pair<Integer> pos)
    {
        int column = pos.first;
        int line = pos.second;

        if (line >= 0 && line < _cells.length && column >= 0 && column < _cells[line].length)
            return _cells[line][column];
        return null;
    }

    /**
     * Gets the cell t.
     *
     * @param pos the pos
     * @return the t
     */
    public T GetCellT(utils.Pair<Integer> pos)
    {
        Cell<T> c = GetCell(pos);
        if (c == null)
            return null;
        return c.GetValue();
    }

    /**
     * Sets the cell.
     *
     * @param pos the pos
     * @param val the val
     */
    public void SetCell(utils.Pair<Integer> pos, T val)
    {
        int column = pos.first;
        int line = pos.second;
        if (line >= 0 && line < _cells.length && column >= 0 && column < _cells[line].length)
            _cells[line][column].SetValue(val);
    }
}
