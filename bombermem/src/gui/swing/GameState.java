package gui.swing;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class GameState extends logic.GameState implements IDraw
{
    Map<Integer, IDraw> _drawableEntities;
    
    public GameState()
    {
        _drawableEntities = new HashMap<Integer, IDraw>();
    }

    @Override
    public void Draw(Graphics2D g)
    {
        g.drawLine(0, 0, 10, 10);
        
        g.drawString("Test", 0, 10);
        
        for (IDraw d : _drawableEntities.values())
            d.Draw(g);
    }
}
