package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Hero;
import model.Position;

public class HeroSprite implements AnimatedSprite
{
	private static int STOPPED_ARMED_DIFF = 1;
	private static int WALKING_ARMED_LINE_DIFF = 4;
    private enum state
    {
        STOPPED(new SpriteState(1, 0, 8, 0, 2)),
        WALKING(new SpriteState(8, 0, 0, 1, 0));

        state(SpriteState s)
        {
            sprState = s;
        }

        public final SpriteState sprState;
    }

    public boolean IsWalking() { return _state == state.WALKING; }

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

    @Override
    public void Update(int diff)
    {
    	_timeCount++;

    	if (_state != state.WALKING && !_position.equals(_unit.GetPosition()))
    	{
    		_state = state.WALKING;
    	}

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

    @Override
    public BufferedImage GetCurrentImage()
    {
        Direction unitCurrentDir = _unit.GetDirection();
        if (_state != state.STOPPED)
            return _sprite.GetTile(_frame, _state.sprState.GetInitialLine() + (_unit.IsArmed() ? WALKING_ARMED_LINE_DIFF : 0) + _dirMap.get(unitCurrentDir));
        else
        {
            if (unitCurrentDir == Direction.South)
            {
                return _sprite.GetTile(0 + (_unit.IsArmed() ? STOPPED_ARMED_DIFF : 0), _state.sprState.GetInitialLine());
            }
            else if (unitCurrentDir == Direction.North)
            {
                return _sprite.GetTile(4 + (_unit.IsArmed() ? STOPPED_ARMED_DIFF : 0), _state.sprState.GetInitialLine());
            }
            else if (unitCurrentDir == Direction.West)
            {
                return _sprite.GetTile(2 + (_unit.IsArmed() ? STOPPED_ARMED_DIFF : 0), _state.sprState.GetInitialLine());
            }
            else if (unitCurrentDir == Direction.East)
            {
                return _sprite.GetTile(6 + (_unit.IsArmed() ? STOPPED_ARMED_DIFF : 0), _state.sprState.GetInitialLine());
            }
        }

        return null;

    }

    private int _frame = 0;
    private int _timeCount = 0;
    private final Hero _unit;
    private Position _position;
    private state _state = state.STOPPED;
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
}
