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
		
		_board.SetCell(pos.first, pos.second, IsHeroArmed() ? 'A' : 'H');
	
		if (_heroPosition != DEFAULT_POSITION)
			_board.SetCell(_heroPosition.first, _heroPosition.second, ' ');
		
		_heroPosition = pos;
		
		return true;
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
	
	public boolean SetExit(Pair<Integer> pos)
	{
		if (!isValidPosition(pos))
			return false;
		
		_board.SetCell(pos.first, pos.second, 'S');
		if (_exitPosition != DEFAULT_POSITION)
			_board.SetCell(_exitPosition.first, _exitPosition.second, ' ');
		
		_exitPosition = pos;
		
		return true;
	}
	
	public boolean SetSwordPosition(Pair<Integer> pos)
	{
		if (!isValidPosition(pos))
			return false;
		
		_board.SetCell(pos.first, pos.second, 'E');
		
		if (_swordPosition != DEFAULT_POSITION)
			_board.SetCell(_swordPosition.first, _swordPosition.second, ' ');
		else
			_board.SetCell(_heroPosition.first, _heroPosition.second, 'H');
		
		_swordPosition = pos;
		
		return true;
	}
	
	public boolean IsFinished() { return _finished; }
	public boolean IsHeroArmed() { return _swordPosition == DEFAULT_POSITION; }
	
	private Grid<Character> _board;
	
	private Pair<Integer> _heroPosition = DEFAULT_POSITION;
	private Pair<Integer> _swordPosition = DEFAULT_POSITION;
	private Pair<Integer> _exitPosition = DEFAULT_POSITION;
	
	private boolean _finished = false;
}
