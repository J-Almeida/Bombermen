package gui.swing;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Key;

import logic.WorldObject;
import logic.GameState;

public class SwingGameState extends GameState implements IDraw, KeyListener
{
    Map<Integer, IDraw> _drawableEntities;

    public SwingGameState()
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
                if (x == 0 || x == 47 || y == 0 || y == 47)
                    _objectBuilder.CreateWall(-1, new Point2D.Double(x * SwingWall.SIZE  + SwingWall.SIZE / 2, y * SwingWall.SIZE  + SwingWall.SIZE / 2));
                else if (x % 2 == 0 && y % 2 == 0)
                    _objectBuilder.CreateWall(1, new Point2D.Double(x * SwingWall.SIZE  + SwingWall.SIZE / 2, y * SwingWall.SIZE  + SwingWall.SIZE / 2));
            }
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
