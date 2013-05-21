package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import logic.UnitEventEntry;

public interface ServerInterface extends Remote
{
	public void SendEvent(UnitEventEntry uev) throws RemoteException;
	public boolean Join(String name) throws RemoteException;
	// public boolean Spectate() throws RemoteException;
}
