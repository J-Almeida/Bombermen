package ui.cli;

import java.util.Scanner;

import logic.Architect;
import logic.DragonBehaviour;
import logic.Maze;
import logic.MazeGenerator;
import logic.RandomMazeGenerator;
import utils.Key;

/**
 * The Class CLI.
 */
public class CLI
{
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
        Architect architect = new Architect();
        MazeGenerator mg = new RandomMazeGenerator();

        architect.SetMazeGenerator(mg);
        architect.ConstructMaze(30, 2, DragonBehaviour.Sleepy);

        Maze maze = architect.GetMaze();

        Scanner sc = new Scanner(System.in);

        while (!maze.IsFinished())
        {
            boolean success = true;
            System.out.println(maze);
            System.out.print("Move hero (W, S, A, D, 1): ");
            do
            {
                if (!success)
                {
                    System.out.print("Invalid move. Try again: ");
                    success = true;
                }

                String inputStr = sc.nextLine();
                if (inputStr.isEmpty() || inputStr.length() > 1)
                {
                    success = false;
                    continue;
                }

                Key input = Key.toEnum(inputStr.toUpperCase().charAt(0));

                if (input == null)
                {
                    success = false;
                    continue;
                }

                if (input == Key.ONE)
                {
                    success = true;
                    maze.SendEagleToSword();
                }
                else
                {
                    success = maze.MoveHero(input);
                }

            } while (!success);

            System.out.print("\n\n");

            success = false;

            maze.Update();
        }

        sc.close();

        System.out.println(maze);
        if (maze.IsHeroAlive())
            System.out.println("You Won!");
        else
            System.out.println("You Lost!");
    }
}
