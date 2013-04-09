package model;

import java.io.Serializable;

/**
 * The Class Grid.
 *
 * @param <T> the stored Cell type
 */
public class Grid<T> implements Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant DEFAULT_SEPARATOR. */
    private static final char DEFAULT_SEPARATOR = ' ';

    /** The cells (2 dimensional array). */
    private final Cell<T>[][] _cells;

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
     * @param obj_init the default object
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
     * @param pos the position
     * @return the cell
     */
    public Cell<T> GetCell(Position pos)
    {
        return GetCell(pos.X, pos.Y);
    }

    /**
     * Gets the cell.
     *
     * @param x the x position
     * @param y the y position
     * @return the cell
     */
    public Cell<T> GetCell(int x, int y)
    {
        if (y >= 0 && y < _cells.length && x >= 0 && x < _cells[y].length)
            return _cells[y][x];
        return null;
    }

    /**
     * Gets the cell stored value
     *
     * @param pos the position
     * @return the value
     */
    public T GetCellT(Position pos)
    {
        return GetCellT(pos.X, pos.Y);
    }

    /**
     * Gets the cell stored value
     *
     * @param x the x position
     * @param y the y position
     * @return the value
     */
    public T GetCellT(int x, int y)
    {
        Cell<T> c = GetCell(x, y);

        return c != null ? c.GetValue() : null;
    }

    /**
     * Sets the cell.
     *
     * @param pos the position
     * @param val the value
     */
    public void SetCell(Position pos, T val)
    {
        int column = pos.X;
        int line = pos.Y;
        if (line >= 0 && line < _cells.length && column >= 0 && column < _cells[line].length)
            _cells[line][column].SetValue(val);
    }
}
