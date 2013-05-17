package tests;

import logic.Bomb;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BombTests {

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
	public void BombTimerTest()
	{
		Bomb b = _gameState.GetObjectBuilder().CreateBomb(_gameState.GetCurrentPlayer(), 10, 10, 1);
		_gameState.Update(500);
		assert(!b.ShouldExplode());
		_gameState.Update(500);
		assert(b.ShouldExplode());
	}

}
