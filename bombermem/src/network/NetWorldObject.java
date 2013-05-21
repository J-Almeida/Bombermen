package network;

import java.awt.Point;
import java.rmi.Remote;

import logic.WorldObjectType;
import utils.Direction;

public interface NetWorldObject extends Remote
{
	public int GetGuid();
	public Point GetPosition();
	public Direction GetDirection();
	public WorldObjectType GetType();
	
	public boolean IsAlive();
}
