package utils;

/**
 * The Class Pair.
 *
 * @param <T> the generic type
 */
public class Pair<T>
{
    /**
     * Builder method for Pair<Integer> (sugar).
     *
     * @param fst the first element
     * @param snd the second element
     * @return the pair
     */
    public static Pair<Integer> IntN(int fst, int snd)
    {
        return new Pair<Integer>(fst, snd);
    }

    /**
     * Builder method for Pair<Double> (sugar).
     *
     * @param fst the first element
     * @param snd the second element
     * @return the pair
     */
    public static Pair<Double> DoubleN(double fst, double snd)
    {
        return new Pair<Double>(fst, snd);
    }

    /**
     * Instantiates a new pair.
     *
     * @param fst the first element
     * @param snd the second element
     */
    public Pair(T fst, T snd)
    {
        this.first = fst;
        this.second = snd;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object rhs)
    {
        if (!(rhs instanceof Pair<?>))
            return false;

        Pair<T> other = (Pair<T>) rhs;

        return this.first == other.first && this.second == other.second;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return 1/2 * (first.hashCode() + second.hashCode()) * (first.hashCode() + second.hashCode() + 1) + second.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("(");
        sb.append(this.first);
        sb.append(", ");
        sb.append(this.second);
        sb.append(")");

        return sb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone()
    {
        Pair<T> other = new Pair<T>(this.first, this.second);
        return (Object)other;
    }

    /** The first element. */
    public T first;

    /** The second element. */
    public T second;
}
