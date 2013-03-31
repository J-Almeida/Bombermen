package logic;

/** Director - responsible for managing the correct sequence of Maze creation */
public class Architect
{
    /** Underlying MazeGenerator */
    private MazeGenerator _mazeGenerator;

    /** Defines the concrete MazeGenerator */
    public void SetMazeGenerator(MazeGenerator mg) { _mazeGenerator = mg; }
    /** Returns the concrete MazeGenerator, call after ConstructMaze */
    public Maze GetMaze() { return _mazeGenerator.GetMaze(); }

    /** Builds the Maze */
    public void ConstructMaze(int size, int dragonCount, Dragon.Behaviour dragonBehaviour)
    {
        _mazeGenerator.SetMazeSize(size);
        _mazeGenerator.CreateNewMaze();

        _mazeGenerator.SetNumberOfDragons(dragonCount);
        _mazeGenerator.SetDragonsBehaviour(dragonBehaviour);

        _mazeGenerator.BuildMaze();
    }
}
