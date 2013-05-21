package network;

import utils.Direction;

public interface NetBomb extends NetWorldObject 
{
	public int GetPlayerOwnerId();
	public int GetTime();
	public int GetStrength();
	public int GetMaxRadius();
	public int GetRadius(Direction d);
	public int GetBombTimer();
	public int GetExplosionTimer();
	public boolean IsExplosionEnded();
	public boolean ShouldExplode();
}
