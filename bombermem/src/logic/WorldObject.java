package logic;

import java.awt.Point;

import logic.events.Event;
import model.QuadTree;
import utils.Direction;

public abstract class WorldObject implements network.NetWorldObject, model.Positionable
{
    @Override
	public int GetGuid() 
    {
		return Guid;
	}

	@Override
	public Point GetPosition() 
	{
		return Position;
	}

	@Override
	public Direction GetDirection()
	{
		return Dir;
	}

	@Override
	public WorldObjectType GetType()
	{
		return Type;
	}

	public final int Guid;

    public final Point Position;

    public Direction Dir;

    public final WorldObjectType Type;

    public WorldObject(WorldObjectType type, int guid, Point position)
    {
        Guid = guid;
        Position = position;
        Dir = null;
        Type = type;
    }

    public abstract boolean IsAlive();

    public abstract void Update(QuadTree<WorldObject> qt, int diff);

    public abstract void HandleEvent(GameState gs, WorldObject src, Event event);
}
