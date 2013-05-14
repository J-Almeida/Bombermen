package logic;

import java.awt.geom.Point2D;

import logic.events.Event;

public abstract class Wall extends WorldObject
{
    public int HitPoints;

    public Wall(int guid, Point2D pos, int hitpoints)
    {
        super(WorldObjectType.Wall, guid, pos);

        HitPoints = hitpoints;
    }

    @Override
    public void Update(int diff)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean IsAlive()
    {
        return IsUndestroyable() || HitPoints > 0;
    }

    public boolean IsUndestroyable()
    {
        return HitPoints == -1;
    }

    @Override
    public void HandleEvent(GameState gs, WorldObject src, Event event)
    {
        // TODO Auto-generated method stub
        
    }
}
