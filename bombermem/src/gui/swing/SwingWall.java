package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import logic.WorldObjectType;
import logic.events.Event;
import utils.Direction;

public class SwingWall extends SwingWorldObject
{
    private TiledImage _tileWall;

    public final static int SIZE_REAL = 16;
    public final static int SIZE = 20;
    
    public final boolean IsUndestroyable;
    
    public SwingWall(int guid, Point pos, Direction dir, boolean undestroyable)
    {
    	super(guid, WorldObjectType.Wall, pos, dir);

    	IsUndestroyable = undestroyable;
        _tileWall = new TiledImage("gui/swing/resources/wall.png", SIZE_REAL, SIZE_REAL);
    }

    @Override
    public void Draw(Graphics2D g)
    {   	
        int row = Position.x;
        int col = Position.y;

        g.drawImage(_tileWall.GetTile(IsUndestroyable ? 0 : 1, 0), row * 20, col * 20, SIZE, SIZE, null);
    }

	@Override
	public void Handle(Event ev) {
		// TODO Auto-generated method stub
		
	}
}
