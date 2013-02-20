/**
 * 
 */
package pg1;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * @author miguel
 *
 */
public class Grid<T> {
	
	private static final char DEFAULT_SEPARATOR = ' ';
	
	public char Seperator = DEFAULT_SEPARATOR;
	
	public final int Width;
	public final int Height;
	
	@SuppressWarnings("unchecked")
	public Grid(int width, int height, T obj_init)
	{
		_cells = (Cell<T>[][])Array.newInstance( Cell[].class, height);
		
		for (int i = 0; i < _cells.length; i++)
			_cells[i] = (Cell<T>[])Array.newInstance(Cell.class, width);
		
		for (int i = 0; i < _cells.length; i++)
			for (int j = 0; j < _cells[i].length; j++)
				_cells[i][j] = new Cell<T>(obj_init);
		
		Width = width;
		Height = height;
	}
	
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		
		for (int i = 0; i < _cells.length; i++)
		{
			for (int j = 0; j < _cells[i].length; j++)
			{
				s.append(_cells[i][j].toString());
				if (j != _cells[i].length - 1) s.append(Seperator);
			}
			if (i != _cells.length - 1) s.append("\n");
		}
		
		return s.toString();
	}
	
	public T GetCell(int i, int j)
	{
		if (i >= 0 && i < _cells.length && j >= 0 && j < _cells[i].length)
			return _cells[i][j].GetValue();
		
		return null;
	}

	public void SetCell(int i, int j, T val)
	{
		if (i >= 0 && i < _cells.length && j >= 0 && j < _cells[i].length)
			_cells[i][j].SetValue(val);	
	}
	
	private Cell<T> [][]_cells;
	
	public static void main (String [] args)
	{
		Grid<Character> g = new Grid<Character>(20, 10, ' ');
		
		for (int i = 0; i < g.Width; i++)
		{
			g.SetCell(i, 0, 'X');
			g.SetCell(i, g.Width-1, 'X');
			g.SetCell(0, i, 'X');
			g.SetCell(g.Height - 1, i, 'X');
		}
		
		Random r = new Random();
		
		for (int i = 1; i < g.Height - 1; i++)
			for (int j = 1; j < g.Width - 1; j++)
			{
				if (r.nextBoolean())
					g.SetCell(i, j, 'X');
			}
		
		System.out.print(g);
	}
}
