package gui.swing;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import logic.Wall;

public class SwingWall extends Wall implements IDraw
{
    public SwingWall(int guid, Point2D pos, int hitpoints)
    {
        super(guid, pos, hitpoints);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void Draw(Graphics2D g)
    {
        // TODO Auto-generated method stub

    }
}
