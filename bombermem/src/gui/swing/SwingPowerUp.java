package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import logic.WorldObjectType;
import logic.events.Event;
import utils.Direction;

public class SwingPowerUp extends SwingWorldObject
{
    public SwingPowerUp(int guid, Point pos, Direction dir)
    {
    	super(guid, WorldObjectType.PowerUp, pos, dir);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        // TODO Auto-generated method stub

    }

	@Override
	public void Handle(Event ev) {
		// TODO Auto-generated method stub
		
	}
}
