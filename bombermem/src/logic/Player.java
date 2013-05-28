package logic;

import java.awt.Point;

import logic.events.Event;
import logic.events.MovementEvent;
import logic.events.RequestMovementEvent;
import logic.events.SpawnEvent;
import model.QuadTree;
import utils.Direction;

public class Player extends WorldObject
{
	public String GetName() 
    {
		return Name;
	}

	protected int currentTile = 0;
    private boolean _alive = true;

    final String Name;

    public Player(int guid, Point pos, String name)
    {
        super(WorldObjectType.Player, guid, pos);

        Name = name;
        Dir = Direction.South;
    }

    @Override
    public void Update(QuadTree<WorldObject> qt, int diff)
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
                    gs.GetObjectBuilder().CreateBomb(this, 3, 1, 3);
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
