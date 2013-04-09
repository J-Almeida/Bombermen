package ui.gui.graphical.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import logic.Direction;
import logic.Dragon;
import model.Position;

public class DyingDragonSprite implements AnimatedSprite
{
    public DyingDragonSprite(Dragon u, TiledImage sprite)
    {
        _dirMap.put(Direction.North, 0);
        _dirMap.put(Direction.South, 1);
        _dirMap.put(Direction.East, 2);
        _dirMap.put(Direction.West, 3);

        _unit = u;
        _sprite = sprite;
    }

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

    @Override
    public BufferedImage GetCurrentImage()
    {
        return _sprite.GetTile(_frame, _dirMap.get(_unit.GetDirection()));
    }

    @Override
    public Position GetPosition()
    {
        return _unit.GetPosition();
    }

    @Override
    public Position GetDeltaPosition(int cell_width, int cell_height)
    {
        return new Position(0, 0);
    }

    private static final SpriteState sprState = new SpriteState(11, 0, 0, 1, 0);

    private int _frame = 0;
    private int _timeCount = 0;
    private boolean _playing = true;
    private final TiledImage _sprite;
    private final Dragon _unit;

    private final HashMap<Direction, Integer> _dirMap = new HashMap<Direction, Integer>();

    @Override
    public boolean IsAlive()
    {
        return true;
    }

    @Override
    public int GetUnitId()
    {
        return _unit.GetId();
    }
}
