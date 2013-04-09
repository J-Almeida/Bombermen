package ui.gui.graphical;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Eagle;

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
		_dirMap.put(Direction.South, 1);
		_dirMap.put(Direction.West, 2);
		_dirMap.put(Direction.North, 3);

		_unit = eagle;
		_sprite = sprite;
	}

	@Override
	public void Update(int diff) {
		// TODO Auto-generated method stub

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

	private final state _state = state.STOPPED;
	private final int _frame = 0;
	private final Eagle _unit;
	private final TiledImage _sprite;

	private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();
}
