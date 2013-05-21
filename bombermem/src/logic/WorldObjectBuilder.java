package logic;

import java.awt.Point;

public class WorldObjectBuilder
{
    private int _lastId;
    private GameState _gameState;
	
    public WorldObjectBuilder()
    {
        _lastId = 0;
        _gameState = null;
    }
    
    void SetGameState(GameState gs)
    {
    	_gameState = gs;
    }
    
    Bomb CreateBomb(WorldObject creator, int radius, int strength, int timer)
    {
    	Point pos = (Point) creator.Position.clone();
    	
    	Bomb bomb = new Bomb(_lastId++, pos, creator.Guid, radius, strength, timer);
    	
    	if (_gameState != null)
    		_gameState.AddEntity(bomb);
    	
    	return bomb;
    }
    
    Player CreatePlayer(String name, Point position)
    {
    	Player player = new Player(_lastId++, position, name);

        if (_gameState != null)
            _gameState.AddEntity(player);

        return player;
    }
    
    PowerUp CreatePowerUp(Point position)
    {
        PowerUp powerup = new PowerUp(_lastId++, position);

        if (_gameState != null)
            _gameState.AddEntity(powerup);

        return powerup;
    }
    
    Wall CreateWall(int hitpoints, Point position)
    {
    	 Wall wall = new Wall(_lastId++, position, hitpoints);

         if (_gameState != null)
             _gameState.AddEntity(wall);

         return wall;
    }
    
}
