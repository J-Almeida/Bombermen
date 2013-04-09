package ui.gui.graphical.game;

import java.awt.image.BufferedImage;

import model.Position;

/**
 * The Interface AnimatedSprite.
 */
public interface AnimatedSprite
{
    /**
     * Update.
     *
     * @param diff the difference between this Update and last call to Update
     */
    public abstract void Update(int diff);

    /**
     * Gets the current image.
     *
     * @return the buffered image
     */
    public abstract BufferedImage GetCurrentImage();

    /**
     * Gets the position.
     *
     * @return the position
     */
    public abstract Position GetPosition();

    /**
     * Gets the delta position.
     *
     * @param cell_width the cell width
     * @param cell_height the cell height
     * @return the position
     */
    public abstract Position GetDeltaPosition(int cell_width, int cell_height);

    /**
     * Checks if is alive.
     *
     * @return true, if successful
     */
    public abstract boolean IsAlive();

    /**
     * Gets the unit id.
     *
     * @return the int
     */
    public abstract int GetUnitId();
}
