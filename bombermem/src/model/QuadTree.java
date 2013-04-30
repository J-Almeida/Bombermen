package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.geom.Rectangle2D;

import logic.WorldObject;


public class QuadTree
{
    private static final int NODE_CAPACITY = 4;
    
    private static final int NORTHWEST = 0;
    private static final int NORTHEAST = 1;
    private static final int SOUTHWEST = 2;
    private static final int SOUTHEAST = 3;
    private static final int NODE_COUNT = 4;
    
    public final Rectangle2D Boundary;
    
    public final Map<Integer, WorldObject> Entities;
    
    public QuadTree[] Nodes;
    
    public QuadTree(Rectangle2D boundary)
    {
        Boundary = boundary;
        Entities = new HashMap<Integer, WorldObject>();
        Nodes = null;
    }
    
    private boolean IsLeaf() { return Nodes == null; }
    
    public boolean Insert(WorldObject obj)
    {
        if (!Boundary.contains(obj.Position))
            return false;
        
        if (Entities.size() < NODE_CAPACITY)
        {
            Entities.put(obj.Guid, obj);
            return true;
        }
        
        if (IsLeaf())
            Subdivide();
        
        for (int i = 0; i < NODE_COUNT; ++i)
            if (Nodes[i].Insert(obj))
                return true;

        return false;
    }
    
    private void Subdivide()
    {
        double subWidth = Boundary.getWidth() / 2;
        double subHeight = Boundary.getHeight() / 2;
        double x = Boundary.getX();
        double y = Boundary.getY();

        Nodes = new QuadTree[NODE_COUNT];
        Nodes[NORTHWEST] = new QuadTree(new Rectangle2D.Double(x, y, subWidth, subHeight));
        Nodes[NORTHEAST] = new QuadTree(new Rectangle2D.Double(x + subWidth, y, subWidth, subHeight));
        Nodes[SOUTHWEST] = new QuadTree(new Rectangle2D.Double(x, y + subHeight, subWidth, subHeight));
        Nodes[SOUTHEAST] = new QuadTree(new Rectangle2D.Double(x + subWidth, y + subHeight, subWidth, subHeight));
    }
    
    public List<WorldObject> QueryRange(Rectangle2D range)
    {
        List<WorldObject> inRange = new ArrayList<WorldObject>();
        
        if (!Boundary.intersects(range))
            return inRange;
        
        for (WorldObject obj : Entities.values())
            if (range.contains(obj.Position))
                inRange.add(obj);
        
        if (IsLeaf())
            return inRange;
        
        for (int i = 0; i < NODE_COUNT; ++i)
            inRange.addAll(Nodes[i].QueryRange(range));

        return inRange;
    }
    
    public void Clear()
    {
        Entities.clear();
        
        if (IsLeaf())
            return;
        
        for (int i = 0; i < NODE_COUNT; ++i)
        {
            Nodes[i].Clear();
            Nodes[i] = null;
        }
        
        Nodes = null;
    }
}
