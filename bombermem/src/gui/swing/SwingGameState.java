package gui.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.QuadTree;
import network.ClientInterface;
import network.NetBomb;
import network.NetPlayer;
import network.NetPowerUp;
import network.NetWall;
import network.NetWorldObject;
import utils.Key;
import logic.WorldObject;
import logic.GameState;

public class SwingGameState implements IDraw, KeyListener, ClientInterface
{
	protected Map<Integer, ClientWorldObject> _entities = new HashMap<Integer, ClientWorldObject>();
    protected Map<Key, Boolean> _pressedKeys = new HashMap<Key, Boolean>();
    protected QuadTree _quadTree = new QuadTree(new Rectangle(0, 0, 50, 50));
    
    public SwingGameState()
    {
    }

    private static final Color BACKGROUND_COLOR = new Color(16, 120,48); // dark green

    @Override
    public void Draw(Graphics2D g)
    {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);

        for (ClientWorldObject obj : _entities.values())
        	_quadTree.Insert(obj);
        
        List<ClientWorldObject> objs = _quadTree.QueryRange(new Rectangle(0, 0, 50, 50));

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
	public void CreateBomb(NetBomb b)
	{
		_entities.put(b.GetGuid(), new SwingBomb(b));
	}

	@Override
	public void CreatePlayer(NetPlayer p) 
	{
		_entities.put(p.GetGuid(), new SwingPlayer(p));
		
	}

	@Override
	public void CreatePowerUp(NetPowerUp pu) 
	{
		_entities.put(pu.GetGuid(), new SwingPowerUp(pu));
	}

	@Override
	public void CreateWall(NetWall w) 
	{
		_entities.put(w.GetGuid(), new SwingWall(w));
	}
}
