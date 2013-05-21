package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import utils.Direction;

public class SwingBomb implements ClientWorldObject
{
    TiledImage _explosionTile;
    TiledImage _bombTile;

    public static final int EXPLOSION_SIZE = 20;
    public static final int EXPLOSION_REAL_SIZE = 16;

    public static final int BOMB_SIZE_WIDTH = 20;
    public static final int BOMB_SIZE_HEIGHT = 19;

    public static final int BOMB_SIZE_REAL_WIDTH = 18;
    public static final int BOMB_SIZE_REAL_HEIGHT = 17;

    public SwingBomb(network.NetBomb bomb)
    {
    	_bomb = bomb;
    	
    	_explosionTile = new TiledImage("gui/swing/resources/explosion.png", EXPLOSION_REAL_SIZE, EXPLOSION_REAL_SIZE);
        _bombTile = new TiledImage("gui/swing/resources/bomb.png", BOMB_SIZE_REAL_WIDTH, BOMB_SIZE_REAL_HEIGHT);
    }

    @Override
    public void Draw(Graphics2D g)
    {
    	Point pos = _bomb.GetPosition();
        int col = pos.x;
        int row = pos.y;

        boolean shouldExplode = _bomb.ShouldExplode();
        
        if (!shouldExplode)
        {
            int bombCol = 0;
            int timer = _bomb.GetBombTimer();
            
            if (timer % 1000.0 > (1000.0 / 5) * 4)
                bombCol = 1;
            else if (timer % 1000.0 > (1000.0 / 5) * 5)
                bombCol = 0;
            else if (timer % 1000.0 > (1000.0 / 5) * 3)
                bombCol = 2;
            else if (timer % 1000.0 > (1000.0 / 5) * 2)
                bombCol = 1;
            else if (timer % 1000.0 > (1000.0 / 5) * 1)
                bombCol = 0;

            g.drawImage(_bombTile.GetTile(bombCol, 0), col * 20, row * 20, BOMB_SIZE_WIDTH, BOMB_SIZE_HEIGHT, null);
        }

        if (shouldExplode && !_bomb.IsExplosionEnded())
        {        	
            int strength;
            int timer = _bomb.GetExplosionTimer();
            if (timer < 67)
                strength = 0;
            else if (timer < 134)
                strength = 1;
            else if (timer < 200)
                strength = 2;
            else if (timer < 267)
                strength = 3;
            else if (timer < 334)
                strength = 4;
            else if (timer < 400)
                strength = 3;
            else if (timer < 467)
                strength = 2;
            else if (timer < 534)
                strength = 1;
            else
                strength = 0;

            // center
            g.drawImage(_explosionTile.GetTile(2, strength), col * 20, row * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null); // middle

            int[] radius = new int[4];
            for (utils.Direction d: utils.Direction.values())
            	radius[d.Index] = _bomb.GetRadius(d);
            
            // body & tip
            for (int i = 1; i <= radius[Direction.West.Index]; ++i)
                g.drawImage(_explosionTile.GetTile(i == radius[Direction.West.Index] ? 0 : 1, strength), (col - i) * 20, row * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null);

            for (int i = 1; i <= radius[Direction.East.Index]; ++i)
                g.drawImage(_explosionTile.GetTile(i == radius[Direction.East.Index] ? 3 : 4, strength), (col + i) * 20, row * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null);

            for (int i = 1; i <= radius[Direction.North.Index]; ++i)
                g.drawImage(_explosionTile.GetTile(i == radius[Direction.North.Index] ? 5 : 6, strength), col * 20, (row - i) * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null);

            for (int i = 1; i <= radius[Direction.South.Index]; ++i)
                g.drawImage(_explosionTile.GetTile(i == radius[Direction.South.Index] ? 7 : 8, strength), col * 20, (row + i) * 20, EXPLOSION_SIZE, EXPLOSION_SIZE, null);
        }
    }

    private network.NetBomb _bomb;

	@Override
	public int GetGuid()
	{
		return _bomb.GetGuid();
	}

	@Override
	public Point GetPosition()
	{
		return _bomb.GetPosition();
	}
}
