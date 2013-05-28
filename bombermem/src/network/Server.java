package network;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import logic.GameState;
import logic.UnitEventEntry;
import logic.WorldObject;
import model.QuadTree;


public class Server extends GameState implements Runnable
{
	private static Server _instance = null; 
	public static int Port = 7777;
	
	public static Server GetInstance()
	{
		if (_instance == null)
		{
			try {
				_instance = new Server();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return _instance;
	}
	
	private ServerSocket _serverSocket;
	
	private int _lastId = 0;
	
	private HashMap<Integer, WorldObject> _entities = new HashMap<Integer, WorldObject>();
	
	private HashMap<Integer, ConnectionHandler> _clients = new HashMap<Integer, ConnectionHandler>();
	protected QuadTree<WorldObject> _quadTree = new QuadTree<WorldObject>(new Rectangle(0,0,50,50));
	private Queue<UnitEventEntry> _eventQueue = new LinkedList<UnitEventEntry>();
	
	private Server() throws IOException
	{
		_serverSocket = new ServerSocket(Port);
		
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() 
	{
		while (true)
		{
			try 
			{
				Socket clientSocket = _serverSocket.accept();
				int playerGuid = _lastId++;
				_clients.put(playerGuid, new ConnectionHandler(this, clientSocket));
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void PushEvent(UnitEventEntry eve)
	{
		_eventQueue.add(eve);
	}

	/*public void Update(int diff)
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
			// Send Kill Event To Players
			_entities.remove(i);
		}
	}*/
	
	public static void main(String[] args) throws IOException
	{
		Server sv = Server.GetInstance();
		
		boolean done = false;
		long millis = System.currentTimeMillis();
		while (!done)
		{			
		    int dt = (int) (System.currentTimeMillis() - millis);
		    System.out.println("diff = " + Integer.toString(dt));
		    
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

class ConnectionHandler implements Runnable
{
	private Socket _socket;
	private Server _server;
	
	public ConnectionHandler(Server server, Socket socket)
	{
		_server = server;
		_socket = socket;
		
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() 
	{
		while (true)
		{
			try 
			{
				ObjectInputStream ois = new ObjectInputStream(_socket.getInputStream());
				UnitEventEntry eve = (UnitEventEntry)ois.readObject();
				_server.PushEvent(eve);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void SendEvent(UnitEventEntry eve)
	{
		try 
		{
			ObjectOutputStream oos = new ObjectOutputStream(_socket.getOutputStream());
			oos.writeObject(eve);
			
			oos.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}

//public class Server
//{
//	private static Server _instance = null; 
//	public static Server GetInstance()
//	{
//		if (_instance == null)
//		{
//			try {
//				_instance = new Server();
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				System.out.println("An error has occured.");
//				/*e.printStackTrace();*/
//			}
//		}
//		
//		return _instance;
//	}
//	
//	private static final long serialVersionUID = 1L;
//	private HashMap<String, ClientInterface> _clients = new HashMap<String, ClientInterface>();
//	
//    protected Map<Integer, WorldObject> _entities = new HashMap<Integer, WorldObject>();
//    protected Map<Key, Boolean> _pressedKeys = new HashMap<Key, Boolean>();
//    protected QuadTree<WorldObject> _quadTree = new QuadTree<WorldObject>(new Rectangle(0,0,50,50));
//    protected int _currentPlayerId = 0;
//    protected WorldObjectBuilder _objectBuilder;
//    protected Queue<UnitEventEntry> _eventQueue = new LinkedList<UnitEventEntry>();
//
//    private int _lastId = 0;
//    
//    public Wall CreateWall(int hitpoints, Point position) throws RemoteException
//    {
//    	 Wall wall = new Wall(_lastId, position, hitpoints);
//
//        _entities.put(_lastId, wall);
//
//        _lastId++;
//        
//        for (ClientInterface ci : _clients.values())
//        	ci.CreateWall(wall);
//        
//         return wall;
//    }
//    
//	private Server() throws RemoteException 
//	{
//		super();
//		
//		for (int x = 0; x < 50; ++x)
//            for (int y = 0; y < 50; ++y)
//                if (x == 0 || x == 47 || y == 0 || y == 47 || (x % 2 == 0 && y % 2 == 0))
//                    CreateWall(-1, new Point(x, y));
//
//        Map<Integer, Point> map = new HashMap<Integer, Point>();
//
//        Random r = new Random();
//
//        while (map.size() != 500)
//        {
//            int x = r.nextInt(50);
//            int y = r.nextInt(50);
//
//            int hash = (int)(1.0/2.0 * (x + y) * (x + y + 1) + y);
//            if (map.containsKey(hash))
//                continue;
//
//            if (x == 0 || y == 0)
//                continue;
//
//            if ((x % 2) == 0 && (y % 2) == 0)
//                continue;
//
//            if ((x == 1 && y == 1) || ((x == 1) && (y == 2)) || ((x == 2) && (y == 1)))
//                continue;
//
//            map.put(hash, new Point(x, y));
//        }
//
//        for (Point p : map.values())
//            CreateWall(1, p);
//        
//        System.out.println("Map ready.");
//	}
//
//	@Override
//	public boolean Join(String user, ClientInterface client) throws RemoteException {
//		if (_clients.containsKey(user))
//			return false;
//		
//		_clients.put(user, client);
//		
//		System.out.println(user  + " joined");
//		
//		NotifyOthers(user, user + " joined");
//		
//		for (String name : _clients.keySet())
//			if (!name.equals(user))
//				client.Notify(name + " is logged in");
//		
//		for (WorldObject wo : _entities.values())
//			client.CreateWorldObject(wo);
//		
//		return true;
//	}
//	
//	private void NotifyOthers(String source, String message) throws RemoteException
//	{
//		for (String name : _clients.keySet())
//			if (!name.equals(source))
//				_clients.get(name).Notify(message);
//	}
//
//	@Override
//	public void PushEvent(UnitEventEntry uev) throws RemoteException
//	{
//		_eventQueue.add(uev);
//	}
//	
//	public void PushEvent(WorldObject src, WorldObject dest, Event ev)
//	{
//		_eventQueue.add(new UnitEventEntry(src.GetGuid(), dest.GetGuid(), ev));
//	}
//	
//	public void Update(int diff) throws RemoteException 
//	{
//		while (!_eventQueue.isEmpty())
//		{ }
//		
//		_quadTree.Clear();
//		for (WorldObject wo : _entities.values())
//		{
//			wo.Update(_quadTree, diff);
//			_quadTree.Insert(wo);
//		}
//		
//		ArrayList<Integer> toRemove = new ArrayList<Integer>();
//		for (WorldObject wo : _entities.values())
//		{
//			if (!wo.IsAlive())
//				toRemove.add(wo.Guid);
//		}
//		
//		for (Integer i : toRemove)
//		{
//			for (ClientInterface ci : _clients.values())
//				ci.DestroyWorldObject(i);
//			_entities.remove(i);
//		}
//	}
//	
//	public static void main(String[] args) throws RemoteException, MalformedURLException
//	{		
//		try {
//			LocateRegistry.createRegistry(1099);
//		} catch (RemoteException e1) {
//		}
//		
//		System.out.println("Registry Located.");
//		
//		Server sv = Server.GetInstance();
//		
//		Naming.rebind("rmi://localhost/Bombermen", (ServerInterface)sv);
//		
//		System.out.println("Server Running.");
//		
//        boolean done = false;
//        long millis = System.currentTimeMillis();
//        while (!done)
//        {
//            int dt = (int) (System.currentTimeMillis() - millis);
//            millis = System.currentTimeMillis();
//
//            sv.Update(dt);
//
//            try
//            {
//                Thread.sleep(20);
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }
//		
//	}
//}
