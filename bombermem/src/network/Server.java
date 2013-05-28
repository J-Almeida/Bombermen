package network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import logic.UnitEventEntry;
import logic.WorldObject;
import logic.WorldObjectBuilder;
import logic.events.Event;
import model.QuadTree;
import utils.Key;

public class Server extends UnicastRemoteObject implements ServerInterface
{
	private static Server _instance = null; 
	public static Server GetInstance()
	{
		if (_instance == null)
		{
			try {
				_instance = new Server();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.println("An error has occured.");
				/*e.printStackTrace();*/
			}
		}
		
		return _instance;
	}
	
	private static final long serialVersionUID = 1L;
	private HashMap<String, ClientInterface> _clients = new HashMap<String, ClientInterface>();
	
    protected Map<Integer, WorldObject> _entities = new HashMap<Integer, WorldObject>();
    protected Map<Key, Boolean> _pressedKeys = new HashMap<Key, Boolean>();
    protected QuadTree<WorldObject> _quadTree;
    protected int _currentPlayerId = 0;
    protected WorldObjectBuilder _objectBuilder;
    protected Queue<UnitEventEntry> _eventQueue = new LinkedList<UnitEventEntry>();

	private Server() throws RemoteException 
	{
		super();
	}

	@Override
	public boolean Join(String user, ClientInterface client) throws RemoteException {
		if (_clients.containsKey(user))
			return false;
		
		_clients.put(user, client);
		
		NotifyOthers(user, user + " joined");
		
		for (String name : _clients.keySet())
			if (!name.equals(user))
				client.Notify(name + " is logged in");
		
		for (WorldObject wo : _entities.values())
			client.CreateWorldObject(wo);
		
		return true;
	}
	
	private void NotifyOthers(String source, String message) throws RemoteException
	{
		for (String name : _clients.keySet())
			if (!name.equals(source))
				_clients.get(name).Notify(message);
	}

	@Override
	public void PushEvent(UnitEventEntry uev) throws RemoteException
	{
		_eventQueue.add(uev);
	}
	
	public void PushEvent(WorldObject src, WorldObject dest, Event ev)
	{
		_eventQueue.add(new UnitEventEntry(src.GetGuid(), dest.GetGuid(), ev));
	}
	
	public void Update(int diff) 
	{
		while (!_eventQueue.isEmpty())
		{ }
		
		_quadTree.Clear();
		for (WorldObject wo : _entities.values())
		{
			wo.Update(_quadTree, diff);
			_quadTree.Insert(wo);
		}
		
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (WorldObject wo : _entities.values())
		{
			if (!wo.IsAlive())
				toRemove.add(wo.Guid);
		}
		
		for (Integer i : toRemove)
		{
			for (ClientInterface ci : _clients.values())
				ci.DestroyWorldObject(i);
			_entities.remove(i);
		}
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException
	{		
		Server sv = Server.GetInstance();
		
		Naming.rebind("rmi://localhost:20001/Bombermen", (ServerInterface)sv);
		
        boolean done = false;
        long millis = System.currentTimeMillis();
        while (!done)
        {
            int dt = (int) (System.currentTimeMillis() - millis);
            millis = System.currentTimeMillis();

            sv.Update(dt);

            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
		
	}
}
