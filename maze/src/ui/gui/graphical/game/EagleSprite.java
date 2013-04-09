package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Eagle;
import model.Position;

/**
 * The Class EagleSprite.
 */
public class EagleSprite implements AnimatedSprite
{
    /**
     * The state of the EagleSprite.
     */
    private enum state
    {
        /** Stopped. */
        STOPPED(new SpriteState(1, 16, 0, 0, 0)),

        /** Flying. */
        FLYING(new SpriteState(16, 0, 0, 1, 0));

        /**
         * Instantiates a new state.
         *
         * @param s the state
         */
        state(SpriteState s)
        {
            sprState = s;
        }

        /** The sprite state. */
        public final SpriteState sprState;
    }

    /**
     * Instantiates a new eagle sprite.
     *
     * @param eagle the eagle
     * @param sprite the sprite
     */
    public EagleSprite(Eagle eagle, TiledImage sprite)
    {
        _dirMap.put(Direction.East, 0);
        _dirMap.put(Direction.North, 1);
        _dirMap.put(Direction.South, 2);
        _dirMap.put(Direction.West, 3);

        _unit = eagle;
        _sprite = sprite;
        _position = _unit.GetPosition().clone();
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#Update(int)
     */
    @Override
    public void Update(int diff)
    {
        _timeCount++;

        if (_state == state.STOPPED)
        {
            if (_unit.IsFollowingHero()) /* Parados */
            {
                if (_unit.IsFlying())
                {
                    _state = state.FLYING;
                    _frame = 0;
                    _timeCount = 0;
                }

                if (!_position.equals(_unit.GetPosition()))
                {
                    _state = state.FLYING;  /* Voar atrás do heroi */
                    _frame = 0;
                    _timeCount = 0;
                }
            }
            else /* Parada na espada */
            {
                if (_unit.IsFlying())
                {
                    _state = state.FLYING;
                    _frame = 0;
                    _timeCount = 0;
                }
            }
        }
        else /* if (_state == state.FLYING) */
        {
            if (_unit.IsFollowingHero()) /* Voar atrás do heroi */
            {
                if (_frame == _state.sprState.GetNumFrames() - 1)
                {
                    _state = state.STOPPED;
                    _position = _unit.GetPosition().clone();
                }
            }
            else /* Voar para e da espada */
            {
                if (!_unit.IsFlying())
                {
                    _state = state.STOPPED;
                    _frame = 0;
                    _timeCount = 0;
                }

            }
        }

        if (_timeCount == 1)
        {
            _frame += _state.sprState.GetDeltaX();
            _frame %= _state.sprState.GetNumFrames();
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
            return _sprite.GetTile(_frame, _state.sprState.GetInitialLine() + _dirMap.get(unitCurrentDir));
        else
            return _sprite.GetTile(_state.sprState.GetInitialColumn(), _state.sprState.GetInitialLine() + _dirMap.get(unitCurrentDir));
    }

    /** The state. */
    private state _state = state.STOPPED;

    /** The time count. */
    private int _timeCount = 0;

    /** The frame. */
    private int _frame = 0;

    /** The unit. */
    private final Eagle _unit;

    /** The position. */
    private Position _position;

    /** The sprite. */
    private final TiledImage _sprite;

    /** The direction map */
    private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetDeltaPosition(int, int)
     */
    @Override
    public Position GetDeltaPosition(int cell_width, int cell_height)
    {
        if (_unit.IsFollowingHero() && _state == state.FLYING)
        {
            Direction unitCurrentDir = _unit.GetDirection();
            switch (unitCurrentDir)
            {
            case East:
                return new Position((cell_width / _state.sprState.GetNumFrames()) * _frame, 0);
            case West:
                return new Position(- (cell_width / _state.sprState.GetNumFrames()) * _frame, 0);
            case North:
                return new Position(0, -(cell_height / _state.sprState.GetNumFrames()) * _frame);
            case South:
                return new Position(0, (cell_height / _state.sprState.GetNumFrames()) * _frame);
            default:
                return null;
            }
        }
        else
            return new Position(0, 0);
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.AnimatedSprite#GetPosition()
     */
    @Override
    public Position GetPosition()
    {
        if (_state == state.STOPPED && _unit.IsFollowingHero())
            return _unit.GetPosition();
        else if (_state == state.STOPPED && !_unit.IsFollowingHero())
            return _unit.GetPosition();
        else if (_state == state.FLYING && _unit.IsFollowingHero())
            return _position;
        else if (_state == state.FLYING && !_unit.IsFollowingHero())
            return _unit.GetPosition();

        return null;
        /*if (!_unit.IsFlying() && _state == state.FLYING)
            return _position;
        else if (_unit.IsFollowingHero() && _state == state.STOPPED)
            return _unit.GetPosition();
        else if (!_unit.IsFollowingHero() && _state == state.STOPPED)
            return _unit.GetPosition();
        else
            return _unit.GetPosition();*/
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
