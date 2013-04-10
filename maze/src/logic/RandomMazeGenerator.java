package logic;

import java.util.Stack;

import model.Position;
import utils.Pair;
import utils.RandomEngine;
import utils.Utilities;

/**
 * ConcreteBuilder - Randomized NxN maze (using Depth First Search with backtracking).
 */
public class RandomMazeGenerator extends MazeGenerator
{
    /* (non-Javadoc)
     * @see logic.MazeGenerator#BuildMaze()
     */
    @Override
    public void BuildMaze()
    {
        Stack<CellNeighbors> stk = new Stack<CellNeighbors>();

        for (int i = 0; i < _maze.GetWidth(); i++)
            for (int j = 0; j < _maze.GetHeight(); j++)
                _maze.GetGrid().GetCell(new Position(i, j)).SetValue(Maze.WALL);

        CellNeighbors h = new CellNeighbors();

        Position initPos = Utilities.RandomPosition(1, _size - 2, 1, _size - 2);

        h.cp = new CellPos(_maze.GetGrid().GetCell(initPos), initPos);
        h.cp.Cell.Visit();
        h.cp.Cell.SetValue(Maze.PATH);
        h.nbrs = GetNeighbors(h.cp.Pos);

        do
        {
            boolean success = false;
            Utilities.RandElement<Pair<CellPos>> nb = null;

            while (!success && !h.nbrs.isEmpty())
            {
                nb = Utilities.RandomElement(h.nbrs);

                if (!nb.Element.first.Cell.WasVisited())
                {
                    if (nb.Element.first.Pos.X == 0 ||
                            nb.Element.first.Pos.Y == 0 ||
                            nb.Element.first.Pos.X == _maze.GetWidth() - 1 ||
                            nb.Element.first.Pos.Y == _maze.GetHeight() - 1)
                    {
                        if (RandomEngine.GetBool(75))
                        {
                            nb.Element.second.Cell.SetValue(Maze.PATH);
                            nb.Element.second.Cell.Visit();
                        }
                        success = false;
                    }
                    else
                    {
                        nb.Element.second.Cell.SetValue(Maze.PATH);
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
                h.cp.Cell.SetValue(Maze.PATH);
                h.nbrs = GetNeighbors(h.cp.Pos);
            }
            else
            {
                h = stk.pop();
                h.cp.Cell.Visit();
                h.cp.Cell.SetValue(Maze.PATH);
            }
        } while (!stk.isEmpty());

        Hero hero = new Hero();
        hero.SetPosition(new Position(initPos.X, initPos.Y));
        _maze.AddWorldObject(hero);

        Eagle eagle = new Eagle();
        eagle.SetPosition(new Position(initPos.X, initPos.Y));
        _maze.AddWorldObject(eagle);

        SetRandomSwordPosition();
        SetRandomDragonsPosition();
        SetRandomExitPosition();
    }
}
