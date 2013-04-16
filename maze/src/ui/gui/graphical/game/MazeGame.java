package ui.gui.graphical.game;

import logic.Maze;

/**
 * The Interface MazeGame.
 */
public interface MazeGame
{
    /**
     * Gets the maze.
     *
     * @return the maze
     */
    Maze GetMaze();

    /**
     * Sets the maze.
     *
     * @param m the m
     */
    void SetMaze(Maze m);

    void ResetGame();

    /**
     * Repaint.
     */
    void repaint();
}
