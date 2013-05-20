package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import logic.Wall;

public class SwingWall extends Wall implements IDraw
{
    private TiledImage _tileWall;

    public final static int SIZE_REAL = 16;
    public final static int SIZE = 20;

    public SwingWall(int guid, Point pos, int hitpoints)
    {
        super(guid, pos, hitpoints);

        _tileWall = new TiledImage("gui/swing/resources/wall.png", SIZE_REAL, SIZE_REAL);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        int row = Position.x;
        int col = Position.y;

        g.drawImage(_tileWall.GetTile(IsUndestroyable() ? 0 : 1, 0), row * 20, col * 20, SIZE, SIZE, null);
    }
}
