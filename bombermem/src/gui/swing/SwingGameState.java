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

import utils.Key;

import logic.WorldObject;
import logic.GameState;

public class SwingGameState extends GameState implements IDraw, KeyListener
{
    public SwingGameState()
    {
        _objectBuilder = new SwingWorldObjectBuilder();
        _objectBuilder.SetGameState(this);

        _objectBuilder.CreatePlayer("test", new Point(1, 1));

        for (int x = 0; x < 50; ++x)
            for (int y = 0; y < 50; ++y)
                if (x == 0 || x == 47 || y == 0 || y == 47 || (x % 2 == 0 && y % 2 == 0))
                    _objectBuilder.CreateWall(-1, new Point(x, y));

        Map<Integer, Point> map = new HashMap<Integer, Point>();

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

            map.put(hash, new Point(x, y));
        }

        for (Point p : map.values())
            _objectBuilder.CreateWall(1, p);
    }

    private static final Color BACKGROUND_COLOR = new Color(16, 120,48); // dark green

    @Override
    public void Draw(Graphics2D g)
    {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);

        synchronized (_quadTree)
        {
            List<WorldObject> objs = _quadTree.QueryRange(new Rectangle(0, 0, 50, 50));

            for (WorldObject wo : objs)
                ((IDraw)wo).Draw(g);
        }
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
}
