package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

public class SwingPlayer implements ClientWorldObject
{
    private TiledImage _tiledImage;

    public final static int SIZE_WIDTH = 14;
    public final static int SIZE_HEIGHT = 20;
    public final static int SIZE_REAL_WIDTH = 18;
    public final static int SIZE_REAL_HEIGHT = 26;

    public SwingPlayer(network.NetPlayer pl)
    {
        _player = pl;

        _tiledImage = new TiledImage("gui/swing/resources/bomberman.png", SIZE_REAL_WIDTH, SIZE_REAL_HEIGHT);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        int tile = -1;

        switch (_player.GetDirection())
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

        Point pos = _player.GetPosition();
        int row = pos.x;
        int col = pos.y;

        g.drawImage(_tiledImage.GetTile(tile, 0), row * 20, col * 20, SIZE_WIDTH, SIZE_HEIGHT, null);
    }

    private network.NetPlayer _player;
    
	@Override
	public int GetGuid()
	{
		return _player.GetGuid();
	}

	@Override
	public Point GetPosition()
	{
		return _player.GetPosition();
	}
}
