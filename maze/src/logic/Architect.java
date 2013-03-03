package logic;

import java.util.Random;

/** Director */
public class Architect
{
    private MazeGenerator _mazeGenerator;
    
    public void SetMazeGenerator(MazeGenerator mg) { _mazeGenerator = mg; }
    public Maze GetMaze() { return _mazeGenerator.GetMaze(); }
    
    public void ConstructMaze(Random r, int size, int dragonCount, DragonBehaviour dragonBehaviour)
    {
        _mazeGenerator.CreateNewMaze(r);
        _mazeGenerator.SetMazeSize(size);
        _mazeGenerator.SetNumberOfDragons(dragonCount);
        _mazeGenerator.SetDragonsBehaviour(dragonBehaviour);
        _mazeGenerator.BuildMaze();
    }
}
