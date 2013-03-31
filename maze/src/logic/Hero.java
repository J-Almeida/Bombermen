package logic;

import model.Position;

/**
 * The Class Hero.
 */
public class Hero extends Unit
{
    /** Has sword? */
    private boolean _armed;

    /**
     * Instantiates a new hero.
     */
    public Hero()
    {
        super(UnitType.Hero);
    }

    /**
     * Checks if hero is armed.
     *
     * @return true, if hero has sword
     */
    public boolean IsArmed()
    {
        return _armed;
    }

    /**
     * Equip or unequip sword.
     */
    public void EquipSword(boolean equip)
    {
        _armed = equip;
    }

    @Override
    public char GetSymbol()
    {
        return _armed ? 'A' : 'H';
    }

    private ExitPortal _exitPortal = null; // cached

    @Override
    public void Update(Maze maze)
    {
        if (_exitPortal == null)
            _exitPortal = maze.FindExitPortal();

        if (_position.equals(_exitPortal.GetPosition()))
            maze.SetFinished(true);

        while (!_eventQueue.isEmpty())
        {
            Event event = _eventQueue.peek();
            if (event.IsRequestMovementEvent())
            {
                RequestMovementEvent ev = event.ToRequestMovementEvent();

                Position newPos = _position.clone();
                Direction.ApplyMovement(newPos, ev.Direction);

                if (maze.IsPathPosition(newPos) || (IsArmed() && _exitPortal.GetPosition().equals(newPos)))
                {
                    _position = newPos;
                    maze.ForwardEventToUnits(new MovementEvent(this, ev.Direction));
                }
            }

            _eventQueue.poll();
        }
    }

    @Override
    public void OnCollision(Unit other)
    {
        if (other.IsSword())
        {
            this.EquipSword(true);
            other.Kill();
        }
        else if (other.IsEagle())
        {
            if (other.ToEagle().IsArmed())
                this.EquipSword(true);
        }
        else if (other.IsDragon())
        {
            if (this.IsArmed())
                other.Kill();
        }
    }
}
