package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote
{
	public void CreateBomb(network.NetBomb b) throws RemoteException;
	public void CreatePlayer(network.NetPlayer b) throws RemoteException;
	public void CreatePowerUp(network.NetPowerUp b) throws RemoteException;
	public void CreateWall(network.NetWall b) throws RemoteException;
	public void CreateWorldObject(NetWorldObject b) throws RemoteException;
	public void DestroyWorldObject(int guid) throws RemoteException;
	public void Notify(String s) throws RemoteException; 
}
