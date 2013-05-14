package logic;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

import logic.events.Event;
import logic.events.MovementEvent;
import logic.events.RequestMovementEvent;
import logic.events.SpawnEvent;

public class Player extends WorldObject
{
    protected int currentTile = 0;
    
    final String Name;
    
    public Player(int guid, Point2D pos, String name)
    {
        super(WorldObjectType.Player, guid, pos);

        Name = name;
    }

    @Override
    public void Update(int diff)
    {
        //Position.setLocation(Position.getX() + 2.0, Position.getY());
    }

    @Override
    public boolean IsAlive()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void HandleEvent(GameState gs, WorldObject src, Event event)
    {
        switch (event.Type)
        {
            case RequestMovement:
            {
                RequestMovementEvent rmv = event.ToRequestMovementEvent();

                Point2D newPos = (Point2D) Position.clone();
                utils.Direction.ApplyMovement(newPos, rmv.Direction, 1.5);

                Direction = rmv.Direction;
                
                // private final static int SIZE_WIDTH = 360/20; 18
                // private final static int SIZE_HEIGHT = 26;

                if (!gs.CollidesWall(new Rectangle((int)(Position.getX() - 18 / 2), (int)(Position.getY() - 26 / 2), 18, 26)));
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
                    gs.AddEntity(gs.GetObjectBuilder().CreateBomb(this, 2, 1, 3));
                break;
            }
            default:
                break;
        }
    }
}
