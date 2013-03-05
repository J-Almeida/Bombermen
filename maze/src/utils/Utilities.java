package utils;

import java.util.List;

import model.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class Utilities.
 */
public abstract class Utilities
{
    /**
     * The Class RandElement.
     *
     * @param <T> the generic type
     */
    public static class RandElement<T>
    {
        /**
         * Instantiates a new rand element.
         *
         * @param pos the pos
         * @param ele the ele
         */
        public RandElement(int pos, T ele)
        {
            Position = pos;
            Element = ele;
        }

        /** The Position. */
        public int Position;

        /** The Element. */
        public T Element;
    }

    /**
     * Random pair i.
     *
     * @param r the r
     * @param min1 the min1
     * @param max1 the max1
     * @param min2 the min2
     * @param max2 the max2
     * @return the pair
     */
    public static Pair<Integer> RandomPairI(int min1, int max1, int min2, int max2)
    {
        return Pair.IntN(RandomEngine.GetInt(min1, max1), RandomEngine.GetInt(min1, max2));
    }
    
    public static Position RandomPosition(int min1, int max1, int min2, int max2)
    {
    	return new Position(RandomEngine.GetInt(min1, max1), RandomEngine.GetInt(min1, max2));
    }

    /**
     * Random element.
     *
     * @param <T> the generic type
     * @param r the r
     * @param l the l
     * @return the rand element
     */
    public static <T> RandElement<T> RandomElement(List<T> l)
    {
        if (l.isEmpty())
            return null;

        int i = RandomEngine.GetInt(0, l.size() - 1);
        return new RandElement<T>(i, l.get(i));
    }
}
