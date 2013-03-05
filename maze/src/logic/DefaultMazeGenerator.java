package logic;

import utils.Pair;

/** ConcreteBuilder - Default 10x10 maze */
public class DefaultMazeGenerator extends MazeGenerator
{
    @Override
    public void BuildMaze()
    {
        String[] cells = { "XXXXXXXXXX",
                           "XH       X",
                           "X XX X X X",
                           "X XX X X X",
                           "X XX X X X",
                           "X      X S",
                           "X XX X X X",
                           "X XX X X X",
                           "X XX     X",
                           "XXXXXXXXXX" };
        
        for (int i = 0; i < _size; i++) // width
        {
            for (int j = 0; j < _size; j++) // height
            {
                char c = cells[j].charAt(i);
                if (c == 'H')
                {
                    if (!_maze.GetHeroPosition().equals(Unit.DEFAULT_POSITION))
                        throw new IllegalArgumentException();

                    _maze.SetHeroPosition(Pair.IntN(i, j));
                }
                else if (c == 'A')
                {
                    if (!_maze.GetSwordPosition().equals(Unit.DEFAULT_POSITION) || !_maze.GetHeroPosition().equals(Unit.DEFAULT_POSITION))
                        throw new IllegalArgumentException();

                    _maze.HeroEquipSword();
                    _maze.SetHeroPosition(Pair.IntN(i, j));
                }
                else if (c == 'E')
                {
                    if (!_maze.GetSwordPosition().equals(Unit.DEFAULT_POSITION) || _maze.IsHeroArmed())
                        throw new IllegalArgumentException();

                    _maze.SetSwordPosition((Pair.IntN(i, j)));
                }
                else if (c == 'F')
                {
                    if (!_maze.GetSwordPosition().equals(Unit.DEFAULT_POSITION) || _maze.IsHeroArmed())
                        throw new IllegalArgumentException();

                    _maze.SetSwordPosition(Pair.IntN(i, j));
                    _maze.SetDragonPosition(_maze.AddDragon(), Pair.IntN(i, j));
                }
                else if (c == 'D')
                {
                    _maze.SetDragonPosition(_maze.AddDragon(), Pair.IntN(i, j));
                }
                else if (c == 'S')
                {
                    if (!_maze.GetExitPosition().equals(Unit.DEFAULT_POSITION))
                        throw new IllegalArgumentException();

                    _maze.SetExitPosition(Pair.IntN(i, j));
                }
                else if (c != 'X' && c != ' ')
                    throw new IllegalArgumentException();

                if (c != 'X' && c != ' ')
                    c = ' ';

                _maze.SetCell(Pair.IntN(i, j), c == ' ' ? Maze.PATH : Maze.WALL);
            }
        }
    }
}
