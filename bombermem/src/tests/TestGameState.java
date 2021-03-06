package tests;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import logic.GameState;
import logic.Player;
import logic.WorldObject;
import utils.Key;

public class TestGameState extends GameState {

     Player _player = null;

     public TestGameState()
     {
          _entities = new HashMap<Integer, WorldObject>();

          _objectBuilder = new TestWorldObjectBuilder();
        _objectBuilder.SetGameState(this);

        _player = _objectBuilder.CreatePlayer("test", new Point(1, 1));

        for (int x = 0; x < 50; ++x)
        {
            for (int y = 0; y < 50; ++y)
            {
                if (x == 0 || x == 47 || y == 0 || y == 47 || (x % 2 == 0 && y % 2 == 0))
                    _objectBuilder.CreateWall(-1, new Point(x, y));
            }
        }

        Initialize();
     }

     public Player GetCurrentPlayer()
     {
          return _player;
     }

     @Override
     public void keyPressed(KeyEvent arg0)
     {
          // TODO Auto-generated method stub
     }

     @Override
     public void keyReleased(KeyEvent arg0)
     {
          // TODO Auto-generated method stub
     }

     @Override
     public void keyTyped(KeyEvent arg0)
     {
          // TODO Auto-generated method stub
     }

     public void PressKey(Key k)
     {
          _pressedKeys.put(k, true);
     }

     public void ReleaseKey(Key k)
     {
          _pressedKeys.put(k, false);
     }
}
