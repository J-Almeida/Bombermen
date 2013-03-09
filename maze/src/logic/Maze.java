package logic;

import java.util.ArrayList;

import logic.Eagle.EagleState;
import model.Cell;
import model.Grid;
import model.Position;
import utils.Key;
import utils.RandomEngine;

/**
 * The Class Maze.
 */
public class Maze
{
    /** The Constant DEFAULT_POSITION. */
    private static final Position DEFAULT_POSITION = new Position();
    static final Path PATH = new Path();
    static final Wall WALL = new Wall();
    /** The Grid. */
    private Grid<InanimatedObject> _board;

    /** The exit position. */
    private Position _exitPosition = DEFAULT_POSITION;

    /** The hero. */
    private Hero _hero = new Hero();

    /** The eagle. */
    private Eagle _eagle = new Eagle();

    /** The sword. */
    private Sword _sword = new Sword();

    /** List of Dragons. */
    private ArrayList<Dragon> _dragons = new ArrayList<Dragon>();

    /** True if Game Over */
    private boolean _finished = false;

    /** The defined Dragon behaviour. */
    private DragonBehaviour _db = DragonBehaviour.Sleepy;

    /**
     * Instantiates an empty maze.
     *
     * @param width the width
     * @param height the height
     */
    public Maze(int width, int height)
    {
        _board = new Grid<InanimatedObject>(width, height, PATH);
    }

    /**
     * Adds a new dragon to the maze
     *
     * @return dragon's index/id
     */
    public int AddDragon()
    {
        _dragons.add(new Dragon(_db));
        return _dragons.size() - 1;
    }

    /**
     * Gets the width.
     *
     * @return the int
     */
    public int GetWidth()
    {
        return _board.Width;
    }

    /**
     * Gets the height.
     *
     * @return the int
     */
    public int GetHeight()
    {
        return _board.Height;
    }

    @Override
    public String toString()
    {
        char[] result = _board.toString().toCharArray();

        result[_exitPosition.Y * (_board.Width * 2) + _exitPosition.X * 2] = 'S';

        if (_sword.IsAlive())
        {
            boolean placedSword = false;

            for (Dragon d: _dragons)
            {
                if (d.IsAlive() && _sword.GetPosition().equals(d.GetPosition()))
                {
                    result[d.GetPosition().Y * (_board.Width * 2) + d.GetPosition().X * 2] = d.IsSleeping() ? 'f' : 'F';
                    placedSword = true;
                }
                else if (d.IsAlive())
                    result[d.GetPosition().Y * (_board.Width * 2) + d.GetPosition().X * 2] = d.toString().charAt(0);
            }
            if (!placedSword && !_sword.GetPosition().equals(DEFAULT_POSITION))
                result[_sword.GetPosition().Y * (_board.Width * 2) + _sword.GetPosition().X * 2] = _sword.toString().charAt(0);
        }
        else
        {
            for (Dragon d: _dragons)
                if (d.IsAlive())
                    result[d.GetPosition().Y * (_board.Width * 2) + d.GetPosition().X * 2] = d.toString().charAt(0);
        }

        if (_eagle.IsAlive() && _eagle.GetState() != EagleState.FollowingHero)
        {
            result[_eagle.GetPosition().Y * (_board.Width * 2) + _eagle.GetPosition().X * 2] = _eagle.toString().charAt(0);
        }

        if (_hero.IsAlive())
        {
            char h;
            if (_hero.IsArmed() && _eagle.GetState() == EagleState.FollowingHero)
                h = '\u00A3' /* Pound */;
            else if (_eagle.GetState() == EagleState.FollowingHero)
                h = '\u00A7' /* Section */;
            else
                h = _hero.toString().charAt(0);

            result[_hero.GetPosition().Y * (_board.Width * 2) + _hero.GetPosition().X * 2] = h;
        }

        return String.copyValueOf(result);
    }

    public boolean IsWall(Position pos)
    {
        return _board.GetCellT(pos).IsWall();
    }

    /**
     * Checks if is valid position. Bound checking only!
     *
     * @param pos the pos
     * @return true, if is valid position
     */
    private boolean IsValidPosition(Position pos)
    {
        return (pos.X >= 0) && (pos.Y >= 0) &&
                (pos.X < _board.Width) && (pos.Y < _board.Height);
    }

    /**
     * Checks if is adjacent.
     *
     * @param pos1 the pos1
     * @param pos2 the pos2
     * @return true, if is adjacent
     */
    private boolean IsAdjacent(Position pos1, Position pos2)
    {
        return (pos1.equals(new Position(pos2.X + 1, pos2.Y)))
                || (pos1.equals(new Position(pos2.X - 1, pos2.Y)))
                || (pos1.equals(new Position(pos2.X, pos2.Y + 1)))
                || (pos1.equals(new Position(pos2.X, pos2.Y - 1)));
    }

    public void SendEagleToSword()
    {
        if (_eagle.GetState() == EagleState.OnFlight)
            return;

        _eagle.SetState(EagleState.OnFlight);
        _eagle.SetSwordPosition(_sword.GetPosition());
    }

    /**
     * Move hero.
     *
     * @param direction the direction
     * @return true, if successful
     */
    public boolean MoveHero(utils.Key direction)
    {
        boolean result = false;

        Position newPos;

        switch (direction)
        {
            case UP:
                newPos = new Position(_hero.GetPosition().X, _hero.GetPosition().Y - 1);
                break;
            case DOWN:
                newPos = new Position(_hero.GetPosition().X, _hero.GetPosition().Y + 1);
                break;
            case LEFT:
                newPos = new Position(_hero.GetPosition().X - 1, _hero.GetPosition().Y);
                break;
            case RIGHT:
                newPos = new Position(_hero.GetPosition().X + 1, _hero.GetPosition().Y);
                break;
            default:
                return false;
        }

        result = SetHeroPosition(newPos);
        if (result && _eagle.GetState() == EagleState.FollowingHero)
            SetEaglePosition(newPos, false);

        return result;
    }

    /**
     * Move dragon.
     *
     * @param index the index
     * @param direction the direction
     * @return true, if successful
     */
    public boolean MoveDragon(int index, utils.Key direction)
    {
        if (!IsDragonAlive(index))
            return true;

        Position newPos;
        Dragon d = _dragons.get(index);

        switch (direction)
        {
            case UP:
                newPos = new Position(d.GetPosition().X, d.GetPosition().Y - 1);
                break;
            case DOWN:
                newPos = new Position(d.GetPosition().X, d.GetPosition().Y + 1);
                break;
            case LEFT:
                newPos = new Position(d.GetPosition().X - 1, d.GetPosition().Y);
                break;
            case RIGHT:
                newPos = new Position(d.GetPosition().X + 1, d.GetPosition().Y);
                break;
        default:
            return false;
        }

        return SetDragonPosition(index, newPos);
    }

    /**
     * Gets the hero position.
     *
     * @return the pair
     */
    public Position GetHeroPosition()
    {
        return _hero.GetPosition();
    }

    /**
     * Sets the hero position.
     *
     * @param pos the new position
     * @return true, if successful
     */
    public boolean SetHeroPosition(Position pos)
    {
        if (!IsValidPosition(pos) || IsWall(pos) ||
                (pos.equals(_exitPosition) && !IsHeroArmed()))
            return false;

        _hero.SetPosition(pos);

        return true;
    }

    /**
     * Sets the eagle position.
     *
     * @param pos the new position
     * @param force true if should ignore walls
     * @return true, if successful
     */
    public boolean SetEaglePosition(Position pos, boolean force)
    {
        if (!IsValidPosition(pos) || (!force && IsWall(pos)))
            return false;

        _eagle.SetPosition(pos);

        return true;
    }

    public Position GetExitPosition()
    {
        return _exitPosition;
    }

    /**
     * Sets the exit position.
     *
     * @param pos the pos
     * @return true, if successful
     */
    public boolean SetExitPosition(Position pos)
    {
        if (!IsValidPosition(pos))
            return false;

        _exitPosition = pos;

        return true;
    }

    public Position GetSwordPosition()
    {
        return _sword.GetPosition();
    }

    /**
     * Sets the sword position.
     *
     * @param pos the pos
     * @return true, if successful
     */
    public boolean SetSwordPosition(Position pos)
    {
        if (!IsValidPosition(pos) && !pos.equals(DEFAULT_POSITION) ||
           (!pos.equals(DEFAULT_POSITION) && IsWall(pos)))
            return false;

        _sword.SetPosition(pos);

        return true;
    }

    public Position GetDragonPosition(int index)
    {
        return _dragons.get(index).GetPosition();
    }

    /**
     * Sets the dragon position.
     *
     * @param index the index
     * @param pos the pos
     * @return true, if successful
     */
    public boolean SetDragonPosition(int index, Position pos)
    {
        if ((!IsValidPosition(pos) && !pos.equals(DEFAULT_POSITION))
                || (!pos.equals(DEFAULT_POSITION) && IsWall(pos)
                || (!pos.equals(DEFAULT_POSITION) && pos.equals(_exitPosition))))
            return false;

        _dragons.get(index).SetPosition(pos);

        return true;

    }

    /**
     * Update.
     */
    public void Update()
    {
        for (int i = 0; i < _dragons.size(); i++)
        {
            boolean success = false;
            while (!success && _dragons.get(i).IsAlive() && _dragons.get(i).CanMove())
            {
                Key dir = Key.toEnum(RandomEngine.GetInt(0, 4));
                success = (dir == null) || this.MoveDragon(i, dir);
            }
        }

        if (_hero.GetPosition().equals(_exitPosition))
            _finished = true;
        if (_hero.GetPosition().equals(_sword.GetPosition()))
        {
            _sword.PushEvent(new Collision(_hero));
            _hero.PushEvent(new Collision(_sword));
        }

        if (_eagle.GetPosition().equals(_sword.GetPosition()))
        {
            _sword.PushEvent(new Collision(_eagle));
            _eagle.PushEvent(new Collision(_sword));
        }

        if (_eagle.IsArmed() && _hero.GetPosition().equals(_eagle.GetPosition()))
        {
            _hero.PushEvent(new Collision(_eagle));
            _eagle.PushEvent(new Collision(_hero));
        }

        for (Dragon d : _dragons)
        {
            if (IsAdjacent(_hero.GetPosition(), d.GetPosition()) || d.GetPosition().equals(_hero.GetPosition()))
            {
                d.PushEvent(new Collision(_hero));
                _hero.PushEvent(new Collision(d));
            }

            if ((_eagle.GetState() == EagleState.ReachedSword || _eagle.GetState() == EagleState.OnFloor) &&
                    IsAdjacent(_eagle.GetPosition(), d.GetPosition()))
            {
                d.PushEvent(new Collision(_eagle));
                _hero.PushEvent(new Collision(d));
            }
        }

        for (int i = 0; i < _dragons.size(); i++)
            _dragons.get(i).Update();

        _sword.Update();
        _eagle.Update();
        _hero.Update();
    }

    /**
     * Checks if is finished.
     *
     * @return true, if successful
     */
    public boolean IsFinished()
    {
        return _finished || !IsHeroAlive();
    }

    /**
     * Checks if hero is armed.
     *
     * @return true, if successful
     */
    public boolean IsHeroArmed()
    {
        return _hero.IsArmed();
    }

    /**
     * Checks if eagle is armed.
     *
     * @return true, if successful
     */
    public boolean IsEagleArmed()
    {
        return _eagle.IsArmed();
    }

    /**
     * Checks if hero is alive.
     *
     * @return true, if successful
     */
    public boolean IsHeroAlive()
    {
        return _hero.IsAlive();
    }

    /**
     * Checks if dragon is alive.
     *
     * @param index the index
     * @return true, if successful
     */
    public boolean IsDragonAlive(int index)
    {
        return (_dragons.size() - 1 <= index) && _dragons.get(index).IsAlive();
    }

    /**
     * Checks if dragon is sleeping.
     *
     * @param index the index
     * @return true, if successful
     */
    public boolean IsDragonSleeping(int index)
    {
        return (_dragons.size() - 1 <= index) && _dragons.get(index).IsSleeping();
    }

    /**
     * Checks if dragon is sleeping.
     *
     * @param index the index
     * @param time  the time that the dragon is going to sleep for
     * @return true, if successful
     */
    public void SetDragonToSleep(int index, int time)
    {
        if (_dragons.size() - 1 <= index)
            _dragons.get(index).SetToSleep(time);
    }

    /**
     * Gets the underlying cell.
     *
     * @param pos the position
     * @return the cell
     */
    public Cell<InanimatedObject> GetCell(Position pos)
    {
        return _board.GetCell(pos);
    }

    /**
     * Sets the underlying cell.
     *
     * @param pos the position
     * @param val the character
     */
    public void SetCell(Position pos, InanimatedObject val)
    {
        _board.SetCell(pos, val);
    }

    /**
     * Sets the dragon behaviour.
     *
     * @param db the Dragon behaviour
     */
    public void SetDragonBehaviour(DragonBehaviour db)
    {
        _db = db;
    }

    public void SetEagleState(EagleState es)
    {
        _eagle.SetState(es);
    }

    public void EquipHero()
    {
        _hero.EquipSword();
    }

    public void EquipEagle()
    {
        _eagle.EquipSword();
    }

    public void UnequipHero()
    {
        _hero.UnequipSword();
    }

    public void UnequipEagle()
    {
        _eagle.UnequipSword();
    }

    public Position GetEaglePosition()
    {
        return _eagle.GetPosition();
    }

    public boolean IsEagleAlive()
    {
        return _eagle.IsAlive();
    }
}
