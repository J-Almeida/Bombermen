package utils;

// TODO: Auto-generated Javadoc
/**
 * The Class Pair.
 *
 * @param <T> the generic type
 */
public class Pair<T>
{

    /**
     * Int n.
     *
     * @param fst the fst
     * @param snd the snd
     * @return the pair
     */
    public static Pair<Integer> IntN(int fst, int snd)
    {
        return new Pair<Integer>(fst, snd);
    }

    /**
     * Double n.
     *
     * @param fst the fst
     * @param snd the snd
     * @return the pair
     */
    public static Pair<Double> DoubleN(double fst, double snd)
    {
        return new Pair<Double>(fst, snd);
    }

    /**
     * Instantiates a new pair.
     *
     * @param fst the fst
     * @param snd the snd
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
    
    @Override
    public Object clone()
    {
    	Pair<T> other = new Pair<T>(this.first, this.second);
    	return (Object)other;
    }

    /** The first. */
    public T first;

    /** The second. */
    public T second;
}
