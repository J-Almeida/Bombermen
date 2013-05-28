package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import logic.WorldObjectType;
import logic.events.Event;
import utils.Direction;

public class SwingBomb extends SwingWorldObject
{
    TiledImage _explosionTile;
    TiledImage _bombTile;

    public static final int EXPLOSION_SIZE = 20;
    public static final int EXPLOSION_REAL_SIZE = 16;

    public static final int BOMB_SIZE_WIDTH = 20;
    public static final int BOMB_SIZE_HEIGHT = 19;

    public static final int BOMB_SIZE_REAL_WIDTH = 18;
    public static final int BOMB_SIZE_REAL_HEIGHT = 17;

    public SwingBomb(int guid, Point pos, Direction dir)
    {
    	super(guid, WorldObjectType.Bomb, pos, dir);
    	
    	_explosionTile = new TiledImage("gui/swing/resources/explosion.png", EXPLOSION_REAL_SIZE, EXPLOSION_REAL_SIZE);
        _bombTile = new TiledImage("gui/swing/resources/bomb.png", BOMB_SIZE_REAL_WIDTH, BOMB_SIZE_REAL_HEIGHT);
    }

    private boolean _shouldExplode = false;
    private int _radius[] = { 0, 0, 0, 0 };
    private int _timer = 0;
    
    @Override
    public void Draw(Graphics2D g)
    {
        int col = Position.x;
        int row = Position.y;
        
        if (!_shouldExplode)
        {
            int bombCol = 0;
            
            if (_timer % 1000.0 > (1000.0 / 5) * 4)
                bombCol = 1;
            else if (_timer % 1000.0 > (1000.0 / 5) * 5)
                bombCol = 0;
            else if (_timer % 1000.0 > (1000.0 / 5) * 3)
                bombCol = 2;
            else if (_timer % 1000.0 > (1000.0 / 5) * 2)
                bombCol = 1;
            else if (_timer % 1000.0 > (1000.0 / 5) * 1)
                bombCol = 0;

            g.drawImage(_bombTile.GetTile(bombCol, 0), col * 20, row * 20, BOMB_SIZE_WIDTH, BOMB_SIZE_HEIGHT, null);
        }

        if (_shouldExplode && _timer < 600)
        {        	
            int strength;
            
            if (_timer < 67)
                strength = 0;
            else if (_timer < 134)
                strength = 1;
            else if (_timer < 200)
                strength = 2;
            else if (_timer < 267)
                strength = 3;
            else if (_timer < 334)
                strength = 4;
            else if (_timer < 400)
                strength = 3;
            else if (_timer < 467)
                strength = 2;
            else if (_timer < 534)
                strength = 1;
            else
                strength = 0;

            // center
            g.drawImage(_explosionTile.GetTile(2, strength), col * 20, row * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null); // middle
            
            // body & tip
            for (int i = 1; i <= _radius[Direction.West.Index]; ++i)
                g.drawImage(_explosionTile.GetTile(i == _radius[Direction.West.Index] ? 0 : 1, strength), (col - i) * 20, row * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null);

            for (int i = 1; i <= _radius[Direction.East.Index]; ++i)
                g.drawImage(_explosionTile.GetTile(i == _radius[Direction.East.Index] ? 3 : 4, strength), (col + i) * 20, row * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null);

            for (int i = 1; i <= _radius[Direction.North.Index]; ++i)
                g.drawImage(_explosionTile.GetTile(i == _radius[Direction.North.Index] ? 5 : 6, strength), col * 20, (row - i) * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null);

            for (int i = 1; i <= _radius[Direction.South.Index]; ++i)
                g.drawImage(_explosionTile.GetTile(i == _radius[Direction.South.Index] ? 7 : 8, strength), col * 20, (row + i) * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null);
        }
    }

	@Override
	public void Handle(Event ev) 
	{
		// TODO Auto-generated method stub
		
	}
}
