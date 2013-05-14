package gui.swing;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import logic.Wall;

public class SwingWall extends Wall implements IDraw
{
    private TiledImage _tileWall;
    
    public final static int SIZE_REAL = 16;
    public final static int SIZE = 40;
    
    public SwingWall(int guid, Point2D pos, int hitpoints)
    {
        super(guid, pos, hitpoints);

        _tileWall = new TiledImage("gui/swing/resources/wall.png", SIZE_REAL, SIZE_REAL);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        double x = Position.getX();
        double y = Position.getY();
        
        int col;
        if (IsUndestroyable())
            col = 0;
        else
            col = 1;

        g.drawImage(_tileWall.GetTile(col, 0), (int)(x - SIZE / 2), (int)(y - SIZE / 2), SIZE, SIZE, null);
    }

    @Override
    public Rectangle2D GetBoundingBox()
    {
        return new Rectangle2D.Double(Position.getX() - SIZE / 2, Position.getY() - SIZE / 2, SIZE, SIZE);
    }
}
