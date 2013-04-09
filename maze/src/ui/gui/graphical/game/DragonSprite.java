package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Dragon;
import model.Position;

public class DragonSprite implements AnimatedSprite
{
    private enum state
    {
        STOPPED(new SpriteState(9, 0, 0, 1, 0)),
        WALKING(new SpriteState(9, 0, 4, 1, 0)),
        SLEEPING(new SpriteState(9, 0, 8, 1, 0));

        state(SpriteState s)
        {
            sprState = s;
        }

        public final SpriteState sprState;
    }

	private Position _position;

    public DragonSprite(Dragon u, TiledImage sprite)
    {
        _dirMap.put(Direction.North, 0);
        _dirMap.put(Direction.South, 1);
        _dirMap.put(Direction.West, 2);
        _dirMap.put(Direction.East, 3);

        _unit = u;
        _sprite = sprite;
        _position = _unit.GetPosition().clone();
    }

    @Override
    public void Update(int diff)
    {
    	_timeCount++;

    	if (_state != state.WALKING && !_position.equals(_unit.GetPosition()))
    	{
    		_state = state.WALKING;
    	}

		if (_state != state.SLEEPING && _unit.IsSleeping()) {
			_state = state.SLEEPING;
			_frame = 0;
			_timeCount = 0;
		} /*else if (_state != state.STOPPED && !_unit.IsSleeping()) {
			_state = state.STOPPED;
			_frame = 0;
			_timeCount = 0;
		}*/

    	if (_timeCount == 3)
    	{
			_frame++;
			_frame %= _state.sprState.GetNumFrames();

    		if (_state == state.WALKING && _frame == 0) {
        		_state = state.STOPPED;
        		_position = _unit.GetPosition().clone();
        	}

			_timeCount = 0;
    	}
    }

    @Override
    public BufferedImage GetCurrentImage()
    {
        Direction unitCurrentDir = _unit.GetDirection();
        return _sprite.GetTile(_frame, _state.sprState.GetInitialLine() + _dirMap.get(unitCurrentDir));
    }

    private final Dragon _unit;
    private int _timeCount = 0;
    private state _state = state.STOPPED;
    private int _frame = 0;
    private final TiledImage _sprite;

    private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();

	@Override
	public Position GetDeltaPosition(int cell_width, int cell_height) {
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

	@Override
	public Position GetPosition() {
		return _position;
	}

	@Override
	public boolean IsAlive() {
		return _unit.IsAlive();
	}

	@Override
	public int GetUnitId()
	{
		return _unit.GetId();
	}

	public Dragon GetDragon()
	{
		return _unit;
	}
}
