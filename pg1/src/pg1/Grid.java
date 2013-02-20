/**
 * 
 */
package pg1;

import java.lang.reflect.Array;

/**
 * @author miguel
 *
 */
public class Grid<T> {
	
	public static char Seperator = ' ';
	
	@SuppressWarnings("unchecked")
	public Grid(int width, int height, T obj_init)
	{
		_cells = (Cell<T>[][])Array.newInstance( Cell[].class, width);
		
		for (int i = 0; i < _cells.length; i++)
			_cells[i] = (Cell<T>[])Array.newInstance(Cell.class, height);
		
		for (int i = 0; i < _cells.length; i++)
			for (int j = 0; j < _cells[i].length; j++)
				_cells[i][j] = new Cell<T>(obj_init);
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
	
	
	private Cell<T> [][]_cells;
	
	public static void main (String [] args)
	{
		Grid<Character> g = new Grid<Character>(10, 10, 'a');
		
		
		System.out.print(g);
	}
}
