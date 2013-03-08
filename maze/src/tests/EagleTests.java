package tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import logic.Architect;
import logic.DefaultMazeGenerator;
import logic.DragonBehaviour;
import logic.Maze;
import logic.MazeGenerator;
import logic.Wall;
import model.Position;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.Key;

public class EagleTests
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
    public void EagleInitiallyOnHeroTest()
    {
        // inicialmente a águia está poisada no braço do herói e acompanha-o;
        assertThat(_maze.GetEaglePosition(), is(_maze.GetHeroPosition()));
        assertThat(_maze.MoveHero(Key.RIGHT), is(true));
        assertThat(_maze.GetEaglePosition(), is(_maze.GetHeroPosition()));
    }
    
    @Test
    public void EagleFlightTest()
    {
        // por ordem do herói, a águia pode levantar voo em direção à espada,
        //  pelo caminho mais próximo possível de uma linha reta;

        Key[] movements1 = {
            Key.RIGHT, Key.RIGHT, Key.RIGHT
        };

        for (Key k : movements1)
        {
            _maze.MoveHero(k);
            _maze.Update();
        }

        _maze.SendEagleToSword();

        _maze.Update();
        _maze.Update();
        
        assertThat(_maze.GetEaglePosition(), is(not(_maze.GetHeroPosition())));

        // quando está a voar, a águia pode estar sobre qualquer quadrícula;
        _maze.Update();
        _maze.Update();

        assertThat(_maze.GetCell(_maze.GetEaglePosition()).GetValue(), is(instanceOf(Wall.class)));
    }
    
    @Test
    public void EagleReachSwordTest()
    {
        // quando chega à quadrícula da espada, a águia desce para apanhar a espada (se ainda aí estiver);
        
        _maze.SendEagleToSword();
        
        for (int i = 0; i < 9; ++i)
            _maze.Update();

        assertThat(_maze.IsEagleAlive(), is(true));
        assertThat(_maze.IsEagleArmed(), is(true));
    }
    
    @Test
    public void EagleReachUnexistingSwordTest()
    {
        // quando chega à quadrícula da espada, a águia desce para apanhar a espada (se ainda aí estiver);
        
        _maze.SendEagleToSword();

        for (int i = 0; i < 4; ++i)
            _maze.Update();
        
        _maze.SetSwordPosition(new Position(-1, -1));
        
        for (int i = 0; i < 5; ++i)
            _maze.Update();
        
        assertThat(_maze.IsEagleAlive(), is(true));
        assertThat(_maze.IsEagleArmed(), is(false));
    }
    
    @Test
    public void EagleReachSwordWithDragonTest()
    {
        // quando chega à quadrícula da espada, a águia desce para apanhar a espada (se ainda aí estiver); 
        //  se um dragão estive acordado nessa posição ou adjacente, mata a águia;

        _maze.SetDragonPosition(0, new Position(1, 7));

        _maze.SendEagleToSword();
        
        for (int i = 0; i < 9; ++i)
            _maze.Update();

        assertThat(_maze.IsEagleAlive(), is(false));
    }
    
    @Test
    public void EagleFlightBackTest()
    {
        // assim que pega a espada, a águia levanta voo de novo em direção à posição de partida
        //  (onde levantou voo do braço do herói);

        Position takeOffPos = _maze.GetEaglePosition();
        
        _maze.SendEagleToSword();
        
        //_maze.MoveHero(Key.RIGHT);
        
        for (int i = 0; i < 9; ++i)
            _maze.Update();

        assertThat(_maze.IsEagleAlive(), is(true));
        assertThat(_maze.IsEagleArmed(), is(true));
        
        for (int i = 0; i < 7; ++i)
            _maze.Update();

        assertThat(_maze.GetEaglePosition(), is(takeOffPos));
        assertThat(_maze.IsEagleAlive(), is(true));
        assertThat(_maze.IsEagleArmed(), is(true));
        
        _maze.Update();
        
        assertThat(_maze.IsEagleArmed(), is(false));
        assertThat(_maze.IsHeroArmed(), is(true));
    }
    
    @Test
    public void EagleFlightBackCatchByHeroTest()
    {
        // voltando à posição de partida, se não estiver aí o herói, a águia permanece no solo até o 
        //  herói a apanhar, correndo o risco de ser morta por um dragão.
        
        _maze.SendEagleToSword();
        
        _maze.MoveHero(Key.RIGHT);
        
        for (int i = 0; i < 20; ++i)
            _maze.Update();
        
        _maze.MoveHero(Key.LEFT);
        _maze.Update();
        
        assertThat(_maze.IsEagleAlive(), is(true));
        assertThat(_maze.IsEagleArmed(), is(false));
        assertThat(_maze.IsHeroArmed(), is(true));
    }
    
    @Test
    public void EagleFlightBackWithDragon()
    {
        // voltando à posição de partida, se não estiver aí o herói, a águia permanece no solo até o 
        //  herói a apanhar, correndo o risco de ser morta por um dragão.
        
        _maze.SendEagleToSword();
        
        _maze.MoveHero(Key.RIGHT);
        _maze.MoveHero(Key.RIGHT);
        
        for (int i = 0; i < 10; ++i)
            _maze.Update();
        
        _maze.MoveDragon(0, Key.UP);
        
        for (int i = 0; i < 8; ++i)
            _maze.Update();
        
        assertThat(_maze.IsEagleAlive(), is(false));
    }
}
