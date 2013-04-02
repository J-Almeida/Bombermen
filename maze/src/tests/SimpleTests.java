package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import logic.Architect;
import logic.DefaultMazeGenerator;
import logic.Direction;
import logic.Dragon;
import logic.Hero;
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
        _architect.ConstructMaze(10, 1, Dragon.Behaviour.Idle);
        _maze = _architect.GetMaze();
    }

    /**
     * Move hero success test.
     */
    @Test
    public void MoveHeroSuccessTest() // i
    {
        Hero h = _maze.FindHero();
        assertEquals(new Position(1, 1), h.GetPosition());
        _maze.MoveHero(Direction.FromKey(Key.RIGHT));
        _maze.Update();
        assertEquals(new Position(2, 1), h.GetPosition());
    }

    /**
     * Move hero failure test.
     */
    @Test
    public void MoveHeroFailureTest() // ii
    {
        assertEquals(new Position(1, 1), _maze.FindHero().GetPosition());
        _maze.MoveHero(Direction.FromKey(Key.UP));
        assertEquals(new Position(1, 1), _maze.FindHero().GetPosition());
    }

    /**
     * Equip sword test.
     */
    @Test
    public void EquipSwordTest() // iii
    {
        assertFalse(_maze.FindHero().IsArmed());

        Key[] movements = {
                Key.RIGHT, Key.RIGHT, Key.RIGHT,
                Key.DOWN, Key.DOWN, Key.DOWN, Key.DOWN,
                Key.LEFT, Key.LEFT, Key.LEFT,
                Key.DOWN, Key.DOWN, Key.DOWN
        };

        for (Key k : movements)
        {
            _maze.MoveHero(Direction.FromKey(k));
            _maze.Update();
        }

        _maze.Update();

        assertTrue(_maze.FindHero().IsArmed());
    }

    /**
     * Dragon kill hero.
     */
    @Test
    public void DragonKillHero() // iv
    {
        assertFalse(_maze.FindHero().IsArmed());

        _maze.MoveHero(Direction.FromKey(Key.DOWN));
        _maze.Update();

        assertTrue(_maze.FindHero() == null);
    }

    /**
     * Hero kill dragon test.
     */
    @Test
    public void HeroKillDragonTest() // v
    {
        _maze.FindHero().EquipSword(true);
        _maze.MoveHero(Direction.FromKey(Key.DOWN));
        _maze.Update();

        assertTrue(_maze.FindDragons().isEmpty());
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
            _maze.MoveHero(Direction.FromKey(k));
            _maze.Update();
        }
        _maze.Update();

        assertTrue(_maze.FindHero().IsAlive());
        assertTrue(_maze.IsFinished());
        //System.out.println(_maze);
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
            _maze.MoveHero(Direction.FromKey(k));
            _maze.Update();
        }

        assertTrue(_maze.FindHero().IsAlive());
        assertFalse(_maze.IsFinished());
    }
}
