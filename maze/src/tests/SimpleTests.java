package tests;

import static org.junit.Assert.*;
import logic.Architect;
import logic.DefaultMazeGenerator;
import logic.DragonBehaviour;
import logic.Maze;
import logic.MazeGenerator;
import model.Position;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.Key;

public class SimpleTests
{
    private static Architect _architect;
    private static Maze _maze;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MazeGenerator mg = new DefaultMazeGenerator();

        _architect = new Architect();
        _architect.SetMazeGenerator(mg);
    }

    @Before
    public void setUpBeforeTest() /* throws Exception */
    {
        _architect.ConstructMaze(10, 1, DragonBehaviour.Idle);
        _maze = _architect.GetMaze();
    }

    @Test
    public void MoveHeroSuccessTest() // i
    {
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
        assertTrue(_maze.MoveHero(Key.RIGHT));
        assertEquals(new Position(2, 1), _maze.GetHeroPosition());
    }

    @Test
    public void MoveHeroFailureTest() // ii
    {
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
        assertFalse(_maze.MoveHero(Key.UP));
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
    }

    @Test
    public void EquipSwordTest() // iii
    {
        assertFalse(_maze.IsHeroArmed());

        Key[] movements = {
                Key.RIGHT, Key.RIGHT, Key.RIGHT,
                Key.DOWN, Key.DOWN, Key.DOWN, Key.DOWN,
                Key.LEFT, Key.LEFT, Key.LEFT,
                Key.DOWN, Key.DOWN, Key.DOWN
        };

        for (Key k : movements)
        {
            _maze.MoveHero(k);
            _maze.Update();
        }

        assertTrue(_maze.IsHeroArmed());
    }

    @Test
    public void DragonKillHero() // iv
    {
        assertFalse(_maze.IsHeroArmed());

        _maze.MoveHero(Key.DOWN);
        _maze.Update();

        assertFalse(_maze.IsHeroAlive());
    }

    @Test
    public void HeroKillDragonTest() // v
    {
        _maze.EquipHero();
        _maze.MoveHero(Key.DOWN);
        _maze.Update();

        assertFalse(_maze.IsDragonAlive(0));
    }

    @Test
    public void EquipSwordKillDragonAndExitTest() // vi
    {
        EquipSwordTest();

        Key[] movements = {
                Key.UP, Key.UP, Key.UP, Key.UP, // dragon killed
                Key.DOWN,
                Key.RIGHT, Key.RIGHT, Key.RIGHT, Key.RIGHT, Key.RIGHT,
                Key.DOWN, Key.DOWN, Key.DOWN,
                Key.RIGHT, Key.RIGHT,
                Key.UP, Key.UP, Key.UP,
                Key.RIGHT // exited
        };

        for (Key k : movements)
        {
            _maze.MoveHero(k);
            _maze.Update();
        }

        assertTrue(_maze.IsHeroAlive());
        assertTrue(_maze.IsFinished());
    }

    @Test
    public void MoveToExitWithoutDragonKilledOrSwordTest() // vii
    {
        Key[] movements = {
                Key.RIGHT, Key.RIGHT, Key.RIGHT, Key.RIGHT, Key.RIGHT, Key.RIGHT, Key.RIGHT,
                Key.DOWN, Key.DOWN, Key.DOWN, Key.DOWN,
                Key.RIGHT
        };

        for (Key k : movements)
        {
            _maze.MoveHero(k);
            _maze.Update();
        }

        assertTrue(_maze.IsHeroAlive());
        assertFalse(_maze.IsFinished());
    }
}
