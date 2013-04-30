package logic;

import java.awt.geom.Point2D;

public class Wall extends WorldObject
{
    boolean Undestroyable;
    int HitPoints;
    
    public Wall(int guid, Point2D pos)
    {
        super(guid, pos);
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
        return Undestroyable || HitPoints > 0;
    }
}
