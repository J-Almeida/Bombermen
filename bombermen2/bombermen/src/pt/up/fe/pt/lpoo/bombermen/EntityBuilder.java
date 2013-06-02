package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class EntityBuilder
{
    private int _lastGuid;
    private TextureManager _textureManager;

    public EntityBuilder(TextureManager tm)
    {
        _lastGuid = 0;
        _textureManager = tm;
    }

    public Bomb CreateBomb(Player creator)
    {
        int tileX = World.GetTileX(creator.GetX());
        int tileY = World.GetTileY(creator.GetX());
        float x = World.GetPositionAtCenterX(tileX);
        float y = World.GetPositionAtCenterY(tileY);

        Sprite s = new Sprite(_textureManager.Get("bomb"), 0, 0, 18 * 3, 18);
        s.setOrigin(x, y);

        return new Bomb(s, _lastGuid++, creator, creator.GetBombRadius(),
                Constants.DEFAULT_BOMB_STRENGTH, Constants.DEFAULT_BOMB_TIMER);
    }

    public Player CreatePlayer(String name, float x, float y)
    {
        Sprite s = new Sprite(_textureManager.Get("bomberman"), 0, 0, 18 * 8, 26);
        s.setOrigin(x, y);

        return new Player(s, _lastGuid++, name);
    }

    public Wall CreateWall(int hitPoints, int tileX, int tileY)
    {
        float x = World.GetPositionAtCenterX(tileX);
        float y = World.GetPositionAtCenterY(tileY);

        Sprite s = new Sprite(_textureManager.Get("wall"), hitPoints == -1 ? 0 : 16, 0, 16, 16);
        s.setOrigin(x, y);

        return new Wall(s, _lastGuid++, hitPoints);
    }

    public PowerUp CreatePowerUp(Wall wall)
    {
        float x = wall.GetX();
        float y = wall.GetY();

        Sprite s = new Sprite(_textureManager.Get("powerup"), 0, 0, 16 * 7, 16 * 2);
        s.setOrigin(x, y);

        return new PowerUp(s, _lastGuid++, MathUtils.random(0, PowerUp.NUMBER_OF_TYPES)); // @TODO: All power up types got the same probability to spawn. Maybe change that.
    }

    public Explosion CreateExplosion(Bomb bomb, int tileX, int tileY)
    {
        float x = World.GetPositionAtCenterX(tileX);
        float y = World.GetPositionAtCenterY(tileY);

        Sprite s = new Sprite(_textureManager.Get("powerup"), 0, 0, 16 * 9, 16 * 5);
        s.setOrigin(x, y);

        return new Explosion(s, _lastGuid++, bomb.GetStrength());
    }
}
