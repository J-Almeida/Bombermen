package logic;

import java.awt.Point;
import java.util.List;

import logic.Bomb.ExplodeHandler;
import logic.events.Event;
import logic.events.MovementEvent;
import logic.events.PickUpEvent;
import logic.events.RequestMovementEvent;
import logic.events.SpawnEvent;

public abstract class Player extends WorldObject
{
    protected int currentTile = 0;
    private boolean _alive = true;

    public final String Name;
    public int MaxBombs = 1;
    public int BombRadius = 2;

    private int _currentBombs = 0;

    public Player(int guid, Point pos, String name)
    {
        super(WorldObjectType.Player, guid, pos);

        Name = name;
    }

    @Override
    public void Update(GameState gs, int diff)
    {

    }

    @Override
    public boolean IsAlive()
    {
        return _alive;
    }

    @Override
    public void HandleEvent(GameState gs, WorldObject src, Event event)
    {
        switch (event.Type)
        {
            case RequestMovement:
            {
                RequestMovementEvent rmv = event.ToRequestMovementEvent();

                Point newPos = (Point) Position.clone();
                utils.Direction.ApplyMovement(newPos, rmv.Direction, 1);

                Dir = rmv.Direction;

                boolean isBlocked = false;
                List<WorldObject> objs = gs.CollidesAny(newPos);
                for (WorldObject obj : objs)
                {
                    if (obj.Type == WorldObjectType.Wall || obj.Type == WorldObjectType.Bomb)
                        isBlocked = true;
                    else if (obj.Type == WorldObjectType.PowerUp)
                        gs.PushEvent(obj, this, new PickUpEvent(this));
                }

                if (!isBlocked)
                {
                    Position.setLocation(newPos);
                    gs.ForwardEvent(this, new MovementEvent(rmv.Direction));
                }

                break;
            }
            case Spawn:
            {
                SpawnEvent se = event.ToSpawnEvent();
                if (se.Type == WorldObjectType.Bomb)
                {
                    if (_currentBombs < MaxBombs)
                    {
                        _currentBombs++;

                        Bomb b = gs.GetObjectBuilder().CreateBomb(this, BombRadius, 1, 3);
                        b.AddOnExplodeHandler(new ExplodeHandler()
                        {
                            @Override
                            public void OnExplode() { _currentBombs--; }
                        });
                    }
                }
                break;
            }
            case Explode:
            {
                _alive = false;
                break;
            }
            default:
                break;
        }
    }
}
