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

/**
 * The Class SimpleTests (3.1)
 */
public class SimpleTests
{
    /** The architect. */
    private static Architect _architect;

    /** The maze. */
    private static Maze _maze;

    /**
     * Sets the up before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MazeGenerator mg = new DefaultMazeGenerator();

        _architect = new Architect();
        _architect.SetMazeGenerator(mg);
    }

    /**
     * Sets the up before test.
     */
    @Before
    public void setUpBeforeTest() /* throws Exception */
    {
        _architect.ConstructMaze(10, 1, DragonBehaviour.Idle);
        _maze = _architect.GetMaze();
    }

    /**
     * Move hero success test.
     */
    @Test
    public void MoveHeroSuccessTest() // i
    {
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
        assertTrue(_maze.MoveHero(Key.RIGHT));
        assertEquals(new Position(2, 1), _maze.GetHeroPosition());
    }

    /**
     * Move hero failure test.
     */
    @Test
    public void MoveHeroFailureTest() // ii
    {
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
        assertFalse(_maze.MoveHero(Key.UP));
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
    }

    /**
     * Equip sword test.
     */
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

    /**
     * Dragon kill hero.
     */
    @Test
    public void DragonKillHero() // iv
    {
        assertFalse(_maze.IsHeroArmed());

        _maze.MoveHero(Key.DOWN);
        _maze.Update();

        assertFalse(_maze.IsHeroAlive());
    }

    /**
     * Hero kill dragon test.
     */
    @Test
    public void HeroKillDragonTest() // v
    {
        _maze.EquipHero();
        _maze.MoveHero(Key.DOWN);
        _maze.Update();

        assertFalse(_maze.IsDragonAlive(0));
    }

    /**
     * Equip sword kill dragon and exit test.
     */
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

    /**
     * Move to exit without dragon killed or sword test.
     */
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
