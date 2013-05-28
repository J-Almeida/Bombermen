package gui.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.IState;
import model.QuadTree;
import network.ClientInterface;
import network.NetBomb;
import network.NetPlayer;
import network.NetPowerUp;
import network.NetWall;
import network.NetWorldObject;
import network.ServerInterface;
import utils.Key;

public class SwingGameState implements IDraw, IState, KeyListener, ClientInterface, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected Map<Integer, ClientWorldObject> _entities = new HashMap<Integer, ClientWorldObject>();
    protected Map<Key, Boolean> _pressedKeys = new HashMap<Key, Boolean>();
    protected QuadTree<ClientWorldObject> _quadTree = new QuadTree<ClientWorldObject>(new Rectangle(0, 0, 50, 50));
    protected ServerInterface _server = null;
    public final String UserName;
    
    public SwingGameState(String userName, String url) throws RemoteException, NotBoundException
    {
    	try 
    	{
			_server = (ServerInterface)Naming.lookup(url + "/Bombermen");
		} 
    	catch (MalformedURLException e) 
    	{ 
			e.printStackTrace();
		}
    	catch ( RemoteException | NotBoundException e) 
    	{
			throw e;
		}
    	
    	UserName = userName;
    	
		try {
			_server.Join(UserName, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
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
        
        for (ClientWorldObject obj : _entities.values())
        	_quadTree.Insert(obj);
        
        List<ClientWorldObject> objs = _quadTree.QueryRange(new Rectangle(0, 0, 50, 50));

        System.out.println("Number of objects: " + objs.size());
        
        for (ClientWorldObject wo : objs)
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
	public void CreateBomb(NetBomb b) throws RemoteException
	{
		_entities.put(b.GetGuid(), new SwingBomb(b));
	}

	@Override
	public void CreatePlayer(NetPlayer p) throws RemoteException 
	{
		_entities.put(p.GetGuid(), new SwingPlayer(p));
		
	}

	@Override
	public void CreatePowerUp(NetPowerUp pu)  throws RemoteException
	{
		_entities.put(pu.GetGuid(), new SwingPowerUp(pu));
	}

	@Override
	public void CreateWall(NetWall w) throws RemoteException 
	{
		_entities.put(w.GetGuid(), new SwingWall(w));
		System.out.println("New Wall: " + w.GetGuid() + ". Number Of Entities: " + _entities.size());
	}

	@Override
	public void Initialize() {

	}

	@Override
	public void LoadContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update(int diff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UnloadContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Notify(String s) {
		System.out.println(s);
		
	}

	@Override
	public void CreateWorldObject(NetWorldObject b) throws RemoteException
	{
		switch (b.GetType())
		{
		case Bomb:
			CreateBomb((NetBomb)b);
			break;
		case Player:
			CreatePlayer((NetPlayer)b);
			break;
		case PowerUp:
			CreatePowerUp((NetPowerUp)b);
			break;
		case Wall:
			CreateWall((NetWall)b);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void DestroyWorldObject(int guid) {
		// TODO Auto-generated method stub
		
	}
}
