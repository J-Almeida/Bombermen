package logic;

import java.awt.geom.Point2D;

public abstract class WorldObject
{
    public final int Guid;

    public final Point2D Position;

    public WorldObject(int guid, Point2D position)
    {
        Guid = guid;
        Position = position;
    }
    
    public abstract boolean IsAlive();
    
    public abstract void Update(int diff);
}
