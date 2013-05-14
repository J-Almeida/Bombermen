package gui.swing;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import logic.Bomb;

public class SwingBomb extends Bomb implements IDraw
{
    TiledImage _explosionTile;
    TiledImage _bombTile;

    public static final int EXPLOSION_SIZE = 40;
    public static final int EXPLOSION_REAL_SIZE = 16;

    public static final int BOMB_SIZE_WIDTH = 40;
    public static final int BOMB_SIZE_HEIGHT = 38;
    
    public static final int BOMB_SIZE_REAL_WIDTH = 19; 
    public static final int BOMB_SIZE_REAL_HEIGHT = 17;
    
    public SwingBomb(int guid, Point2D position, int playerOwnerId, int radius, int strength, int time)
    {
        super(guid, position, playerOwnerId, radius, strength, time);

        _explosionTile = new TiledImage("gui/swing/resources/explosion.png", EXPLOSION_REAL_SIZE, EXPLOSION_REAL_SIZE);
        _bombTile = new TiledImage("gui/swing/resources/bomb.png", BOMB_SIZE_REAL_WIDTH, BOMB_SIZE_REAL_HEIGHT);
    }
    
    int strength = 0;

    @Override
    public void Draw(Graphics2D g)
    {
        double x = Position.getX();
        double y = Position.getY();
        
        if (!_shouldExplode)
            g.drawImage(_bombTile.GetTile(0, 0), (int)(x - BOMB_SIZE_WIDTH / 2), (int)(y - BOMB_SIZE_HEIGHT / 2), BOMB_SIZE_WIDTH, BOMB_SIZE_HEIGHT, null);
        else
        {
            strength = (strength + 1) % 5;
        
            // center
            g.drawImage(_explosionTile.GetTile(2, strength), (int)(x - EXPLOSION_SIZE / 2), (int)(y - EXPLOSION_SIZE / 2), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // middle
        
            // body
            for (int i = 1; i < Radius; ++i)
            {
                g.drawImage(_explosionTile.GetTile(1, strength), (int)((x - EXPLOSION_SIZE / 2) - (i * EXPLOSION_SIZE)), (int)((y - EXPLOSION_SIZE / 2)), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // west
                g.drawImage(_explosionTile.GetTile(4, strength), (int)((x - EXPLOSION_SIZE / 2) + (i * EXPLOSION_SIZE)), (int)((y - EXPLOSION_SIZE / 2)), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // east
                g.drawImage(_explosionTile.GetTile(6, strength), (int)((x - EXPLOSION_SIZE / 2)), (int)((y - EXPLOSION_SIZE / 2) - (i * EXPLOSION_SIZE)), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // north
                g.drawImage(_explosionTile.GetTile(8, strength), (int)((x - EXPLOSION_SIZE / 2)), (int)((y - EXPLOSION_SIZE / 2) + (i * EXPLOSION_SIZE)), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // south 
            }
            
            // tip
            g.drawImage(_explosionTile.GetTile(0, strength), (int)((x - EXPLOSION_SIZE / 2) - (Radius * EXPLOSION_SIZE)), (int)((y - EXPLOSION_SIZE / 2)), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // west
            g.drawImage(_explosionTile.GetTile(3, strength), (int)((x - EXPLOSION_SIZE / 2) + (Radius * EXPLOSION_SIZE)), (int)((y - EXPLOSION_SIZE / 2)), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // east
            g.drawImage(_explosionTile.GetTile(5, strength), (int)((x - EXPLOSION_SIZE / 2)), (int)((y - EXPLOSION_SIZE / 2) - (Radius * EXPLOSION_SIZE)), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // north
            g.drawImage(_explosionTile.GetTile(7, strength), (int)((x - EXPLOSION_SIZE / 2)), (int)((y - EXPLOSION_SIZE / 2) + (Radius * EXPLOSION_SIZE)), EXPLOSION_SIZE, EXPLOSION_SIZE, null); // south 
            //_shouldExplode = false;
        }
    }

    @Override
    public Rectangle2D GetBoundingBox()
    {
        return new Rectangle2D.Double(Position.getX() - BOMB_SIZE_WIDTH / 2, Position.getY() - BOMB_SIZE_HEIGHT / 2, BOMB_SIZE_WIDTH, BOMB_SIZE_HEIGHT);
    }
}
