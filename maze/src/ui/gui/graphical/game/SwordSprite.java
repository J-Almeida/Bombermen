package ui.gui.graphical.game;

import java.awt.image.BufferedImage;

import logic.Sword;
import model.Position;

/**
 * The Class SwordSprite.
 */
public class SwordSprite implements AnimatedSprite
{
    /**
     * Instantiates a new sword sprite.
     *
     * @param u the sword
     * @param sprite the sprite
     */
    public SwordSprite(Sword u, TiledImage sprite)
    {
        _sprite = sprite;
        _unit = u;
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#Update(int)
     */
    @Override
    public void Update(int diff) { }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetCurrentImage()
     */
    @Override
    public BufferedImage GetCurrentImage()
    {
        return _sprite.GetBufferedImage();
    }

    /** The sprite. */
    private final TiledImage _sprite;

    /** The unit. */
    private final Sword _unit;

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetDeltaPosition(int, int)
     */
    @Override
    public Position GetDeltaPosition(int cell_width, int cell_height)
    {
        return new Position(0,0);
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetPosition()
     */
    @Override
    public Position GetPosition()
    {
        return _unit.GetPosition();
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#IsAlive()
     */
    @Override
    public boolean IsAlive()
    {
        return _unit.IsAlive();
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetUnitId()
     */
    @Override
    public int GetUnitId()
    {
        return _unit.GetId();
    }
}
