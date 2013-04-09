package logic;

import model.Position;

/**
 * ConcreteBuilder - Empty maze with only outter walls.
 */
public class EmptyMazeGenerator extends MazeGenerator
{
    /* (non-Javadoc)
     * @see logic.MazeGenerator#BuildMaze()
     */
    @Override
    public void BuildMaze()
    {
        for (int i = 0; i < _maze.GetWidth(); i++)
        {
            for (int j = 0; j < _maze.GetHeight(); j++)
            {
                boolean wall = i == 0 || i == _maze.GetWidth() - 1 || j == 0 || j == _maze.GetHeight() - 1; // borders

                WorldObject wo = wall ? new Wall() : new Path();
                wo.SetPosition(new Position(i, j));
                _maze.AddWorldObject(wo);
            }
        }
    }
}
