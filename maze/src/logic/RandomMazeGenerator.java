package logic;

import java.util.Stack;

import utils.Pair;
import utils.RandomEngine;
import utils.Utilities;

/** ConcreteBuilder - Randomised NxN maze */
public class RandomMazeGenerator extends MazeGenerator
{
    @Override
    public void BuildMaze()
    {
        Stack<CellNeighbors> stk = new Stack<CellNeighbors>();

        for (int i = 0; i < _maze.GetWidth(); i++)
            for (int j = 0; j < _maze.GetHeight(); j++)
                _maze.GetCell(Pair.IntN(i, j)).SetValue('X');

        CellNeighbors h = new CellNeighbors();

        Pair<Integer> initPos = Utilities.RandomPairI(1, _size - 2, 1, _size - 2);

        h.cp = new CellPos(_maze.GetCell(initPos), initPos);
        h.cp.Cell.Visit();
        h.cp.Cell.SetValue(' ');
        h.nbrs = GetNeighbors(h.cp.Position);

        do
        {
            boolean success = false;
            Utilities.RandElement<Pair<CellPos>> nb = null;

            while (!success && !h.nbrs.isEmpty())
            {
                nb = Utilities.RandomElement(h.nbrs);

                if (!nb.Element.first.Cell.WasVisited())
                {
                    if (nb.Element.first.Position.first == 0 ||
                            nb.Element.first.Position.second == 0 ||
                            nb.Element.first.Position.first == _maze.GetWidth() - 1 ||
                            nb.Element.first.Position.second == _maze.GetHeight() - 1)
                    {
                        if (RandomEngine.GetBool(75))
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
                }
                else
                    success = false;

                h.nbrs.remove(nb.Position);
            }

            if (success)
            {
                h = new CellNeighbors();
                h.cp = nb.Element.first;
                h.cp.Cell.Visit();
                h.cp.Cell.SetValue(' ');
                h.nbrs = GetNeighbors(h.cp.Position);
            }
            else
            {
                h = stk.pop();
                h.cp.Cell.Visit();
                h.cp.Cell.SetValue(' ');
            }
        } while (!stk.isEmpty());

        _maze.SetHeroPosition(initPos);
        _maze.SetEaglePosition(initPos, false);
        SetRandomSwordPosition();
        SetRandomDragonsPosition();
        SetRandomExitPosition();
    }
}
