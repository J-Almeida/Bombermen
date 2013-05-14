package logic;

import java.awt.Rectangle;
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
    protected Map<Integer, WorldObject> _entities;
    protected Map<Key, Boolean> _pressedKeys;
    protected QuadTree _quadTree;
    protected int _currentPlayerId = 0;
    protected IWorldObjectBuilder _objectBuilder;
    protected Queue<UnitEventEntry> _eventQueue;

    @Override
    public void Initialize()
    {
        _quadTree = new QuadTree(new Rectangle(0, 0, 800, 600));
        _eventQueue = new LinkedList<UnitEventEntry>();
        
        _pressedKeys = new HashMap<Key, Boolean>();
        for (Key k : Key.values())
            _pressedKeys.put(k, false);
    }

    @Override
    public void LoadContents()
    {
        // TODO Auto-generated method stub

    }
    
    public void MoveHero(Direction direction)
    {
        PushEvent(_entities.get(_currentPlayerId), null, new RequestMovementEvent(direction));
    }
    
    public boolean CollidesWall(Rectangle rect)
    {
        List<WorldObject> objs = _quadTree.QueryRange(rect);

        for (WorldObject obj : objs)
            if (obj.Type == WorldObjectType.Wall)
                return true;
        
        return false;
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
                    PushEvent(_entities.get(_currentPlayerId), null, new RequestMovementEvent(d));
                break;
            case SPACE:
                PushEvent(_entities.get(_currentPlayerId), null, new SpawnEvent(WorldObjectType.Bomb));
                pair.setValue(false);
                break;
            default:
                break;
            }
        }

        _quadTree.Clear();
        for (WorldObject wo : _entities.values())
        {
            wo.Update(diff);
            _quadTree.Insert(wo);
        }
        
        // handle events
        while (!_eventQueue.isEmpty())
            _eventQueue.poll().HandleEvent(this);
    }

    @Override
    public void UnloadContents()
    {
        // TODO Auto-generated method stub

    }
    
    public void PushEvent(WorldObject wo, WorldObject src, Event ev) { _eventQueue.add(new UnitEventEntry(wo, src, ev)); }
    
    public void ForwardEvent(WorldObject src, Event ev)
    {
        for (WorldObject wo : _entities.values())
            PushEvent(wo, src, ev);
    }

    public void AddEntity(WorldObject entity) { _entities.put(entity.Guid, entity); }
    
    public IWorldObjectBuilder GetObjectBuilder() { return _objectBuilder; }
}
