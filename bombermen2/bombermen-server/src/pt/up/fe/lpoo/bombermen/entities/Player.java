package pt.up.fe.lpoo.bombermen.entities;

import pt.up.fe.lpoo.bombermen.BombermenServer;
import pt.up.fe.lpoo.bombermen.CollisionHandler;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Entity;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DEATH;
import pt.up.fe.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.SMSG_PLAYER_SPEED;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;
import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The Class Player.
 */
public class Player extends Entity
{

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetBoundingRectangle()
     */
    @Override
    public Rectangle GetBoundingRectangle()
    {
        _boundingRectangle.x = GetX() + offsetWidth;
        _boundingRectangle.y = GetY() + offsetHeight;
        return _boundingRectangle;
    }

    /** The c handler. */
    private static CollisionHandler<Player> cHandler = new CollisionHandler<Player>()
    {};

    /** The name. */
    private String _name;

    /** The speed. */
    private float _speed;

    /** The max bombs. */
    private int _maxBombs;

    /** The explosion radius. */
    private int _explosionRadius;

    /** The current bombs. */
    private int _currentBombs;

    /** The score. */
    private int _score;

    /** The moving. */
    private boolean[] _moving = { false, false, false, false };

    /** The Constant offsetWidth. */
    private static final float offsetWidth = (Constants.PLAYER_WIDTH - Constants.PLAYER_BOUNDING_WIDTH) / 2.f;

    /** The Constant offsetHeight. */
    private static final float offsetHeight = (Constants.PLAYER_HEIGHT - Constants.PLAYER_BOUNDING_HEIGHT) / 2.f;

    /** The bounding rectangle. */
    private Rectangle _boundingRectangle = new Rectangle(0, 0, Constants.PLAYER_BOUNDING_WIDTH, Constants.PLAYER_BOUNDING_HEIGHT);

    /**
     * Instantiates a new player.
     *
     * @param guid the guid
     * @param name the name
     * @param pos the pos
     * @param initScore the init score
     * @param sv the sv
     */
    public Player(int guid, String name, Vector2 pos, int initScore, BombermenServer sv)
    {
        super(TYPE_PLAYER, guid, pos, sv);
        _name = name;
        _speed = Constants.INIT_PLAYER_SPEED;
        _currentBombs = 0;
        _explosionRadius = Constants.INIT_BOMB_RADIUS;
        _maxBombs = Constants.INIT_NUM_MAX_BOMBS;
        _score = initScore;
    }

    /**
     * Gets the score.
     *
     * @return the int
     */
    public int GetScore()
    {
        return _score;
    }

    /**
     * Change score.
     *
     * @param amount the amount
     * @return the int
     */
    public int ChangeScore(int amount)
    {
        _score += amount;
        return _score;
    }

    /**
     * Gets the name.
     *
     * @return the string
     */
    public String GetName()
    {
        return _name;
    }

    /**
     * Sets the name.
     *
     * @param name the name
     */
    public void SetName(String name)
    {
        _name = name;
    }

    /**
     * Gets the current bombs.
     *
     * @return the int
     */
    public int GetCurrentBombs()
    {
        return _currentBombs;
    }

    /**
     * Gets the max bombs.
     *
     * @return the int
     */
    public int GetMaxBombs()
    {
        return _maxBombs;
    }

    /**
     * Gets the explosion radius.
     *
     * @return the int
     */
    public int GetExplosionRadius()
    {
        return _explosionRadius;
    }

    /**
     * Update current bombs.
     *
     * @param inc the inc
     */
    public void UpdateCurrentBombs(int inc)
    {
        _currentBombs += inc;
    }

    /**
     * Update max bombs.
     *
     * @param inc the inc
     */
    public void UpdateMaxBombs(int inc)
    {
        _maxBombs += inc;
    }

    /**
     * Update explosion radius.
     *
     * @param inc the inc
     */
    public void UpdateExplosionRadius(int inc)
    {
        if (_explosionRadius >= Integer.MAX_VALUE) return;

        _explosionRadius += inc;
    }

    /**
     * Update speed.
     *
     * @param inc the inc
     */
    public void UpdateSpeed(float inc)
    {
        _speed += inc;
        _server.SendAll(new SMSG_PLAYER_SPEED(GetGuid(), _speed));
    }

    /**
     * Sets the explosion radius.
     *
     * @param val the val
     */
    public void SetExplosionRadius(int val)
    {
        _explosionRadius = val;
    }

    /**
     * Gets the speed.
     *
     * @return the float
     */
    public float GetSpeed()
    {
        return _speed;
    }

    /**
     * Sets the speed.
     *
     * @param speed the speed
     */
    public void SetSpeed(float speed)
    {
        _speed = speed;
    }

    /**
     * Sets the moving.
     *
     * @param val the val
     * @param dir the dir
     */
    public void SetMoving(boolean val, int dir)
    {
        if (dir != Direction.NONE) _moving[dir] = val;
    }

    /**
     * Gets the moving.
     *
     * @param dir the dir
     * @return true, if successful
     */
    public boolean GetMoving(int dir)
    {
        if (dir == Direction.NONE) return false;
        return _moving[dir];
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSpawnMessage()
     */
    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_PLAYER(GetGuid(), GetName(), GetScore(), GetX(), GetY());
    }

    /** The Constant size. */
    private static final Vector2 size = new Vector2(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#GetSize()
     */
    @Override
    public Vector2 GetSize()
    {
        return size;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#OnCollision(pt.up.fe.lpoo.bombermen.Entity)
     */
    @Override
    public void OnCollision(Entity e)
    {
        cHandler.OnCollision(this, e);
    }

    /** The timer. */
    private int _timer = 0;

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#Update(int)
     */
    @Override
    public void Update(int diff)
    {
        _timer += diff;

        for (int i = 0; i < _moving.length; ++i)
        {
            if (!_moving[i]) continue;

            boolean moved = true;

            float x = GetX();
            float y = GetY();

            switch (i)
            {
                case Direction.NORTH:
                    SetY(GetY() + GetSpeed() * diff);
                    break;
                case Direction.SOUTH:
                    SetY(GetY() - GetSpeed() * diff);
                    break;
                case Direction.EAST:
                    SetX(GetX() + GetSpeed() * diff);
                    break;
                case Direction.WEST:
                    SetX(GetX() - GetSpeed() * diff);
                    break;
                default:
                    moved = false;
            }

            if (moved)
            {
                for (Entity e : _server.GetEntities().values())
                {
                    if (e.IsBomb() && e.ToBomb().JustCreated() && e.ToBomb().GetCreatorGuid() == GetGuid()) if (Collides(e))
                        break;
                    else
                        e.ToBomb().SetJustCreated(false);

                    if (!e.IsPlayer() && Collides(e))
                    {
                        if (e.IsExplosion() || e.IsPowerUp())
                        {
                            OnCollision(e);
                            e.OnCollision(this);
                        }
                        else
                        {
                            moved = false;
                            SetPosition(x, y);
                        }
                        break;
                    }
                }
            }

            if (_timer >= 200)
            {
                _server.SendAll(new SMSG_MOVE(GetGuid(), GetPosition()));
                _timer = 0;
            }
        }

    }

    /**
     * Kill.
     */
    public void Kill()
    {
        _server.RemoveEntityNextUpdate(GetGuid());
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Entity#OnDestroy()
     */
    @Override
    public void OnDestroy()
    {
        _server.SendTo(GetGuid(), new SMSG_DEATH());
    }
}
