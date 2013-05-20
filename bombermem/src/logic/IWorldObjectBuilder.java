package logic;

import java.awt.Point;

public interface IWorldObjectBuilder
{
    void SetGameState(GameState gs);
    Bomb CreateBomb(WorldObject creator, int radius, int strength, int timer);
    Player CreatePlayer(String name, Point position);
    PowerUp CreatePowerUp(Point position);
    Wall CreateWall(int hitpoints, Point position);
}
