package utils;

import java.util.Random;

/**
 * The Class RandomEngine.
 *
 * Static wrapper for Random
 */
public class RandomEngine
{
    /** Underlying Random generator. */
    private static Random _r = new Random();

    /**
     * Sets the underlying Random generator.
     *
     * @param r the random class
     */
    public static void SetRandom(Random r)
    {
        _r = r;
    }

    /**
     * Forces the seed for Random generator.
     *
     * @param seed the seed
     */
    public static void SetSeed(long seed)
    {
        _r.setSeed(seed);
    }

    /**
     * Reset Random generator.
     */
    public static void Reset()
    {
        _r = new Random();
    }

    /**
     * Random int between 0 and 2^32.
     *
     * @return the int
     */
    public static int GetInt()
    {
        return _r.nextInt();
    }

    /**
     * Random int between min and max, inclusive.
     *
     * @param min the min
     * @param max the max
     * @return the int
     */
    public static int GetInt(int min, int max)
    {
        return _r.nextInt(max - min + 1) + min;
    }

    /**
     * 50% chance to get true.
     *
     * @return true, if successful
     */
    public static boolean GetBool()
    {
        return _r.nextBoolean();
    }

    /**
     * N% chance to get true.
     *
     * @param chancePer the chance per
     * @return true, if successful
     */
    public static boolean GetBool(int chancePer)
    {
        return GetInt(1, 100) <= chancePer;
    }

    /**
     * Disabled constructor.
     */
    private RandomEngine() {}
}
