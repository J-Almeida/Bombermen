package logic;

import java.awt.geom.Point2D;

public class Bomb extends WorldObject
{
    public Bomb(int guid, Point2D position)
    {
        super(guid, position);
        // TODO Auto-generated constructor stub
    }

    public int Radius; // Number of cells in each direction
    public int Strength; // Damage in number of hitpoints

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
}
