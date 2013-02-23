/**
 * 
 */
package utils;

/**
 * @author miguel
 *
 */
public class Pair<T> {
	public Pair(T fst, T snd)
	{
		this.first = fst;
		this.second = snd;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object rhs)
	{
		if (!(rhs instanceof Pair<?>))
			return false;
		
		Pair<T> other = (Pair<T>) rhs;
		
		return this.first == other.first && this.second == other.second;
	}
	
	public T first;
	public T second;
}
