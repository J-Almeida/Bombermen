package model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Cell.
 *
 * @param <T> the generic type
 */
public class Cell<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new cell.
     *
     * @param val the val
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

    @Override
    public String toString()
    {
        return _value.toString();
    }

    /**
     * Was visited.
     *
     * @return true, if successful
     */
    public boolean WasVisited() { return _visited; }

    /**
     * Visit.
     */
    public void Visit() { _visited = true; }

    /** The _value. */
    private T _value;

    /** The _visited. */
    private boolean _visited;
}
