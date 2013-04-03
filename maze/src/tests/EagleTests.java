package tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import logic.Architect;
import logic.DefaultMazeGenerator;
import logic.Direction;
import logic.Dragon;
import logic.Eagle;
import logic.Hero;
import logic.Maze;
import logic.MazeGenerator;
import model.Position;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.Key;

/**
 * The Class EagleTests (3.3)
 */
public class EagleTests
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
    public void setUpBeforeTest()
    {
        _architect.ConstructMaze(10, 1, Dragon.Behaviour.Idle);
        _maze = _architect.GetMaze();

        Eagle e = new Eagle();
        Hero h = _maze.FindHero();
        e.SetPosition(new Position(h.GetPosition().X, h.GetPosition().Y));
        _maze.AddWorldObject(e);
    }

    /**
     * Eagle initially on hero test.
     */
    @Test
    public void EagleInitiallyOnHeroTest()
    {
        // inicialmente a águia está poisada no braço do herói e acompanha-o;
        assertThat(_maze.FindEagle().GetPosition(), is(_maze.FindHero().GetPosition()));
        _maze.MoveHero(Direction.FromKey(Key.RIGHT));
        assertThat(_maze.FindEagle().GetPosition(), is(_maze.FindHero().GetPosition()));
    }

    /**
     * Eagle flight test.
     */
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
            _maze.MoveHero(Direction.FromKey(k));
            _maze.Update();
        }

        _maze.SendEagleToSword();

        _maze.Update();
        _maze.Update();

        assertThat(_maze.FindEagle().GetPosition(), is(not(_maze.FindHero().GetPosition())));

        // quando está a voar, a águia pode estar sobre qualquer quadrícula;
        _maze.Update();
        _maze.Update();

        assertThat(_maze.GetGrid().GetCellT(_maze.FindEagle().GetPosition()).IsWall(), is(true));
    }

    /**
     * Eagle reach sword test.
     */
    @Test
    public void EagleReachSwordTest()
    {
        // quando chega à quadrícula da espada, a águia desce para apanhar a espada (se ainda aí estiver);

        _maze.SendEagleToSword();

        for (int i = 0; i < 9; ++i)
            _maze.Update();

        assertThat(_maze.FindEagle().IsAlive(), is(true));
        assertThat(_maze.FindEagle().IsArmed(), is(true));
    }

    /**
     * Eagle reach unexisting sword test.
     */
    @Test
    public void EagleReachUnexistingSwordTest()
    {
        // quando chega à quadrícula da espada, a águia desce para apanhar a espada (se ainda aí estiver);

        _maze.SendEagleToSword();

        for (int i = 0; i < 4; ++i)
            _maze.Update();


        _maze.FindSword().Kill();

        for (int i = 0; i < 5; ++i)
            _maze.Update();


        assertThat(_maze.FindEagle().IsAlive(), is(true));
        assertThat(_maze.FindEagle().IsArmed(), is(false));
    }

    /**
     * Eagle reach sword with dragon test.
     */
    @Test
    public void EagleReachSwordWithDragonTest()
    {

        // quando chega à quadrícula da espada, a águia desce para apanhar a espada (se ainda aí estiver);
        //  se um dragão estive acordado nessa posição ou adjacente, mata a águia;

        _maze.FindDragons().get(0).SetPosition(new Position(1, 7));

        _maze.SendEagleToSword();

        for (int i = 0; i < 9; ++i)
            _maze.Update();

        assertNull(_maze.FindEagle());
    }

    /**
     * Eagle flight back test.
     */
    @Test
    public void EagleFlightBackTest()
    {
        // assim que pega a espada, a águia levanta voo de novo em direção à posição de partida
        //  (onde levantou voo do braço do herói);

        Position takeOffPos = _maze.FindEagle().GetPosition();

        _maze.SendEagleToSword();

        _maze.MoveHero(Direction.FromKey(Key.RIGHT));

        for (int i = 0; i < 9; ++i)
            _maze.Update();

        assertThat(_maze.FindEagle().IsAlive(), is(true));
        assertThat(_maze.FindEagle().IsArmed(), is(true));

        for (int i = 0; i < 7; ++i)
            _maze.Update();

        assertThat(_maze.FindEagle().GetPosition(), is(takeOffPos));
        assertThat(_maze.FindEagle().IsAlive(), is(true));
        assertThat(_maze.FindEagle().IsArmed(), is(true));

        _maze.MoveHero(Direction.FromKey(Key.LEFT));

        _maze.Update();

        assertThat(_maze.FindEagle().IsArmed(), is(false));
        assertThat(_maze.FindHero().IsArmed(), is(true));
    }

    /**
     * Eagle flight back catch by hero test.
     */
    @Test
    public void EagleFlightBackCatchByHeroTest()
    {
        // voltando à posição de partida, se não estiver aí o herói, a águia permanece no solo até o
        //  herói a apanhar, correndo o risco de ser morta por um dragão.

        _maze.SendEagleToSword();

        _maze.MoveHero(Direction.FromKey(Key.RIGHT));

        for (int i = 0; i < 20; ++i)
            _maze.Update();

        _maze.MoveHero(Direction.FromKey(Key.LEFT));
        _maze.Update();

        assertThat(_maze.FindEagle().IsAlive(), is(true));
        assertThat(_maze.FindEagle().IsArmed(), is(false));
        assertThat(_maze.FindHero().IsArmed(), is(true));
    }

    /**
     * Eagle flight back with dragon.
     */
    @Test
    public void EagleFlightBackWithDragon()
    {
        // voltando à posição de partida, se não estiver aí o herói, a águia permanece no solo até o
        //  herói a apanhar, correndo o risco de ser morta por um dragão.

        _maze.SendEagleToSword();

        _maze.MoveHero(Direction.FromKey(Key.RIGHT));
        _maze.MoveHero(Direction.FromKey(Key.RIGHT));

        for (int i = 0; i < 10; ++i)
            _maze.Update();

        Dragon d = _maze.FindDragons().get(0);
        d.SetPosition(new Position(d.GetPosition().X, d.GetPosition().Y-1));

        for (int i = 0; i < 8; ++i)
            _maze.Update();

        assertNull(_maze.FindEagle());
    }
}
