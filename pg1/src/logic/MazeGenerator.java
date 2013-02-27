package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import model.Cell;
import utils.Pair;
import utils.Utilities;

public abstract class MazeGenerator
{
    private static class CellPos
    {
        CellPos(Cell<Character> c, Pair<Integer> pos)
        {
            Cell = c;
            Position = pos;
        }

        Cell<Character> Cell;
        Pair<Integer> Position;
    }

    private static class Helper
    {
        public CellPos cp;
        public List<Pair<CellPos>> nbrs;
    }

    public static Maze RandomMaze(int size, int numDragons, Random r)
    {
        Maze result = new Maze(size, size, numDragons);
        result.r = r;
        Stack<Helper> stk = new Stack<Helper>();

        for (int i = 0; i < result.GetWidth(); i++)
            for (int j = 0; j < result.GetHeight(); j++)
                result.GetCell(Pair.IntN(i, j)).SetValue('X');
        
        Helper h = new Helper();

        Pair<Integer> initPos = Utilities.RandomPairI(r, 1, size - 2, 1,
                size - 2);

        h.cp = new CellPos(result.GetCell(initPos), initPos);
        h.cp.Cell.Visit();
        h.cp.Cell.SetValue(' ');
        h.nbrs = GetNeighbors(result, h.cp.Position);
        
        do
        {
            boolean success = false;
            Utilities.RandElement<Pair<CellPos>> nb = null;
            
            while (!success && !h.nbrs.isEmpty())
            {
                nb = Utilities.RandomElement(r, h.nbrs);
                
                if (!nb.Element.first.Cell.WasVisited())
                {
                    if (nb.Element.first.Position.first == 0 || nb.Element.first.Position.second == 0 || nb.Element.first.Position.first == result.GetWidth() - 1 || nb.Element.first.Position.second == result.GetHeight() - 1)
                    {
                        int i = r.nextInt(4);
                        if (i > 0)
                        {
                            nb.Element.second.Cell.SetValue(' ');
                            nb.Element.second.Cell.Visit();
                        }
                        success = false;
                    }
                    else
                    {
                        nb.Element.second.Cell.SetValue(' ');
                        nb.Element.second.Cell.Visit();
                        stk.push(h);
                        success = true;
                    }
                } else { success = false; }
                h.nbrs.remove(nb.Position);
            }
            
            if (success)
            {
                h = new Helper();
                h.cp = nb.Element.first;
                h.cp.Cell.Visit();
                h.cp.Cell.SetValue(' ');
                h.nbrs = GetNeighbors(result, h.cp.Position);
            }
            else
            {
                h = stk.pop();
                h.cp.Cell.Visit();
                h.cp.Cell.SetValue(' ');
            }
        } while (!stk.isEmpty());
        
        result.SetHeroPosition(initPos);
        SetRandomSwordPosition(result, r);
        SetRandomDragonsPosition(result, numDragons, r);
        SetRandomExitPosition(result, r);
        
        return result;
    }

    private static void SetRandomExitPosition(Maze m, Random r)
    {
        List<CellPos> whitelst = new LinkedList<CellPos>();

        for (int x = 1; x < m.GetWidth() - 1; x++)
        {
            int y = 1;
            Cell<Character> cell = m.GetCell(Pair.IntN(x, y));
            if (cell.GetValue() == ' ')
                whitelst.add(new CellPos(m.GetCell(Pair.IntN(x, y-1)), Pair.IntN(x, y-1)));
            
            y = m.GetHeight() - 2;
            cell = m.GetCell(Pair.IntN(x, y));
            if (cell.GetValue() == ' ')
                whitelst.add(new CellPos(m.GetCell(Pair.IntN(x, y+1)), Pair.IntN(x, y+1)));
        }

        for (int y = 2; y < m.GetHeight() - 2; y++)
        {
            int x = 1;
            Cell<Character> cell = m.GetCell(Pair.IntN(x, y));
            if (cell.GetValue() == ' ')
                whitelst.add(new CellPos(m.GetCell(Pair.IntN(x-1, y)), Pair.IntN(x-1, y)));
            
            x = m.GetWidth() - 2;
            cell = m.GetCell(Pair.IntN(x, y));
            if (cell.GetValue() == ' ')
                whitelst.add(new CellPos(m.GetCell(Pair.IntN(x+1, y)), Pair.IntN(x+1, y)));
        }

        m.SetExitPosition(Utilities.RandomElement(r, whitelst).Element.Position);
    }

    private static void SetRandomDragonsPosition(Maze m, int numDragons, Random r)
    {
        for (int i = 0; i < numDragons; i++)
        {
            boolean success;
            List<Pair<CellPos>> lst = GetNeighbors(m, m.GetHeroPosition());
            List<Pair<Integer>> lstn = new LinkedList<Pair<Integer>>();

            for (Pair<CellPos> ele : lst)
            {
                lstn.add(ele.first.Position);
                lstn.add(ele.second.Position);
            }

            do
            {
                Pair<Integer> p = Utilities.RandomPairI(r, 1, m.GetWidth() - 2, 1, m.GetHeight() - 2);

                success = !lstn.contains(p);

                if (success)
                    success = m.SetDragonPosition(i, p);
            } while (!success);
        }
    }

    private static void SetRandomSwordPosition(Maze m, Random r)
    {
        boolean success;
        do
        {
            success = m.SetSwordPosition(Utilities.RandomPairI(r, 1, m.GetWidth() - 2, 1, m.GetHeight() - 2));
        } while (!success);
    }

    // 1s - "cell", 2nd - "wall"
    private static List<Pair<CellPos>> GetNeighbors(Maze m, Pair<Integer> pos)
    {
        List<Pair<CellPos>> l = new LinkedList<Pair<CellPos>>();
        int w = m.GetWidth() - 1;
        int x = pos.first;
        int y = pos.second;

        if (y >= 2)
            l.add(new Pair<CellPos>(
                    new CellPos(m.GetCell(Pair.IntN(x, y - 2)), Pair.IntN(x, y - 2)),    //Cell
                    new CellPos(m.GetCell(Pair.IntN(x, y - 1)), Pair.IntN(x, y - 1))));  //Wall

        if (x >= 2)
            l.add(new Pair<CellPos>(
                    new CellPos(m.GetCell(Pair.IntN(x - 2, y)), Pair.IntN(x - 2, y)),
                    new CellPos(m.GetCell(Pair.IntN(x - 1, y)), Pair.IntN(x - 1, y))));

        if (x <= (w - 2))
            l.add(new Pair<CellPos>(
                    new CellPos(m.GetCell(Pair.IntN(x + 2, y)), Pair.IntN(x + 2, y)),
                    new CellPos(m.GetCell(Pair.IntN(x + 1, y)), Pair.IntN(x + 1, y))));

        if (y <= (w - 2))
            l.add(new Pair<CellPos>(
                    new CellPos(m.GetCell(Pair.IntN(x, y + 2)), Pair.IntN(x, y + 2)),
                    new CellPos(m.GetCell(Pair.IntN(x, y + 1)), Pair.IntN(x, y + 1))));


        return l;
    }
}
