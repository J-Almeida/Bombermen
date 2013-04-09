package ui.gui.graphical;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Hero;

public class HeroSprite implements AnimatedSprite
{
	private enum state
	{
		STOPPED(new SpriteState(1, 0, 8, 0, 2)),
		WALKING(new SpriteState(8, 0, 0, 1, 0)),
		WALKING_ARMED(new SpriteState(4, 0, 0, 1, 0));

		state(SpriteState s)
		{
			sprState = s;
		}

		public final SpriteState sprState;
	}

	public HeroSprite(Hero u, TiledImage sprite)
	{
		_dirMap.put(Direction.East, 0);
		_dirMap.put(Direction.North, 1);
		_dirMap.put(Direction.South, 2);
		_dirMap.put(Direction.West, 3);

		_unit = u;
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
			if (unitCurrentDir == Direction.South)
			{
				return _sprite.GetTile(0, _state.sprState.GetInitialLine());
			}
			else if (unitCurrentDir == Direction.North)
			{
				return _sprite.GetTile(4, _state.sprState.GetInitialLine());
			}
			else if (unitCurrentDir == Direction.West)
			{
				return _sprite.GetTile(2, _state.sprState.GetInitialLine());
			}
			else if (unitCurrentDir == Direction.East)
			{
				return _sprite.GetTile(6, _state.sprState.GetInitialLine());
			}
		}

		return null;

	}

	private final Hero _unit;
	private final state _state = state.STOPPED;
	private final int _frame = 0;
	private final TiledImage _sprite;

	private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();
}
