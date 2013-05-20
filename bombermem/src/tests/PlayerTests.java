package tests;

import java.awt.geom.Point2D;

import logic.Player;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.Key;

public class PlayerTests {

     private TestGameState _gameState;

     @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {

    }

     @Before
     public void setUpBeforeTest()
     {
          _gameState = new TestGameState();
     }

     @Test
     public void PlayerMovementSuccess()
     {
          Player p = _gameState.GetCurrentPlayer();
          Point2D prevPos = (Point2D) p.Position.clone();
          _gameState.PressKey(Key.RIGHT);
          _gameState.Update(1);
          assert(!p.Position.equals(prevPos));
          assert(p.Position.getX() > prevPos.getX());
          assert(p.Position.getY() == prevPos.getY());
     }

     @Test
     public void PlayerMovementNotSuccess()
     {
          Player p = _gameState.GetCurrentPlayer();
          Point2D prevPos = (Point2D) p.Position.clone();
          _gameState.PressKey(Key.LEFT);
          _gameState.Update(1);
          assert(p.Position.equals(prevPos));
     }

}
