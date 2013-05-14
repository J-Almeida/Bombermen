package gui.swing;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import logic.Bomb;

public class SwingBomb extends Bomb implements IDraw
{
    TiledImage _tiledImage;
    private static final int SIZE = 16;
    
    public SwingBomb(int guid, Point2D position, int playerOwnerId, int radius, int strength, int time)
    {
        super(guid, position, playerOwnerId, radius, strength, time);

        _tiledImage = new TiledImage("gui/swing/resources/explosion.png", SIZE, SIZE);
    }
    
    int strength = 0;

    @Override
    public void Draw(Graphics2D g)
    {
        double x = Position.getX();
        double y = Position.getY();
        
        if (/* explosion */ true)
        {
            strength = (strength + 1) % 5;

            // center
            g.drawImage(_tiledImage.GetTile(2, strength), (int)(x - SIZE / 2), (int)(y - SIZE / 2), null); // middle
        
            // body
            for (int i = 1; i < Radius; ++i)
            {
                g.drawImage(_tiledImage.GetTile(1, strength), (int)((x - SIZE / 2) - (i * SIZE)), (int)((y - SIZE / 2)), null); // west
                g.drawImage(_tiledImage.GetTile(4, strength), (int)((x - SIZE / 2) + (i * SIZE)), (int)((y - SIZE / 2)), null); // east
                g.drawImage(_tiledImage.GetTile(6, strength), (int)((x - SIZE / 2)), (int)((y - SIZE / 2) - (i * SIZE)), null); // north
                g.drawImage(_tiledImage.GetTile(8, strength), (int)((x - SIZE / 2)), (int)((y - SIZE / 2) + (i * SIZE)), null); // south 
            }
            
            // tip
            g.drawImage(_tiledImage.GetTile(0, strength), (int)((x - SIZE / 2) - (Radius * SIZE)), (int)((y - SIZE / 2)), null); // west
            g.drawImage(_tiledImage.GetTile(3, strength), (int)((x - SIZE / 2) + (Radius * SIZE)), (int)((y - SIZE / 2)), null); // east
            g.drawImage(_tiledImage.GetTile(5, strength), (int)((x - SIZE / 2)), (int)((y - SIZE / 2) - (Radius * SIZE)), null); // north
            g.drawImage(_tiledImage.GetTile(7, strength), (int)((x - SIZE / 2)), (int)((y - SIZE / 2) + (Radius * SIZE)), null); // south 
        }
    }
}
