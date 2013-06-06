package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.entities.Bomb;
import pt.up.fe.pt.lpoo.bombermen.entities.Explosion;
import pt.up.fe.pt.lpoo.bombermen.entities.Player;
import pt.up.fe.pt.lpoo.bombermen.entities.PowerUp;
import pt.up.fe.pt.lpoo.bombermen.entities.Wall;
import pt.up.fe.pt.lpoo.utils.Direction;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityBuilder
{
    public static Bomb CreateBomb(int guid, float x, float y)
    {
        Texture t = Assets.GetTexture("bomb"); // 18 x 18

        if (Bomb.Regions == null && Bomb.Animation == null)
        {
            Bomb.Regions = TextureRegion.split(t, 18, 18);
            Bomb.Animation = new Animation(1f / 3, Bomb.Regions[0][0], Bomb.Regions[0][1], Bomb.Regions[0][2]);
        }

        Bomb b = new Bomb(guid);
        b.setBounds(x, y, Constants.BOMB_WIDTH, Constants.BOMB_HEIGHT);

        return b;
    }

    public static Player CreatePlayer(int guid, String name, float x, float y)
    {
        Texture t = Assets.GetTexture("bomberman"); // 18 x 26

        if (Player.Regions == null)
            Player.Regions = TextureRegion.split(t, 18, 26);

        for (int d : Direction.Directions)
        {
            if (Player.Animations[d] == null)
            {
                int col = 0;
                switch (d)
                {
                    case Direction.NORTH:
                        col = 4;
                        break;
                    case Direction.SOUTH:
                        col = 0;
                        break;
                    case Direction.EAST:
                        col = 2;
                        break;
                    case Direction.WEST:
                        col = 6;
                        break;
                }

                Player.Animations[d] = new Animation(1f / 5, Player.Regions[0][col], Player.Regions[0][col+1]);
            }

        }

        Player p = new Player(guid, name);
        p.setBounds(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);

        return p;
    }

    public static Wall CreateWall(int guid, int hitPoints, float x, float y)
    {
        Texture t = Assets.GetTexture("wall"); // 16 x 16

        if (Wall.Regions == null)
            Wall.Regions = TextureRegion.split(t, 16, 16);

        Wall w = new Wall(guid, hitPoints);
        w.setBounds(x, y, Constants.WALL_WIDTH, Constants.WALL_HEIGHT);

        return w;
    }

    public static PowerUp CreatePowerUp(int guid, float x, float y, int powerUpType)
    {
        Texture t = Assets.GetTexture("powerup"); // 16 x 16

        if (PowerUp.Regions == null)
        {
            PowerUp.Regions = TextureRegion.split(t, 16, 16);
        }

        PowerUp pu = new PowerUp(guid, new Animation(0.5f, PowerUp.Regions[0][powerUpType], PowerUp.Regions[1][powerUpType]));
        pu.setBounds(x, y, Constants.POWER_UP_WIDTH, Constants.POWER_UP_HEIGHT);

        return pu;
    }

    public static Explosion CreateExplosion(int guid, float x, float y, int direction, boolean end)
    {
        Texture t = Assets.GetTexture("explosion"); // 16 x 16

        if (Explosion.Regions == null)
            Explosion.Regions = TextureRegion.split(t, 16, 16);

        Explosion e = new Explosion(guid, direction, end);
        e.setBounds(x, y, Constants.EXPLOSION_WIDTH, Constants.EXPLOSION_HEIGHT);

        return e;
    }
}
