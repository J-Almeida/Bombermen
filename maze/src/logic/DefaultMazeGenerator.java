package logic;

import model.Position;

/** ConcreteBuilder - Default 10x10 maze */
public class DefaultMazeGenerator extends MazeGenerator
{
    @Override
    public void BuildMaze()
    {
        String[] cells = { "XXXXXXXXXX",
                           "XH       X",
                           "X XX X X X",
                           "XDXX X X X",
                           "X XX X X X",
                           "X      X S",
                           "X XX X X X",
                           "X XX X X X",
                           "XEXX     X",
                           "XXXXXXXXXX" };
        
        for (int x = 0; x < _size; x++) // width
        {
            for (int y = 0; y < _size; y++) // height
            {
                char c = cells[y].charAt(x);
                if (c == 'H')
                {
                    if (!_maze.GetHeroPosition().equals(Unit.DEFAULT_POSITION))
                        throw new IllegalArgumentException();

                    _maze.SetHeroPosition(new Position(x, y));
                }
                else if (c == 'A')
                {
                    if (!_maze.GetSwordPosition().equals(Unit.DEFAULT_POSITION) || !_maze.GetHeroPosition().equals(Unit.DEFAULT_POSITION))
                        throw new IllegalArgumentException();

                    _maze.HeroEquipSword();
                    _maze.SetHeroPosition(new Position(x, y));
                }
                else if (c == 'E')
                {
                    if (!_maze.GetSwordPosition().equals(Unit.DEFAULT_POSITION) || _maze.IsHeroArmed())
                        throw new IllegalArgumentException();

                    _maze.SetSwordPosition((new Position(x, y)));
                }
                else if (c == 'F')
                {
                    if (!_maze.GetSwordPosition().equals(Unit.DEFAULT_POSITION) || _maze.IsHeroArmed())
                        throw new IllegalArgumentException();

                    _maze.SetSwordPosition(new Position(x, y));
                    _maze.SetDragonPosition(_maze.AddDragon(), new Position(x, y));
                }
                else if (c == 'D')
                {
                    _maze.SetDragonPosition(_maze.AddDragon(), new Position(x, y));
                }
                else if (c == 'S')
                {
                    if (!_maze.GetExitPosition().equals(Unit.DEFAULT_POSITION))
                        throw new IllegalArgumentException();

                    _maze.SetExitPosition(new Position(x, y));
                }
                else if (c != 'X' && c != ' ')
                    throw new IllegalArgumentException();

                if (c != 'X' && c != ' ')
                    c = ' ';

                _maze.SetCell(new Position(x, y), c == ' ' ? Maze.PATH : Maze.WALL);
            }
        }
    }
}
