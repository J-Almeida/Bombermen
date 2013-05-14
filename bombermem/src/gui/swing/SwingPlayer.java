package gui.swing;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import logic.Player;

public class SwingPlayer extends Player implements IDraw
{
    private TiledImage _tiledImage;

    public final static int SIZE_WIDTH = 26;
    public final static int SIZE_HEIGHT = 36;
    public final static int SIZE_REAL_WIDTH = 360/20;
    public final static int SIZE_REAL_HEIGHT = 26;
    
    public SwingPlayer(int guid, Point2D pos, String name)
    {
        super(guid, pos, name);
        
        _tiledImage = new TiledImage("gui/swing/resources/bomberman.png", SIZE_REAL_WIDTH, SIZE_REAL_HEIGHT);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        int tile = -1;
        
        if (Direction == null)
            Direction = utils.Direction.South; // default
        
        switch (Direction)
        {
        case East:
            tile = 6;
            break;
        case North:
            tile = 10;
            break;
        case South:
            tile = 0;
            break;
        case West:
            tile = 3;
            break;
        default:
            return;
        }

        g.drawImage(_tiledImage.GetTile(tile, 0), (int)(Position.getX() - SIZE_WIDTH / 2), (int)(Position.getY() - SIZE_HEIGHT / 2), SIZE_WIDTH, SIZE_HEIGHT, null);
    }

    @Override
    public Rectangle2D GetBoundingBox()
    {
        return new Rectangle2D.Double(Position.getX() - SIZE_WIDTH / 2 - 1, Position.getY() - SIZE_HEIGHT / 2 - 1, SIZE_WIDTH + 2, SIZE_HEIGHT + 2);
    }
}
