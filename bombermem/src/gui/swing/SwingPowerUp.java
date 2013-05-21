package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

public class SwingPowerUp implements ClientWorldObject
{
    public SwingPowerUp(network.NetPowerUp powerUp)
    {
        _powerUp = powerUp;
    }

    @Override
    public void Draw(Graphics2D g)
    {
        // TODO Auto-generated method stub

    }
    
    private network.NetPowerUp _powerUp;

	@Override
	public int GetGuid()
	{
		return _powerUp.GetGuid();
	}

	@Override
	public Point GetPosition()
	{
		return _powerUp.GetPosition();
	}
}
