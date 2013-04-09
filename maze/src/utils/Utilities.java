package utils;

import java.util.List;

import model.Position;

/**
 * Miscellaneous utility functions
 */
public abstract class Utilities
{
    /**
     * Class returned by RandomElement(List<T> l)
     *
     * @param <T> the generic type
     */
    public static class RandElement<T>
    {
        /**
         * Instantiates a new rand element.
         *
         * @param pos the index
         * @param ele the object
         */
        public RandElement(int pos, T ele)
        {
            Position = pos;
            Element = ele;
        }

        /** The index. */
        public int Position;

        /** The object. */
        public T Element;
    }

    /**
     * Random position between [min, max] values.
     *
     * @param min1 the minimum X
     * @param max1 the maximum X
     * @param min2 the minimum Y
     * @param max2 the maximum Y
     * @return the position
     */
    public static Position RandomPosition(int min1, int max1, int min2, int max2)
    {
        return new Position(RandomEngine.GetInt(min1, max1), RandomEngine.GetInt(min1, max2));
    }

    /**
     * Random element of a list.
     *
     * Null is returned if list is empty.
     *
     * @param <T> the generic type
     * @param l the list
     * @return the picked random element
     */
    public static <T> RandElement<T> RandomElement(List<T> l)
    {
        if (l.isEmpty())
            return null;

        int i = RandomEngine.GetInt(0, l.size() - 1);
        return new RandElement<T>(i, l.get(i));
    }
}
