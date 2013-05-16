package gui.swing;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import utils.Key;

import logic.WorldObject;
import logic.GameState;

public class SwingGameState extends GameState implements IDraw, KeyListener
{
    Map<Integer, IDraw> _drawableEntities;
    
    private static SwingGameState _instance;
    public static SwingGameState GetInstance()
    {
        if (_instance == null)
            _instance = new SwingGameState();
        return _instance;
    }

    private SwingGameState()
    {
        _drawableEntities = new HashMap<Integer, IDraw>();
        _entities = new HashMap<Integer, WorldObject>();
        
        _objectBuilder = new SwingWorldObjectBuilder();
        _objectBuilder.SetGameState(this);
        
        _objectBuilder.CreatePlayer("test", new Point2D.Double(56, 60));

        for (int x = 0; x < 50; ++x)
        {
            for (int y = 0; y < 50; ++y)
            {
                if (x == 0 || x == 47 || y == 0 || y == 47 || (x % 2 == 0 && y % 2 == 0))
                    _objectBuilder.CreateWall(-1, new Point2D.Double(x * SwingWall.SIZE  + SwingWall.SIZE / 2, y * SwingWall.SIZE  + SwingWall.SIZE / 2));
            }
        }
        
        class Pair
        {
            int X;
            int Y;
        }
        
        Map<Integer, Pair> map = new HashMap<Integer, Pair>();
        
        Random r = new Random();
        
        while (map.size() != 500)
        {
            int x = r.nextInt(50);
            int y = r.nextInt(50);
            
            int hash = (int)(1.0/2.0 * (x + y) * (x + y + 1) + y);
            if (map.containsKey(hash))
                continue;
            
            if (x == 0 || y == 0)
                continue;
            
            if ((x % 2) == 0 && (y % 2) == 0)
                continue;
            
            if ((x == 1 && y == 1) || ((x == 1) && (y == 2)) || ((x == 2) && (y == 1)))
                continue;
            
            Pair p = new Pair();
            p.X = x;
            p.Y = y;
            map.put(hash, p);
        }
        
        for (Pair p : map.values())
        {
            _objectBuilder.CreateWall(1, new Point2D.Double(p.X * SwingWall.SIZE  + SwingWall.SIZE / 2, p.Y * SwingWall.SIZE  + SwingWall.SIZE / 2));
        }
    }
    
    @Override
    public void AddEntity(WorldObject entity)
    {
        _drawableEntities.put(entity.Guid, (IDraw)entity);
        _entities.put(entity.Guid, entity);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        List<WorldObject> objs = _quadTree.QueryRange(new Rectangle(0, 0, 800, 600));
        
        for (WorldObject wo : objs)
            _drawableEntities.get(wo.Guid).Draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

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
}
