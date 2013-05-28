package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import utils.Direction;
import logic.WorldObjectType;
import logic.events.Event;

public class SwingPlayer extends SwingWorldObject
{
    private TiledImage _tiledImage;

    public final static int SIZE_WIDTH = 14;
    public final static int SIZE_HEIGHT = 20;
    public final static int SIZE_REAL_WIDTH = 18;
    public final static int SIZE_REAL_HEIGHT = 26;

    public SwingPlayer(int guid, Point pos, Direction dir)
    {
    	super(guid, WorldObjectType.Player, pos, dir);

        _tiledImage = new TiledImage("gui/swing/resources/bomberman.png", SIZE_REAL_WIDTH, SIZE_REAL_HEIGHT);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        int tile = -1;

        switch (this.Dir)
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

        int row = Position.x;
        int col = Position.y;

        g.drawImage(_tiledImage.GetTile(tile, 0), row * 20, col * 20, SIZE_WIDTH, SIZE_HEIGHT, null);
    }

	@Override
	public void Handle(Event ev) {
		// TODO Auto-generated method stub
		
	}
}
