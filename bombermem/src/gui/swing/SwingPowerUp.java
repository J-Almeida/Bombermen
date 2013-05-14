package gui.swing;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import logic.PowerUp;

public class SwingPowerUp extends PowerUp implements IDraw
{

    public SwingPowerUp(int guid, Point2D pos)
    {
        super(guid, pos);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void Draw(Graphics2D g)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Rectangle2D GetBoundingBox()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
