package ui.gui.graphical;

import logic.Maze;

public interface MazeGame
{
    Maze GetMaze();
    void SetMaze(Maze m);
    void repaint();
}
