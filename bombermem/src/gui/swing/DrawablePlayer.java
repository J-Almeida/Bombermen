package gui.swing;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import logic.Player;

public class DrawablePlayer extends Player implements IDraw
{
    public DrawablePlayer(int guid, Point2D pos)
    {
        super(guid, pos);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        
    }
}
