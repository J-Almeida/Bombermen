package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;
import pt.up.fe.pt.lpoo.utils.Direction;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity
{
    private static CollisionHandler<Player> cHandler = new CollisionHandler<Player>()
    {};

    private String _name;
    private float _speed;
    private int _maxBombs;
    private int _explosionRadius;
    private int _currentBombs;
    private boolean[] _moving = { false, false, false, false };

    Player(int guid, String name, Vector2 pos, BombermenServer sv)
    {
        super(TYPE_PLAYER, guid, pos, sv);
        _name = name;
        _speed = Constants.INIT_PLAYER_SPEED;
        _currentBombs = 0;
        _explosionRadius = Constants.INIT_BOMB_RADIUS;
        _maxBombs = Constants.INIT_NUM_MAX_BOMBS;
    }

    public String GetName()
    {
        return _name;
    }

    public void SetName(String name)
    {
        _name = name;
    }

    public int GetCurrentBombs()
    {
        return _currentBombs;
    }

    public int GetMaxBombs()
    {
        return _maxBombs;
    }

    public int GetExplosionRadius()
    {
        return _explosionRadius;
    }

    public void UpdateCurrentBombs(int inc)
    {
        _currentBombs += inc;
    }

    public void UpdateMaxBombs(int inc)
    {
        _maxBombs += inc;
    }

    public void UpdateExplosionRadius(int inc)
    {
        _explosionRadius += inc;
    }

    public void SetExplosionRadius(int val)
    {
        _explosionRadius = val;
    }

    public float GetSpeed()
    {
        return _speed;
    }

    public void SetSpeed(float speed)
    {
        _speed = speed;
    }

    public void SetMoving(boolean val, int dir)
    {
        if (dir != Direction.NONE) _moving[dir] = val;
    }

    public boolean GetMoving(int dir)
    {
        if (dir == Direction.NONE) return false;
        return _moving[dir];
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_PLAYER(GetGuid(), GetName(), GetX(), GetY());
    }

    private static final Vector2 size = new Vector2(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);

    @Override
    public Vector2 GetSize()
    {
        return size;
    }

    @Override
    public void OnCollision(Entity e)
    {
        cHandler.OnCollision(this, e);
    }

    private int _timer = 0;

    @Override
    public void Update(int diff)
    {
        _timer += diff;

        if (_timer >= 75)
        {
            for (int i = 0; i < _moving.length; ++i)
            {
                if (!_moving[i]) continue;

                boolean moved = true;

                float x = GetX();
                float y = GetY();

                switch (i)
                {
                    case Direction.NORTH:
                        SetY(GetY() + GetSpeed() * _timer);
                        break;
                    case Direction.SOUTH:
                        SetY(GetY() - GetSpeed() * _timer);
                        break;
                    case Direction.EAST:
                        SetX(GetX() + GetSpeed() * _timer);
                        break;
                    case Direction.WEST:
                        SetX(GetX() - GetSpeed() * _timer);
                        break;
                    default:
                        moved = false;
                }

                if (moved)
                {
                    for (Entity e : _server.GetEntities().values())
                    {
                        if (e.IsBomb() && e.ToBomb().JustCreated() && e.ToBomb().GetCreatorGuid() == GetGuid())
                            if (Collides(e))
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

                if (moved)
                {

                    _server.SendAll(new SMSG_MOVE(GetGuid(), GetPosition()));

                }
            }
            _timer = 0;
        }

    }

    public void Kill()
    {
        _server.RemoveEntityNextUpdate(GetGuid());
    }
}
