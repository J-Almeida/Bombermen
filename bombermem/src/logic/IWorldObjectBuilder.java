package logic;

import java.awt.Point;

import logic.PowerUp.PowerUpType;

public interface IWorldObjectBuilder
{
    void SetGameState(GameState gs);
    Bomb CreateBomb(WorldObject creator, int radius, int strength, int timer);
    Player CreatePlayer(String name, Point position);
    PowerUp CreatePowerUp(Point position, PowerUpType type);
    Wall CreateWall(int hitpoints, Point position);
}
