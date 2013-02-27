package utils;

import java.util.List;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Utilities.
 */
public abstract class Utilities
{

    /**
     * Random between.
     *
     * @param r the r
     * @param min the min
     * @param max the max
     * @return the int
     */
    public static int RandomBetween(Random r, int min, int max)
    {
        return r.nextInt(max - min + 1) + min;
    }

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
    public static Pair<Integer> RandomPairI(Random r, int min1, int max1, int min2, int max2)
    {
        return Pair.IntN(RandomBetween(r, min1, max1), RandomBetween(r, min1, max2));
    }

    /**
     * Random element.
     *
     * @param <T> the generic type
     * @param r the r
     * @param l the l
     * @return the rand element
     */
    public static <T> RandElement<T> RandomElement(Random r, List<T> l)
    {
        if (!l.isEmpty())
        {
            int i = r.nextInt(l.size());
            return new RandElement<T>(i, l.get(i));
        }
        else
        {
            return null;
        }
    }
}
