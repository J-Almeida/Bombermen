package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Hero;
import model.Position;

/**
 * The Class HeroSprite.
 */
public class HeroSprite implements AnimatedSprite
{
    /** The stopped armed frame count. */
    private static int STOPPED_ARMED_DIFF = 1;

    /** The walking armed line frame count. */
    private static int WALKING_ARMED_LINE_DIFF = 4;

    /**
     * The HeroSprite state.
     */
    private enum state
    {

        /** The stopped. */
        STOPPED(new SpriteState(1, 0, 8, 0, 2)),

        /** The walking. */
        WALKING(new SpriteState(8, 0, 0, 1, 0));

        /**
         * Instantiates a new state.
         *
         * @param s the sprite state
         */
        state(SpriteState s)
        {
            sprState = s;
        }

        /** The sprite state. */
        public final SpriteState sprState;
    }

    /**
     * Checks if is walking.
     *
     * @return true, if successful
     */
    public boolean IsWalking() { return _state == state.WALKING; }

    /**
     * Instantiates a new hero sprite.
     *
     * @param u the hero
     * @param sprite the sprite
     */
    public HeroSprite(Hero u, TiledImage sprite)
    {
        _dirMap.put(Direction.East, 0);
        _dirMap.put(Direction.North, 1);
        _dirMap.put(Direction.South, 2);
        _dirMap.put(Direction.West, 3);

        _unit = u;

        _position = _unit.GetPosition().clone();

        _sprite = sprite;
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#Update(int)
     */
    @Override
    public void Update(int diff)
    {
        _timeCount++;

        if (_state != state.WALKING && !_position.equals(_unit.GetPosition()))
            _state = state.WALKING;

        if (_timeCount == 3)
        {
            _frame += _state.sprState.GetDeltaX();
            _frame %= _state.sprState.GetNumFrames();

            if (_state == state.WALKING && _frame == 0) {
                _state = state.STOPPED;
                _position = _unit.GetPosition().clone();
            }

            _timeCount = 0;
        }
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetCurrentImage()
     */
    @Override
    public BufferedImage GetCurrentImage()
    {
        Direction unitCurrentDir = _unit.GetDirection();
        if (_state != state.STOPPED)
            return _sprite.GetTile(_frame, _state.sprState.GetInitialLine() + (_unit.IsArmed() ? WALKING_ARMED_LINE_DIFF : 0) + _dirMap.get(unitCurrentDir));
        else
        {
            switch (unitCurrentDir)
            {
            case South:
                return _sprite.GetTile(0 + (_unit.IsArmed() ? STOPPED_ARMED_DIFF : 0), _state.sprState.GetInitialLine());
            case North:
                return _sprite.GetTile(4 + (_unit.IsArmed() ? STOPPED_ARMED_DIFF : 0), _state.sprState.GetInitialLine());
            case West:
                return _sprite.GetTile(2 + (_unit.IsArmed() ? STOPPED_ARMED_DIFF : 0), _state.sprState.GetInitialLine());
            case East:
                return _sprite.GetTile(6 + (_unit.IsArmed() ? STOPPED_ARMED_DIFF : 0), _state.sprState.GetInitialLine());
            default:
                return null;
            }
        }
    }

    /** The frame. */
    private int _frame = 0;

    /** The time count. */
    private int _timeCount = 0;

    /** The unit. */
    private final Hero _unit;

    /** The position. */
    private Position _position;

    /** The state. */
    private state _state = state.STOPPED;

    /** The sprite. */
    private final TiledImage _sprite;

    /** The direction map. */
    private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetDeltaPosition(int, int)
     */
    @Override
    public Position GetDeltaPosition(int cell_width, int cell_height)
    {
        if (_state == state.WALKING)
        {
            Direction unitCurrentDir = _unit.GetDirection();
            switch (unitCurrentDir)
            {
            case East:
                return new Position((cell_width / _state.sprState.GetNumFrames()) * _frame, 0);
            case West:
                return new Position(-(cell_width / _state.sprState.GetNumFrames()) * _frame, 0);
            case North:
                return new Position(0 , -(cell_height / _state.sprState.GetNumFrames()) * _frame);
            case South:
                return new Position(0 , (cell_height / _state.sprState.GetNumFrames()) * _frame);
            default:
                return null;
            }
        }
        else
        {
            return new Position(0, 0);
        }
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetPosition()
     */
    @Override
    public Position GetPosition()
    {
        return _position;
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
