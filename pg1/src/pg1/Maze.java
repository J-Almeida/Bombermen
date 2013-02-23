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
	
	public boolean SetHeroPosition(Pair<Integer> pos)
	{
		char prevCell = _board.GetCell(pos.first, pos.second);
		if (prevCell == 'X' )
			return false;
		
		if (pos.equals(_exitPosition))
			_finished = true;
		
		_board.SetCell(pos.first, pos.second, 'H');
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
		_board.SetCell(pos.first, pos.second, 'S');
		_exitPosition = pos;
		
		return true;
	}
	
	public boolean IsFinished() { return _finished; }
	
	private Grid<Character> _board;
	private Pair<Integer> _heroPosition = new Pair<Integer>(-1, -1);
	private boolean _finished = false;
	private Pair<Integer> _exitPosition = new Pair<Integer>(-1, -1);
}
