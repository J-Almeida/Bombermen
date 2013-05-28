package logic;

import java.awt.Point;

import logic.events.Event;
import model.QuadTree;

public class PowerUp extends WorldObject implements network.NetPowerUp
{
    public PowerUp(int guid, Point pos)
    {
        super(WorldObjectType.PowerUp, guid, pos);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void Update(QuadTree<WorldObject> qt, int diff)
    {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

    }
}
