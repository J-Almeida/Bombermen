package tests;

import java.awt.geom.Point2D;

import logic.Bomb;
import logic.GameState;
import logic.IWorldObjectBuilder;
import logic.Player;
import logic.PowerUp;
import logic.Wall;
import logic.WorldObject;

public class TestWorldObjectBuilder implements IWorldObjectBuilder {
	private int _lastId;
	private GameState _gameState;

	public TestWorldObjectBuilder() {
		_lastId = 0;
		_gameState = null;
	}

	@Override
	public void SetGameState(GameState gs) {
		_gameState = gs;
	}

	@Override
	public Bomb CreateBomb(WorldObject creator, int radius, int strength,
			int timer) {
		Point2D p = new Point2D.Double(
				Math.floor(creator.Position.getX() / 40.0) * 40 + 20,
				Math.floor(creator.Position.getY() / 40.0) * 40 + 20);

		Bomb bomb = new TestBomb(_lastId++, p, creator.Guid, radius,
				strength, timer);

		if (_gameState != null)
			_gameState.AddEntity(bomb);

		return bomb;
	}

	@Override
	public Player CreatePlayer(String name, Point2D position) {
		Player player = new TestPlayer(_lastId++, position, name);

		if (_gameState != null)
			_gameState.AddEntity(player);

		return player;
	}

	@Override
	public Wall CreateWall(int hitpoints, Point2D position) {
		Wall wall = new TestWall(_lastId++, position, hitpoints);

		if (_gameState != null)
			_gameState.AddEntity(wall);

		return wall;
	}

	@Override
	public PowerUp CreatePowerUp(Point2D position) {
		PowerUp powerup = new TestPowerUp(_lastId++, position);

		if (_gameState != null)
			_gameState.AddEntity(powerup);

		return powerup;
	}
}
