package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Eagle;
import model.Position;

public class EagleSprite implements AnimatedSprite
{
    private enum state
    {
        STOPPED(new SpriteState(1, 16, 0, 0, 0)),
        FLYING(new SpriteState(16, 0, 0, 1, 0));

        state(SpriteState s)
        {
            sprState = s;
        }

        public final SpriteState sprState;
    }

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

    @Override
    public void Update(int diff) {
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

    @Override
    public BufferedImage GetCurrentImage()
    {
        Direction unitCurrentDir = _unit.GetDirection();
        if (_state != state.STOPPED)
            return _sprite.GetTile(_frame, _state.sprState.GetInitialLine() + _dirMap.get(unitCurrentDir));
        else
        {
            return _sprite.GetTile(_state.sprState.GetInitialColumn(), _state.sprState.GetInitialLine() + _dirMap.get(unitCurrentDir));
        }
    }

    private state _state = state.STOPPED;
    private int _timeCount = 0;
    private int _frame = 0;
    private final Eagle _unit;
    private Position _position;
    private final TiledImage _sprite;

    private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();

	@Override
	public Position GetDeltaPosition(int cell_width, int cell_height) {
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
		{
			return new Position(0, 0);
		}
	}

	@Override
	public Position GetPosition() {
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

	@Override
	public boolean IsAlive() {
		return _unit.IsAlive();
	}

	@Override
	public int GetUnitId() {
		return _unit.GetId();
	}
}
