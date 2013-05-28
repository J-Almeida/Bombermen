package logic;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import utils.Key;
import utils.Direction;

import logic.events.Event;
import logic.events.RequestMovementEvent;
import logic.events.SpawnEvent;
import model.QuadTree;

public abstract class GameState implements IState
{
    protected Map<Integer, WorldObject> _entities = new HashMap<Integer, WorldObject>();
    protected Map<Key, Boolean> _pressedKeys = new HashMap<Key, Boolean>();
    protected QuadTree<WorldObject> _quadTree = new QuadTree<WorldObject>(new Rectangle(0, 0, 50, 50));
    protected int _currentPlayerId = 0;
    protected WorldObjectBuilder _objectBuilder;
    protected Queue<UnitEventEntry> _eventQueue = new LinkedList<UnitEventEntry>();

    public WorldObject GetEntity(int guid)
    {
    	return _entities.get(guid);
    }
    
    @Override
    public void Initialize()
    {
        /*_quadTree = new QuadTree<WorldObject>(new Rectangle(0, 0, 50, 50));*/

        for (Key k : Key.values())
            _pressedKeys.put(k, false);
    }

    @Override
    public void LoadContents()
    {
        // TODO Auto-generated method stub

    }

    public void MovePlayer(Direction direction)
    {
        PushEvent(_entities.get(_currentPlayerId), null, new RequestMovementEvent(direction));
    }

    public class WallCollision
    {
        WallCollision(boolean coll, Wall wall) { Collision = coll; Wall = wall; }
        boolean Collision;
        Wall Wall;
    }

    public WallCollision CollidesWall(Point p)
    {
        synchronized (_quadTree)
        {
            List<WorldObject> objs = _quadTree.QueryRange(p);

            for (WorldObject obj : objs)
                if (obj.Type == WorldObjectType.Wall)
                    return new WallCollision(true, (Wall)obj);

            return new WallCollision(false, null);
        }
    }

    public List<WorldObject> CollidesAny(Point p)
    {
        synchronized (_quadTree)
        {
            return _quadTree.QueryRange(p);
        }

    }

    @Override
    public void Update(int diff)
    {
        for (Entry<Key, Boolean> pair : _pressedKeys.entrySet())
        {
            if (!pair.getValue())
                continue;

            switch (pair.getKey())
            {
            case ESC:
                break;
            case DOWN:
            case LEFT:
            case RIGHT:
            case UP:
                Direction d = Direction.FromKey(pair.getKey());
                if (d != null)
                    MovePlayer(d);
                pair.setValue(false);
                break;
            case SPACE:
                PushEvent(_entities.get(_currentPlayerId), null, new SpawnEvent(WorldObjectType.Bomb));
                pair.setValue(false);
                break;
            default:
                break;
            }
        }

        // handle events
        while (!_eventQueue.isEmpty())
        {
            _eventQueue.poll().HandleEvent(this);
        }

        synchronized (_quadTree)
        {
            // update objects & build quadtree
            _quadTree.Clear();
            for (WorldObject wo : _entities.values())
            {
                wo.Update(_quadTree, diff);
                _quadTree.Insert(wo);
            }
        }

        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        for (WorldObject wo : _entities.values())
        {
            if (!wo.IsAlive())
                toRemove.add(wo.Guid);
        }

        for (Integer i : toRemove)
            _entities.remove(i);
    }

    @Override
    public void UnloadContents()
    {
        // TODO Auto-generated method stub

    }

    public void PushEvent(WorldObject wo, WorldObject src, Event ev) { _eventQueue.add(new UnitEventEntry(wo.Guid, src.Guid, ev)); }

    public void ForwardEvent(WorldObject src, Event ev)
    {
        for (WorldObject wo : _entities.values())
            PushEvent(wo, src, ev);
    }

    public void AddEntity(WorldObject entity) { _entities.put(entity.Guid, entity); }

    public WorldObjectBuilder GetObjectBuilder() { return _objectBuilder; }
}
