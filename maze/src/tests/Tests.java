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
import utils.Pair;

public class Tests
{
    private static Maze _maze;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        Architect architect = new Architect();
        MazeGenerator mg = new DefaultMazeGenerator();

        architect.SetMazeGenerator(mg);
        architect.ConstructMaze(10, 1, DragonBehaviour.Idle);
        
        _maze = architect.GetMaze();
    }
    
    @Before
    public void setUpBeforeTest() /* throws Exception */
    {
        _maze.SetHeroPosition(new Position(1, 1));
        _maze.SetDragonPosition(0, new Position(1, 3));
        _maze.SetSwordPosition(new Position(1, 8));
        _maze.GetHero().UnequipSword();
    }

    @Test
    public void MoveHeroSuccessTest()
    {
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
        assertTrue(_maze.MoveHero(Key.RIGHT));
        assertEquals(new Position(2, 1), _maze.GetHeroPosition());
    }
    
    @Test
    public void MoveHeroFailureTest()
    {
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
        assertFalse(_maze.MoveHero(Key.UP));
        assertEquals(new Position(1, 1), _maze.GetHeroPosition());
    }

    @Test
    public void EquipSwordTest()
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
    public void GetHeroKilledTest()
    {
        _maze.MoveHero(Key.DOWN);
        _maze.Update();
        
        assertFalse(_maze.IsHeroAlive());
    }
    
    @Test
    public void HeroKillDragonTest()
    {
        fail("SOMETHING IS WRONG HERE!!!111!!!oeneone");
        _maze.GetHero().EquipSword();

        //_maze.MoveHero(Key.DOWN);
        _maze.Update();

        assertFalse(_maze.IsDragonAlive(0));
    }
}
