package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import logic.PowerUp;

public class SwingPowerUp extends PowerUp implements IDraw
{
    private TiledImage _tiledImage;

    public final static int SIZE = 20;
    public final static int SIZE_REAL = 16;

    public SwingPowerUp(int guid, Point pos, PowerUpType type)
    {
        super(guid, pos, type);

        _tiledImage = new TiledImage("gui/swing/resources/powerup.png", SIZE_REAL, SIZE_REAL);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        int row = Position.x;
        int col = Position.y;

        int rowSprite = (_timer / 350) % 2;

        g.drawImage(_tiledImage.GetTile(Type.Index, rowSprite), row * 20, col * 20, SIZE, SIZE, null);
    }
}
