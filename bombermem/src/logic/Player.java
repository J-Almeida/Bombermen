package logic;

import java.awt.Point;

import logic.events.Event;
import logic.events.MovementEvent;
import logic.events.RequestMovementEvent;
import logic.events.SpawnEvent;

public abstract class Player extends WorldObject
{
    protected int currentTile = 0;

    final String Name;

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
        return true;
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

                Direction = rmv.Direction;

                if (!gs.CollidesWall(newPos).Collision)
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
                    gs.GetObjectBuilder().CreateBomb(this, 10, 1, 3);
                break;
            }
            default:
                break;
        }
    }
}
