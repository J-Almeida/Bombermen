package logic;

import java.awt.geom.Point2D;

import logic.events.Event;

public abstract class PowerUp extends WorldObject
{
    public PowerUp(int guid, Point2D pos)
    {
        super(WorldObjectType.PowerUp, guid, pos);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void Update(int diff)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean IsAlive()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void HandleEvent(GameState gs, WorldObject src, Event event)
    {
        // TODO Auto-generated method stub
        
    }
}
