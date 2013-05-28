package network;

import java.rmi.Remote;

public interface ClientInterface extends Remote
{
	public void CreateBomb(network.NetBomb b);
	public void CreatePlayer(network.NetPlayer b);
	public void CreatePowerUp(network.NetPowerUp b);
	public void CreateWall(network.NetWall b);
	public void CreateWorldObject(NetWorldObject b);
	public void DestroyWorldObject(int guid);
	public void Notify(String s); 
}
