package logic;

import java.awt.Point;

import logic.events.Event;

import utils.Direction;

public abstract class WorldObject
{
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

    public abstract void Update(GameState gameState, int diff);

    public abstract void HandleEvent(GameState gs, WorldObject src, Event event);
}
