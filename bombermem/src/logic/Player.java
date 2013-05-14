package logic;

import gui.swing.SwingPlayer;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import logic.events.Event;
import logic.events.MovementEvent;
import logic.events.RequestMovementEvent;
import logic.events.SpawnEvent;

public abstract class Player extends WorldObject
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

                Rectangle2D bb = new Rectangle2D.Double(newPos.getX() - SwingPlayer.SIZE_WIDTH / 2, newPos.getY() - SwingPlayer.SIZE_HEIGHT / 2, SwingPlayer.SIZE_WIDTH, SwingPlayer.SIZE_HEIGHT);
                if (!gs.CollidesWall(bb) || gs.CollidesBomb(Position))
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
