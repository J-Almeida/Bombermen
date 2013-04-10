package model;

import java.io.Serializable;

/**
 * Represents a cell inside a grid.
 *
 * @param <T> the generic type
 */
public class Cell<T> implements Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new cell.
     *
     * @param val the stored value
     */
    public Cell(T val)
    {
        _value = val;
        _visited = false;
    }

    /**
     * Gets the value.
     *
     * @return the t
     */
    public T GetValue() { return _value; }

    /**
     * Sets the value.
     *
     * @param val the val
     */
    public void SetValue(T val) { _value = val; }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return _value.toString();
    }

    /**
     * Was visited. (for DFS)
     *
     * @return true, if successful
     */
    public boolean WasVisited() { return _visited; }

    /**
     * Visit.
     */
    public void Visit() { _visited = true; }

    /** The value. */
    private T _value;

    /** Was visited */
    private boolean _visited;
}
