/**
 * 
 */
package pg1;

import utils.Pair;

/**
 * @author miguel
 *
 */
public class Maze {

	private static final Pair<Integer> DEFAULT_POSITION = new Pair<Integer>(-1, -1);
	public Maze (int width, int height)
	{
		_board = new Grid<Character>(width, height, ' ');
	}
	
	public Maze (int width, int height, String [] cells)
	{		
		_board = new Grid<Character>(width, height, ' ');
		
		for (int i = 0; i < width; i++)			// Para cada coluna
			for (int j = 0; j < height; j++)	// Para cada linha
			{
				char c = cells[j].charAt(i);
				if (c != 'X' && c != ' ')
					c = ' ';
				
				_board.SetCell(i, j, c);
			}
	}
	
	@Override
	public String toString() { return _board.toString(); }
	
	private boolean isValidPosition(Pair<Integer> pos) 
	{
		return (pos.first >= 0) && (pos.second >= 0) && (pos.first < _board.Width) && (pos.second < _board.Height);
	}
	
	public boolean SetHeroPosition(Pair<Integer> pos)
	{
		if (!isValidPosition(pos))
			return false;
		
		char prevCell = _board.GetCell(pos.first, pos.second);
		if (prevCell == 'X' )
			return false;
		
		if (pos.equals(_exitPosition))
		{
			if (IsHeroArmed())
				_finished = true;
			else
				return false;
		}
		
		if (pos.equals(_swordPosition))
			_swordPosition = DEFAULT_POSITION;
		
		if (isAdjacent(pos, _dragonPosition) || pos.equals(_dragonPosition))
		{
			if (IsHeroArmed())
				SetDragonPosition(DEFAULT_POSITION);
			else
				_heroAlive = false;
		}
		
		_board.SetCell(pos.first, pos.second, IsHeroArmed() ? 'A' : 'H');
	
		if (!_heroPosition.equals(DEFAULT_POSITION))
			_board.SetCell(_heroPosition.first, _heroPosition.second, ' ');
		
		_heroPosition = pos;
		
		return true;
	}
	
	private boolean isAdjacent(Pair<Integer> pos1, Pair<Integer> pos2) {
		return (pos1.equals(new Pair<Integer>(pos2.first + 1, pos2.second))) || 
			   (pos1.equals(new Pair<Integer>(pos2.first - 1, pos2.second))) || 
			   (pos1.equals(new Pair<Integer>(pos2.first, pos2.second + 1))) || 
			   (pos1.equals(new Pair<Integer>(pos2.first, pos2.second - 1)));
	}

	public boolean MoveHero(utils.Key direction)
	{
		boolean result = false;
		
		switch (direction)
		{
		case UP:
			result = SetHeroPosition(new Pair<Integer>(_heroPosition.first, _heroPosition.second - 1));
			break;
		case DOWN:
			result = SetHeroPosition(new Pair<Integer>(_heroPosition.first, _heroPosition.second + 1));
			break;
		case LEFT:
			result = SetHeroPosition(new Pair<Integer>(_heroPosition.first - 1, _heroPosition.second));
			break;
		case RIGHT:
			result = SetHeroPosition(new Pair<Integer>(_heroPosition.first + 1, _heroPosition.second));
			break;
		}
		
		
		return result;
	}
	
	public boolean MoveDragon(utils.Key direction)
	{
		boolean result = false;
		
		if (!IsDragonAlive())
			return true;
		
		switch (direction)
		{
		case UP:
			result = SetDragonPosition(new Pair<Integer>(_dragonPosition.first, _dragonPosition.second - 1));
			break;
		case DOWN:
			result = SetDragonPosition(new Pair<Integer>(_dragonPosition.first, _dragonPosition.second + 1));
			break;
		case LEFT:
			result = SetDragonPosition(new Pair<Integer>(_dragonPosition.first - 1, _dragonPosition.second));
			break;
		case RIGHT:
			result = SetDragonPosition(new Pair<Integer>(_dragonPosition.first + 1, _dragonPosition.second));
			break;
		}
		
		return result;
	}
	
	public boolean SetExit(Pair<Integer> pos)
	{
		if (!isValidPosition(pos))
			return false;
		
		_board.SetCell(pos.first, pos.second, 'S');
		if (!_exitPosition.equals(DEFAULT_POSITION))
			_board.SetCell(_exitPosition.first, _exitPosition.second, ' ');
		
		_exitPosition = pos;
		
		return true;
	}
	
	public boolean SetSwordPosition(Pair<Integer> pos)
	{
		if (!isValidPosition(pos) && !pos.equals(DEFAULT_POSITION))
			return false;
		
		if (!pos.equals(DEFAULT_POSITION))
			_board.SetCell(pos.first, pos.second, pos.equals(_dragonPosition) ? 'F' : 'E');
		
		if (!_swordPosition.equals(DEFAULT_POSITION))
			_board.SetCell(_swordPosition.first, _swordPosition.second, _swordPosition.equals(_dragonPosition) ? 'D' : ' ');
		else
			_board.SetCell(_heroPosition.first, _heroPosition.second, 'H');
		
		_swordPosition = pos;
		
		return true;
	}
	
	public boolean SetDragonPosition(Pair<Integer> pos)
	{
		if (!isValidPosition(pos) && !pos.equals(DEFAULT_POSITION))
			return false;
		
		if (!pos.equals(DEFAULT_POSITION) && _board.GetCell(pos.first, pos.second) == 'X' )
			return false;
		
		if (!pos.equals(DEFAULT_POSITION))
			_board.SetCell(pos.first, pos.second, pos.equals(_swordPosition) ? 'F' : 'D');
		
		if (!_dragonPosition.equals(DEFAULT_POSITION))
			_board.SetCell(_dragonPosition.first, _dragonPosition.second, _dragonPosition.equals(_swordPosition) ? 'E' : ' ');
		
		_dragonPosition = pos;
		
		return true;
		
	}
	
	public boolean IsFinished() { return _finished || !_heroAlive; }
	public boolean IsHeroArmed() { return _swordPosition.equals(DEFAULT_POSITION); }
	public boolean IsHeroAlive() { return _heroAlive; }
	public boolean IsDragonAlive() { return !_dragonPosition.equals(DEFAULT_POSITION); }
	private Grid<Character> _board;
	
	private Pair<Integer> _heroPosition = DEFAULT_POSITION;
	private Pair<Integer> _swordPosition = DEFAULT_POSITION;
	private Pair<Integer> _exitPosition = DEFAULT_POSITION;
	private Pair<Integer> _dragonPosition = DEFAULT_POSITION;
	
	private boolean _finished = false;
	private boolean _heroAlive = true;
}
