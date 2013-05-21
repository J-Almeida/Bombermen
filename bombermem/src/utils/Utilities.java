package utils;

import java.util.List;

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

    public static <T> RandElement<T> RandomElement(T[] l)
    {
        if (l.length == 0)
            return null;

        int i = RandomEngine.GetInt(0, l.length - 1);
        return new RandElement<T>(i, l[i]);
    }
}
