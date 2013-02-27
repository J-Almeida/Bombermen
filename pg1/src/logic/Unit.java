package logic;

import utils.Pair;

public abstract class Unit {
    
	public final static Pair<Integer> DEFAULT_POSITION = Pair.IntN(-1, -1);
	
	
	public Unit(UnitType type)
	{
		Type = type;
		_alive = true;
	}
	
	public Pair<Integer> GetPosition()
	{
		return _position;
	}
	
	public void SetPosition(Pair<Integer> pos)
	{
		_position = pos;
	}
	
	public boolean IsAlive()
	{
		return _alive;
	}
	
	public void Kill()
	{
	    _alive = false;
	}
	
	public final UnitType Type;
	protected boolean _alive;
	protected Pair<Integer> _position = DEFAULT_POSITION;
	
	
}
