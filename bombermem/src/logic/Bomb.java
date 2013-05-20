package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import utils.Direction;

import logic.GameState.WallCollision;
import logic.events.Event;
import logic.events.ExplodeEvent;

public abstract class Bomb extends WorldObject
{
    public Bomb(int guid, Point position, int playerOwnerId, int radius, int strength, int time)
    {
        super(WorldObjectType.Bomb, guid, position);

        Radius = radius;
        Strength = strength;
        Time = time;
        PlayerOwnerId = playerOwnerId;
    }

    public int Radius; // Number of cells in each direction
    public int Strength; // Damage in number of hitpoints
    public int Time; // Time to explode
    public int PlayerOwnerId; // Player who planted the bomb

    protected int _radius[] = { 0, 0, 0, 0 };/* new int[4]; */

    protected int _bombTimer = 0;
    protected int _explosionTimer = 0;
    protected boolean _shouldExplode = false;
    protected boolean _explosionEnded = false;

    @Override
    public void Update(GameState gs, int diff)
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

            if (_explosionTimer != diff)
            {
                /*
                List<WorldObject> objs = new ArrayList<WorldObject>();

                for (int i = 1; i <= _radius[Direction.West.Index]; ++i)
                    objs.addAll(gs.CollidesAny(Direction.ApplyMovementToPoint(Position, Direction.West, i)));

                for (int i = 1; i <= _radius[Direction.East.Index]; ++i)
                    objs.addAll(gs.CollidesAny(Direction.ApplyMovementToPoint(Position, Direction.East, i)));

                for (int i = 1; i <= _radius[Direction.North.Index]; ++i)
                    objs.addAll(gs.CollidesAny(Direction.ApplyMovementToPoint(Position, Direction.North, i)));

                for (int i = 1; i <= _radius[Direction.South.Index]; ++i)
                    objs.addAll(gs.CollidesAny(Direction.ApplyMovementToPoint(Position, Direction.South, i)));

                for (WorldObject obj : objs)
                    if (obj.Type == WorldObjectType.Player || obj.Type == WorldObjectType.Bomb)
                        gs.PushEvent(obj, this, new ExplodeEvent(this));
                */

                return;
            }

            boolean[] draw = { true, true, true, true };

            for (int i = 1; i <= Radius; ++i)
            {
                for (utils.Direction d : utils.Direction.values())
                {
                    if (draw[d.Index])
                    {
                        WallCollision wc = gs.CollidesWall(Direction.ApplyMovementToPoint(Position, d, i));
                        _radius[d.Index]++;
                        if (wc.Collision)
                        {
                            if (wc.Wall.IsUndestroyable())
                                _radius[d.Index]--;
                            draw[d.Index] = false;
                            gs.PushEvent(wc.Wall, this, new ExplodeEvent(this));
                        }

                        /*
                        List<WorldObject> objs = gs.CollidesAny(Direction.ApplyMovementToPoint(Position, d, i));
                        for (WorldObject obj : objs)
                        {
                            gs.PushEvent(obj, this, new ExplodeEvent(this));
                            if (obj.Type == WorldObjectType.Wall)
                            {
                                _radius[d.Index]++;
                                if (((Wall)obj).IsUndestroyable())
                                    _radius[d.Index]--;
                                draw[d.Index] = false;
                            }
                        }
                        */
                    }
                }
            }
        }
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
