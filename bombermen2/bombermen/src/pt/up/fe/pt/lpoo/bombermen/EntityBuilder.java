package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import pt.up.fe.pt.lpoo.utils.Point;

public class EntityBuilder
{
    private int _lastGuid;
    
    public EntityBuilder()
    {
        _lastGuid = 0;
    }

    public Bomb CreateBomb(Player creator)
    {
        Point point = World.GetTile(creator.GetPosition());
        Vector2 position = World.GetPositionAtCenter(point);

        return new Bomb(position, _lastGuid++, creator, creator.GetBombRadius(),
                Constants.DEFAULT_BOMB_STRENGTH, Constants.DEFAULT_BOMB_TIMER);
    }
    
    public Player CreatePlayer(String name, Vector2 position)
    {
        return new Player(position, _lastGuid++, name);
    }
    
    public Wall CreateWall(int hitPoints, Point point)
    {
        Vector2 position = World.GetPositionAtCenter(point);
        
        return new Wall(position, _lastGuid++, hitPoints);
    }

    public PowerUp CreatePowerUp(Wall wall)
    {
        Vector2 position = wall.GetPosition().cpy();

        return new PowerUp(position, _lastGuid++, MathUtils.random(0, PowerUp.NUMBER_OF_TYPES)); // @TODO: All power up types got the same probability to spawn. Maybe change that.
    }
    
    public Explosion CreateExplosion(Bomb bomb, Point point)
    {
        Vector2 position = World.GetPositionAtCenter(point);
        
        return new Explosion(position, _lastGuid++, bomb.GetStrength());
    }
}
