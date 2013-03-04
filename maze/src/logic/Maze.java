package logic;

import java.util.ArrayList;

import model.Cell;
import model.Grid;
import utils.Key;
import utils.Pair;
import utils.RandomEngine;

/**
 * The Class Maze.
 */
public class Maze
{
    /** The Constant DEFAULT_POSITION. */
    private static final Pair<Integer> DEFAULT_POSITION = Pair.IntN(-1, -1);

    /** The Grid. */
    private Grid<Character> _board;

    /** The exit position. */
    private Pair<Integer> _exitPosition = DEFAULT_POSITION;

    /** The hero. */
    private Hero _hero = new Hero();

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
        _board = new Grid<Character>(width, height, ' ');
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        _board.SetCell(_exitPosition, 'S');

        if (_sword.IsAlive())
        {
            boolean placedSword = false;

            for (Dragon d: _dragons)
            {
                if (d.IsAlive() && _sword.GetPosition().equals(d.GetPosition()))
                {
                    _board.SetCell(_sword.GetPosition(), d.IsSleeping() ? 'f' : 'F');
                    placedSword = true;
                }
                else if (d.IsAlive())
                    _board.SetCell(d.GetPosition(), d.IsSleeping() ? 'd' : 'D');
            }
            if (!placedSword)
                _board.SetCell(_sword.GetPosition(), 'E');
        }
        else
        {
            for (Dragon d: _dragons)
                if (d.IsAlive())
                    _board.SetCell(d.GetPosition(), d.IsSleeping() ? 'd' : 'D');
        }

        if (_hero.IsAlive())
            _board.SetCell(_hero.GetPosition(), _hero.IsArmed() ? 'A' : 'H');

        String res = _board.toString();

        // reset
        _board.SetCell(_exitPosition, ' ');
        if (_hero.IsAlive())
            _board.SetCell(_hero.GetPosition(), ' ');
        if (_sword.IsAlive())
            _board.SetCell(_sword.GetPosition(), ' ');
        for (Dragon d : _dragons)
            if (d.IsAlive())
                _board.SetCell(d.GetPosition(), ' ');

        return res;
    }

    /**
     * Checks if is valid position.
     *
     * @param pos the pos
     * @return true, if is valid position
     */
    private boolean IsValidPosition(Pair<Integer> pos)
    {
        return (pos.first >= 0) && (pos.second >= 0) &&
                (pos.first < _board.Width) && (pos.second < _board.Height);
    }

    /**
     * Checks if is adjacent.
     *
     * @param pos1 the pos1
     * @param pos2 the pos2
     * @return true, if is adjacent
     */
    private boolean IsAdjacent(Pair<Integer> pos1, Pair<Integer> pos2)
    {
        return (pos1.equals(Pair.IntN(pos2.first + 1, pos2.second)))
                || (pos1.equals(Pair.IntN(pos2.first - 1, pos2.second)))
                || (pos1.equals(Pair.IntN(pos2.first, pos2.second + 1)))
                || (pos1.equals(Pair.IntN(pos2.first, pos2.second - 1)));
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

        switch (direction)
        {
            case UP:
                result = SetHeroPosition(Pair.IntN(_hero.GetPosition().first,
                        _hero.GetPosition().second - 1));
                break;
            case DOWN:
                result = SetHeroPosition(Pair.IntN(_hero.GetPosition().first,
                        _hero.GetPosition().second + 1));
                break;
            case LEFT:
                result = SetHeroPosition(Pair.IntN(
                        _hero.GetPosition().first - 1,
                        _hero.GetPosition().second));
                break;
            case RIGHT:
                result = SetHeroPosition(Pair.IntN(
                        _hero.GetPosition().first + 1,
                        _hero.GetPosition().second));
                break;
        default:
            return false;
        }

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
        boolean result = false;

        if (!IsDragonAlive(index))
            return true;

        switch (direction)
        {
            case UP:
                result = SetDragonPosition(index, Pair.IntN(
                        _dragons.get(index).GetPosition().first,
                        _dragons.get(index).GetPosition().second - 1));
                break;
            case DOWN:
                result = SetDragonPosition(index, Pair.IntN(
                        _dragons.get(index).GetPosition().first,
                        _dragons.get(index).GetPosition().second + 1));
                break;
            case LEFT:
                result = SetDragonPosition(index, Pair.IntN(
                        _dragons.get(index).GetPosition().first - 1,
                        _dragons.get(index).GetPosition().second));
                break;
            case RIGHT:
                result = SetDragonPosition(index, Pair.IntN(
                        _dragons.get(index).GetPosition().first + 1,
                        _dragons.get(index).GetPosition().second));
                break;
        default:
            return false;
        }

        return result;
    }

    /**
     * Gets the hero position.
     *
     * @return the pair
     */
    public Pair<Integer> GetHeroPosition()
    {
        return _hero.GetPosition();
    }

    /**
     * Sets the hero position.
     *
     * @param pos the pos
     * @return true, if successful
     */
    public boolean SetHeroPosition(Pair<Integer> pos)
    {
        if (!IsValidPosition(pos) || _board.GetCellT(pos) == 'X'
                || (pos.equals(_exitPosition) && !IsHeroArmed()))
            return false;

        _hero.SetPosition(pos);

        return true;
    }

    public Pair<Integer> GetExitPosition()
    {
        return _exitPosition;
    }

    /**
     * Sets the exit position.
     *
     * @param pos the pos
     * @return true, if successful
     */
    public boolean SetExitPosition(Pair<Integer> pos)
    {
        if (!IsValidPosition(pos))
            return false;

        _exitPosition = pos;

        return true;
    }

    public Pair<Integer> GetSwordPosition()
    {
        return _sword.GetPosition();
    }

    /**
     * Sets the sword position.
     *
     * @param pos the pos
     * @return true, if successful
     */
    public boolean SetSwordPosition(Pair<Integer> pos)
    {
        if (!IsValidPosition(pos) && !pos.equals(DEFAULT_POSITION) ||
           (!pos.equals(DEFAULT_POSITION) && _board.GetCellT(pos) == 'X'))
            return false;

        _sword.SetPosition(pos);

        return true;
    }

    public Pair<Integer> GetDragonPosition(int index)
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
    public boolean SetDragonPosition(int index, Pair<Integer> pos)
    {
        if ((!IsValidPosition(pos) && !pos.equals(DEFAULT_POSITION))
                || (!pos.equals(DEFAULT_POSITION) && _board.GetCellT(pos) == 'X')
                || (!pos.equals(DEFAULT_POSITION) && pos.equals(_exitPosition)))
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
            _sword.pushEvent(new Colision<Hero>(_hero));
            _hero.pushEvent(new Colision<Sword>(_sword));
        }

        for (Dragon d : _dragons)
        {
            if (IsAdjacent(_hero.GetPosition(), d.GetPosition()) || d.GetPosition().equals(_hero.GetPosition()))
            {
                d.pushEvent(new Colision<Hero>(_hero));
                _hero.pushEvent(new Colision<Dragon>(d));
            }
        }

        for (int i = 0; i < _dragons.size(); i++)
            _dragons.get(i).Update();

        _sword.Update();
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
     * Hero equips sword.
     */
    public void HeroEquipSword()
    {
        _hero.EquipSword();
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
        return _dragons.get(index).IsAlive();
    }

    /**
     * Gets the underlying cell.
     *
     * @param pos the position
     * @return the cell
     */
    public Cell<Character> GetCell(Pair<Integer> pos)
    {
        return _board.GetCell(pos);
    }

    /**
     * Sets the underlying cell.
     *
     * @param pos the position
     * @param val the character
     */
    public void SetCell(Pair<Integer> pos, Character val)
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
}
