package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import network.Server;
import utils.Direction;
import logic.events.Event;
import logic.events.ExplodeEvent;
import model.QuadTree;

public class Bomb extends WorldObject implements network.NetBomb
{
    @Override
	public int GetPlayerOwnerId() 
    {
		return PlayerOwnerId;
	}

	public int GetBombTimer()
	{
		return _bombTimer;
	}
	
	public int GetExplosionTimer()
	{
		return _explosionTimer;
	}
	
	public boolean IsExplosionEnded()
	{
		return _explosionEnded;
	}
    
	@Override
	public int GetTime() 
	{
		return Time;
	}

	@Override
	public int GetStrength() 
	{
		return Strength;
	}

	@Override
	public int GetMaxRadius() 
	{
		return MaxRadius;
	}

	@Override
	public int GetRadius(Direction d) 
	{
		return _radius[d.Index];
	}

	public Bomb(int guid, Point position, int playerOwnerId, int radius, int strength, int time)
    {
        super(WorldObjectType.Bomb, guid, position);

        MaxRadius = radius;
        Strength = strength;
        Time = time;
        PlayerOwnerId = playerOwnerId;
    }

    public int MaxRadius; // Number of cells in each direction
    public int Strength; // Damage in number of hitpoints
    public int Time; // Time to explode
    public final int PlayerOwnerId; // Player who planted the bomb

    protected int _radius[] = { 0, 0, 0, 0 };

    protected int _bombTimer = 0;
    protected int _explosionTimer = 0;
    protected boolean _shouldExplode = false;
    protected boolean _explosionEnded = false;

    @Override
    public void Update(QuadTree<WorldObject> qt, int diff)
    {
        _bombTimer += diff;

        if (_bombTimer > Time * 1000)
        {
            _shouldExplode = true;
            _bombTimer = -1;
        }

        if (_shouldExplode)
        {
            _explosionTimer += diff;

            if (_explosionTimer > 600)
            {
                _explosionEnded = true;
                _explosionTimer = -1;
            }

            if (_explosionTimer == diff)
                CalculateRadiuses(qt);
            else
                VerifyCollisions(qt);
        }
    }

    private void CalculateRadiuses(QuadTree<WorldObject> qt)
    {
        System.out.println("Calculating bomb explosion...");
        
        boolean[] draw = { true, true, true, true };

        for (int i = 1; i <= MaxRadius; ++i)
        {
            for (utils.Direction d : utils.Direction.values())
            {
                if (draw[d.Index])
                {
                	List<WorldObject> lstWo = qt.QueryRange(Direction.ApplyMovementToPoint(Position, d, i));

                    _radius[d.Index]++;
                	
                	for (WorldObject wo :lstWo)
                	{
                		if (wo.Type == WorldObjectType.Wall)
                		{
                			Wall w = (Wall)wo;
                			if (w.IsUndestroyable())
                				_radius[d.Index]--;
                            draw[d.Index] = false;
                		}
                	}
                }
            }
        }
    }

    public void VerifyCollisions(QuadTree<WorldObject> qt)
    {
        List<WorldObject> objs = new ArrayList<WorldObject>();

        for (int i = 1; i <= _radius[Direction.West.Index]; ++i)
            objs.addAll(qt.QueryRange(Direction.ApplyMovementToPoint(Position, Direction.West, i)));

        for (int i = 1; i <= _radius[Direction.East.Index]; ++i)
            objs.addAll(qt.QueryRange(Direction.ApplyMovementToPoint(Position, Direction.East, i)));

        for (int i = 1; i <= _radius[Direction.North.Index]; ++i)
            objs.addAll(qt.QueryRange(Direction.ApplyMovementToPoint(Position, Direction.North, i)));

        for (int i = 1; i <= _radius[Direction.South.Index]; ++i)
            objs.addAll(qt.QueryRange(Direction.ApplyMovementToPoint(Position, Direction.South, i)));

        for (WorldObject obj : objs)
        	Server.GetInstance().PushEvent(obj, this, new ExplodeEvent(this));
    }

    @Override
    public boolean IsAlive()
    {
        return !_explosionEnded;
    }

    public boolean ShouldExplode()
    {
         return _shouldExplode;
    }

    @Override
    public void HandleEvent(GameState gs, WorldObject src, Event event)
    {
        if (event.IsExplodeEvent())
        {
            _shouldExplode = true;
        }
    }
}
