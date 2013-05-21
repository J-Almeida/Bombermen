package gui.swing;

import java.awt.Point;

import logic.Bomb;
import logic.GameState;
import logic.IWorldObjectBuilder;
import logic.Player;
import logic.PowerUp;
import logic.PowerUp.PowerUpType;
import logic.Wall;
import logic.WorldObject;

public class SwingWorldObjectBuilder implements IWorldObjectBuilder
{
    private int _lastId;
    private SwingGameState _gameState;

    public SwingWorldObjectBuilder()
    {
        _lastId = 0;
        _gameState = null;
    }

    @Override
    public void SetGameState(GameState gs)
    {
        _gameState = (SwingGameState) gs;
    }

    @Override
    public Bomb CreateBomb(WorldObject creator, int radius, int strength, int timer)
    {
        Point p = new Point(creator.Position.x, creator.Position.y);

        SwingBomb bomb = new SwingBomb(_lastId++, p, creator.Guid, radius, strength, timer);

        if (_gameState != null)
            _gameState.AddEntity(bomb);

        return bomb;
    }

    @Override
    public Player CreatePlayer(String name, Point position)
    {
        SwingPlayer player = new SwingPlayer(_lastId++, position, name);

        if (_gameState != null)
            _gameState.AddEntity(player);

        return player;
    }

    @Override
    public Wall CreateWall(int hitpoints, Point position)
    {
        SwingWall wall = new SwingWall(_lastId++, position, hitpoints);

        if (_gameState != null)
            _gameState.AddEntity(wall);

        return wall;
    }

    @Override
    public PowerUp CreatePowerUp(Point position, PowerUpType type)
    {
        SwingPowerUp powerup = new SwingPowerUp(_lastId++, position, type);

        if (_gameState != null)
            _gameState.AddEntity(powerup);

        return powerup;
    }
}
