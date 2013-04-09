package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Dragon;

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

    public DragonSprite(Dragon u, TiledImage sprite)
    {
        _dirMap.put(Direction.North, 0);
        _dirMap.put(Direction.South, 1);
        _dirMap.put(Direction.West, 2);
        _dirMap.put(Direction.East, 3);

        _unit = u;
        _sprite = sprite;
    }

    @Override
    public void Update(int diff)
    {
        if (_unit.IsSleeping() && _state != state.SLEEPING) {
            _state = state.SLEEPING;
            _frame = 0;
        } else if (_state != state.STOPPED) {
            _state = state.STOPPED;
            _frame = 0;
        }

        if (diff > 16)
        {
            _frame++;
            _frame %= _state.sprState.GetNumFrames();
        }
    }

    @Override
    public BufferedImage GetCurrentImage()
    {
        Direction unitCurrentDir = _unit.GetDirection();
        return _sprite.GetTile(_frame, _state.sprState.GetInitialLine() + _dirMap.get(unitCurrentDir));
    }

    private final Dragon _unit;
    private state _state = state.STOPPED;
    private int _frame = 0;
    private final TiledImage _sprite;

    private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();
}
