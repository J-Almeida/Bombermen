package utils;

import java.util.List;
import java.util.Random;

public abstract class Utilities
{
	public static int RandomBetween(Random r, int min, int max)
	{
	    return r.nextInt(max - min + 1) + min;
	}
	
	public static class RandElement<T>
	{
	    public RandElement(int pos, T ele)
	    {
	        Position = pos;
	        Element = ele;
	    }

	    public int Position;
	    public T Element;
	}
	
	public static Pair<Integer> RandomPairI(Random r, int min1, int max1, int min2, int max2)
	{
		return Pair.IntN(RandomBetween(r, min1, max1), RandomBetween(r, min1, max2));
	}

	public static <T> RandElement<T> RandomElement(Random r, List<T> l)
	{
	    int i = r.nextInt(l.size());
	    return new RandElement<T>(i, l.get(i));
	}
}
