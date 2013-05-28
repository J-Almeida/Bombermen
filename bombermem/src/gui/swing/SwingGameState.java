package gui.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

import logic.IState;
import logic.UnitEventEntry;
import logic.WorldObject;
import logic.WorldObjectType;
import logic.events.MovementEvent;
import logic.events.SpawnEvent;
import model.QuadTree;
import utils.Direction;
import utils.Key;

public class SwingGameState implements IDraw, IState, KeyListener, Runnable
{
	protected Map<Integer, SwingWorldObject> _entities = new HashMap<Integer, SwingWorldObject>();
    protected Map<Key, Boolean> _pressedKeys = new HashMap<Key, Boolean>();
    protected QuadTree<SwingWorldObject> _quadTree = new QuadTree<SwingWorldObject>(new Rectangle(0, 0, 50, 50));
    protected Queue<UnitEventEntry> _eventQueue = new LinkedList<UnitEventEntry>();
    protected Socket _socket;
    
    public SwingGameState(String url, int port) throws UnknownHostException, IOException
    {
    	_socket = new Socket(url, port);
    	
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
				_eventQueue.add(eve);
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
	
    private static final Color BACKGROUND_COLOR = new Color(16, 120,48); // dark green

    @Override
    public void Draw(Graphics2D g)
    {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);

        _quadTree.Clear();
        
        System.out.println("Number of entities: " + _entities.size());
        
        for (SwingWorldObject obj : _entities.values())
        	_quadTree.Insert(obj);
        
        List<SwingWorldObject> objs = _quadTree.QueryRange(new Rectangle(0, 0, 50, 50));

        System.out.println("Number of objects: " + objs.size());
        
        for (SwingWorldObject wo : objs)
            ((IDraw)wo).Draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e)
    {
        Key k = Key.ToEnum(e.getKeyCode());
        if (k == null)
            return;

        _pressedKeys.put(k, true);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        Key k = Key.ToEnum(e.getKeyCode());
        if (k == null)
            return;

        _pressedKeys.put(k, false);
    }

	@Override
	public void Initialize() 
	{
	}

	@Override
	public void LoadContents() 
	{
	}

	@Override
	public void Update(int diff) 
	{
		for (Entry<Key, Boolean> pair : _pressedKeys.entrySet())
        {
            if (!pair.getValue())
                continue;

            switch (pair.getKey())
            {
            case ESC:
                break;
            case DOWN:
            case LEFT:
            case RIGHT:
            case UP:
                Direction d = Direction.FromKey(pair.getKey());
                if (d != null)
                    SendEvent(new UnitEventEntry(0, 0, new MovementEvent(d)));
                pair.setValue(false);
                break;
            case SPACE:
            	SendEvent(new UnitEventEntry(0, 0, new SpawnEvent(WorldObjectType.Bomb)));
                pair.setValue(false);
                break;
            default:
                break;
            }
        }
		
		while (!_eventQueue.isEmpty())
		{
			UnitEventEntry eve = _eventQueue.poll();
			
	    	SwingWorldObject entityWo = _entities.get(eve.GetEntity());
	    	
	        if (entityWo == null)
	            return;

	        entityWo.Handle(eve.GetEvent());
		}
	}

	@Override
	public void UnloadContents() 
	{
	}
}
