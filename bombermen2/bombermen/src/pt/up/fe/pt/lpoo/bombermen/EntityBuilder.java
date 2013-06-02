package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class EntityBuilder
{
    private TextureManager _textureManager;

    public EntityBuilder(TextureManager tm)
    {
        _textureManager = tm;
    }

    public Bomb CreateBomb(int guid, Player creator)
    {
        int tileX = World.GetTileX(creator.GetX());
        int tileY = World.GetTileY(creator.GetX());
        float x = World.GetPositionAtCenterX(tileX);
        float y = World.GetPositionAtCenterY(tileY);

        Sprite s = new Sprite(_textureManager.Get("bomb"), 0, 0, 18 * 3, 18);
        s.setPosition(x - 18 / 2, y + 18 / 2);
        s.setOrigin(x, y);

        Bomb b = new Bomb(s, guid, creator, creator.GetBombRadius(), Constants.DEFAULT_BOMB_STRENGTH, Constants.DEFAULT_BOMB_TIMER);

        b.SplitSprite(18, 18);

        return b;
    }

    public Player CreatePlayer(int guid, String name, float x, float y)
    {
        Sprite s = new Sprite(_textureManager.Get("bomberman"), 0, 0, 18 * 8, 26);
        s.setPosition(x - 18 / 2, y + 26 / 2);
        s.setOrigin(x, y);

        Player p = new Player(s, guid, name);

        p.SplitSprite(18, 26);

        return p;
    }

    public Wall CreateWall(int guid, int hitPoints, int tileX, int tileY)
    {
        float x = World.GetPositionAtCenterX(tileX);
        float y = World.GetPositionAtCenterY(tileY);

        System.out.println("tileX: " + tileX + " tileY: " + tileY + " x: " + x + " y: " + y);

        Sprite s = new Sprite(_textureManager.Get("wall"), 0, 0, 16 * 2, 16);
        s.setPosition(x - 16 / 2, y + 16 / 2);
        s.setOrigin(x, y);

        Wall w = new Wall(s, guid, hitPoints);

        w.SplitSprite(16, 16);

        return w;
    }

    public PowerUp CreatePowerUp(int guid, Wall wall)
    {
        float x = wall.GetX();
        float y = wall.GetY();

        Sprite s = new Sprite(_textureManager.Get("powerup"), 0, 0, 16 * 7, 16 * 2);
        s.setPosition(x - 16 / 2, y + 16 / 2);
        s.setOrigin(x, y);

        PowerUp pu = new PowerUp(s, guid, MathUtils.random(0, PowerUp.NUMBER_OF_TYPES)); // @TODO:
                                                                                         // All
                                                                                         // power
                                                                                         // up
                                                                                         // types
                                                                                         // got
                                                                                         // the
                                                                                         // same
                                                                                         // probability
                                                                                         // to
                                                                                         // spawn.
                                                                                         // Maybe
                                                                                         // change
                                                                                         // that.

        pu.SplitSprite(16, 16);

        return pu;
    }

    public Explosion CreateExplosion(int guid, Bomb bomb, int tileX, int tileY)
    {
        float x = World.GetPositionAtCenterX(tileX);
        float y = World.GetPositionAtCenterY(tileY);

        Sprite s = new Sprite(_textureManager.Get("powerup"), 0, 0, 16 * 9, 16 * 5);
        s.setPosition(x - 16 / 2, y + 16 / 2);
        s.setOrigin(x, y);

        Explosion e = new Explosion(s, guid, bomb.GetStrength());

        e.SplitSprite(16, 16);

        return e;
    }
}
