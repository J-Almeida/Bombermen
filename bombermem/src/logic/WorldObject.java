package logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import logic.events.Event;

import utils.Direction;

public abstract class WorldObject
{
    public final int Guid;

    public final Point2D Position;
    
    public Direction Direction;
    
    public final WorldObjectType Type;

    public WorldObject(WorldObjectType type, int guid, Point2D position)
    {
        Guid = guid;
        Position = position;
        Direction = null;
        Type = type;
    }
    
    public abstract boolean IsAlive();
    
    public abstract void Update(int diff);

    public abstract void HandleEvent(GameState gs, WorldObject src, Event event);
    
    public abstract Rectangle2D GetBoundingBox();
}
