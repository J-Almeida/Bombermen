package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import logic.UnitEventEntry;

public interface ServerInterface extends Remote
{
	public void PushEvent(UnitEventEntry uev) throws RemoteException;
	public boolean Join(String name, ClientInterface client) throws RemoteException;
	// public boolean Spectate() throws RemoteException;
}
