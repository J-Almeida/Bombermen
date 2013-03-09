package utils;

import java.util.Random;

public class RandomEngine
{
    /** Underlying Random generator */
    private static Random _r = new Random();

    public static void SetRandom(Random r)
    {
        _r = r;
    }

    /** Forces the seed for Random generator */
    public static void SetSeed(long seed)
    {
        _r.setSeed(seed);
    }

    /** Reset Random generator */
    public static void Reset()
    {
        _r = new Random();
    }

    /** Random int between 0 and 2^32 */
    public static int GetInt()
    {
        return _r.nextInt();
    }

    /** Random int between min and max, inclusive */
    public static int GetInt(int min, int max)
    {
        return _r.nextInt(max - min + 1) + min;
    }

    /** 50% chance to get true */
    public static boolean GetBool()
    {
        return _r.nextBoolean();
    }

    /** N% chance to get true */
    public static boolean GetBool(int chancePer)
    {
        return GetInt(1, 100) <= chancePer;
    }

    /** Disabled constructor */
    private RandomEngine() {}
}
