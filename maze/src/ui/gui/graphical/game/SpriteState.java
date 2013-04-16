package ui.gui.graphical.game;

import java.io.Serializable;

/**
 * Represents the animation state of a Sprite
 */
public class SpriteState implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new sprite state.
     *
     * @param nF frame count
     * @param cI initial column
     * @param lI initial line
     * @param dX delta x
     * @param dY delta y
     */
    public SpriteState(int nF, int cI, int lI, int dX, int dY)
    {
        _numFrames = nF;
        _colInit   = cI;
        _lineInit  = lI;
        _deltaX    = dX;
        _deltaY    = dY;
    }

    /**
     * Gets the number of frames.
     *
     * @return the count
     */
    public int GetNumFrames() { return _numFrames; }

    /**
     * Gets the initial column.
     *
     * @return the column
     */
    public int GetInitialColumn() { return _colInit; }

    /**
     * Gets the initial line.
     *
     * @return the line
     */
    public int GetInitialLine() { return _lineInit; }

    /**
     * Gets the delta x.
     *
     * @return the x
     */
    public int GetDeltaX() { return _deltaX; }

    /**
     * Gets the delta y.
     *
     * @return the y
     */
    public int GetDeltaY() { return _deltaY; }

    /** The number of frames. */
    private final int _numFrames;

    /** The initial column. */
    private final int _colInit;

    /** The initial line. */
    private final int _lineInit;

    /** The delta x. */
    private final int _deltaX;

    /** The delta y. */
    private final int _deltaY;
}
