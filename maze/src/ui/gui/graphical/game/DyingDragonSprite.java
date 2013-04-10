package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Dragon;
import model.Position;

/**
 * AnimatedSprite for dragons that die.
 */
public class DyingDragonSprite implements AnimatedSprite
{
    /**
     * Instantiates a new dying dragon sprite.
     *
     * @param u the dragon
     * @param sprite the sprite
     */
    public DyingDragonSprite(Dragon u, TiledImage sprite)
    {
        _dirMap.put(Direction.North, 0);
        _dirMap.put(Direction.South, 1);
        _dirMap.put(Direction.East, 2);
        _dirMap.put(Direction.West, 3);

        _unit = u;
        _sprite = sprite;
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#Update(int)
     */
    @Override
    public void Update(int diff)
    {
        _timeCount++;

        if (_playing && _timeCount == 3)
        {
            _frame += sprState.GetDeltaX();
            if (_frame == sprState.GetNumFrames() - 1)
                _playing = false;

            _timeCount = 0;
        }

    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetCurrentImage()
     */
    @Override
    public BufferedImage GetCurrentImage()
    {
        return _sprite.GetTile(_frame, _dirMap.get(_unit.GetDirection()));
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
     * @see ui.gui.graphical.game.AnimatedSprite#GetDeltaPosition(int, int)
     */
    @Override
    public Position GetDeltaPosition(int cell_width, int cell_height)
    {
        return new Position(0, 0);
    }

    /** The Constant sprite state. */
    private static final SpriteState sprState = new SpriteState(11, 0, 0, 1, 0);

    /** The frame. */
    private int _frame = 0;

    /** The time count. */
    private int _timeCount = 0;

    /** If is playing. */
    private boolean _playing = true;

    /** The sprite. */
    private final TiledImage _sprite;

    /** The unit. */
    private final Dragon _unit;

    /** The direction map. */
    private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#IsAlive()
     */
    @Override
    public boolean IsAlive()
    {
        return true;
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
