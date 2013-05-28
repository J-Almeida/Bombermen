package logic;

import java.awt.Point;

import logic.events.Event;
import logic.events.ExplodeEvent;
import model.QuadTree;

public class Wall extends WorldObject implements network.NetWall
{
    @Override
	public int GetHitPoints()
    {
		return HitPoints;
	}

	public int HitPoints;

    public Wall(int guid, Point pos, int hitpoints)
    {
        super(WorldObjectType.Wall, guid, pos);

        HitPoints = hitpoints;
    }

    @Override
    public void Update(QuadTree<WorldObject> qt, int diff)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean IsAlive()
    {
        return IsUndestroyable() || HitPoints > 0;
    }

    public boolean IsUndestroyable()
    {
        return HitPoints == -1;
    }

    @Override
    public void HandleEvent(GameState gs, WorldObject src, Event event)
    {
        if (event.IsExplodeEvent())
        {
            ExplodeEvent ev = event.ToExplodeEvent();

            if (!IsUndestroyable())
            {
                int dmg = ev.Bomb.Strength;

                if (dmg > HitPoints)
                    HitPoints = 0;
                else
                    HitPoints -= dmg;
            }
        }
    }
}
