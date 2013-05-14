package logic;

import java.awt.geom.Point2D;

public interface IWorldObjectBuilder
{
    void SetGameState(GameState gs);
    Bomb CreateBomb(WorldObject creator, int radius, int strength, int timer);
    Player CreatePlayer(String name, Point2D position);
    PowerUp CreatePowerUp(Point2D position);
    Wall CreateWall(int hitpoints, Point2D position);
}
