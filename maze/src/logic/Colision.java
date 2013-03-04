package logic;

public class Colision<T extends Unit> extends Event
{
	public Colision(T other)
	{
		super(EventType.Colision);
		Other = other;
	}
	
	public final T Other;
}
