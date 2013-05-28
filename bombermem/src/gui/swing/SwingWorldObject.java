package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import utils.Direction;
import logic.WorldObjectType;
import logic.events.Event;
import model.Positionable;

public abstract class SwingWorldObject implements IDraw, Positionable
{
	
	public SwingWorldObject(int guid, WorldObjectType type, Point pos, Direction dir)
	{
		Guid = guid;
		Type = type;
		Position = pos;
		Dir = dir;
	}
	
	public final int Guid;
	public final WorldObjectType Type;
	public Point Position;
	public Direction Dir;
	
	public int GetGuid() { return Guid; }
	public Point GetPosition() { return Position; }
	
	@Override
	public abstract void Draw(Graphics2D g);
	
	public abstract void Handle(Event ev);
	
	public void Update(int diff) { }

}
