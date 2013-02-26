package pg1;

import java.util.Random;
import java.util.Scanner;

import utils.Key;
import utils.Pair;

public class Game
{
    private static Maze _maze;
    private static Scanner _sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        String[] cells = {  "XXXXXXXXXX",
                            "XH       X",
                            "X XX X X X",
                            "XDXX X X X",
                            "X XX X X X",
                            "X      X S",
                            "X XX X X X",
                            "X XX X X X",
                            "XEXX     X",
                            "XXXXXXXXXX" };

        _maze = new Maze(10, 10, cells);

        _maze.SetHeroPosition(new Pair<Integer>(1, 1));
        _maze.SetSwordPosition(new Pair<Integer>(1, 8));
        _maze.SetDragonPosition(new Pair<Integer>(1, 3));
        _maze.SetExit(new Pair<Integer>(9, 5));

        while (!_maze.IsFinished())
        {
            boolean success = true;
            boolean dragonAlivePast = _maze.IsDragonAlive();
            System.out.println(_maze);
            System.out.print("Move hero (W, S, A, D): ");
            do
            {
                if (!success)
                {
                    System.out.print("Invalid move. Try again: ");
                    success = true;
                }

                String inputStr = _sc.nextLine();
                if (inputStr.length() > 1) { success = false; continue; }

                Key input = Key.toEnum(inputStr.toUpperCase().charAt(0));

                if (input == null) { success = false; continue; }

                success = _maze.MoveHero(input);

            } while (!success);

            System.out.print("\n\n");

            success = !_maze.IsDragonAlive();

            if (success && dragonAlivePast)
                System.out.println("Congratulations you killed the dragon!");

            while (!success)
            {
                Random r = new Random();
                Key dir = Key.toEnum(r.nextInt(5));
                success = (dir == null) || _maze.MoveDragon(dir);
            }
        }

        System.out.println(_maze);
        if (_maze.IsHeroAlive())
            System.out.println("You Won!");
        else
            System.out.println("You Lost!");

    }
}
