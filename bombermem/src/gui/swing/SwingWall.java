package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

public class SwingWall implements ClientWorldObject
{
    private TiledImage _tileWall;

    public final static int SIZE_REAL = 16;
    public final static int SIZE = 20;

    public SwingWall(network.NetWall wall)
    {
        _wall = wall;

        _tileWall = new TiledImage("gui/swing/resources/wall.png", SIZE_REAL, SIZE_REAL);
    }

    @Override
    public void Draw(Graphics2D g)
    {
    	Point pos = _wall.GetPosition();
    	
        int row = pos.x;
        int col = pos.y;

        g.drawImage(_tileWall.GetTile(_wall.IsUndestroyable() ? 0 : 1, 0), row * 20, col * 20, SIZE, SIZE, null);
    }
    
    private network.NetWall _wall;

	@Override
	public int GetGuid()
	{
		return _wall.GetGuid();
	}

	@Override
	public Point GetPosition()
	{
		return _wall.GetPosition();
	}
}
