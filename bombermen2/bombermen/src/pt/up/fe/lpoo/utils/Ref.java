package pt.up.fe.lpoo.utils;

/**
 * Wrapper for passing value types by reference, sort of...
 *
 * @param <T> the generic type
 */
public class Ref<T>
{

    /** The value. */
    private T value;

    /**
     * Instantiates a new reference.
     *
     * @param value the value
     */
    public Ref(T value)
    {
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the t
     */
    public T Get()
    {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param other the other
     */
    public void Set(T other)
    {
        value = other;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return value.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        return value.equals(obj);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return value.hashCode();
    }
}
