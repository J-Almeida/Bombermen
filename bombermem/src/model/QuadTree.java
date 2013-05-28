package model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;


public class QuadTree<T extends Positionable> implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int NODE_CAPACITY = 4;

    private static final int NORTHWEST = 0;
    private static final int NORTHEAST = 1;
    private static final int SOUTHWEST = 2;
    private static final int SOUTHEAST = 3;
    private static final int NODE_COUNT = 4;

    public final Rectangle2D Boundary;

    public final Map<Integer, T> Entities;

    public QuadTree<T>[] Nodes;

    public QuadTree(Rectangle2D boundary)
    {
        Boundary = boundary;
        Entities = new HashMap<Integer, T>();
        Nodes = null;
    }

    private boolean IsLeaf() { return Nodes == null; }

    public boolean Insert(T obj)
    {
        if (!Boundary.contains(obj.GetPosition()))
            return false;

        if (Entities.size() < NODE_CAPACITY)
        {
            Entities.put(obj.GetGuid(), obj);
            return true;
        }

        if (IsLeaf())
            Subdivide();

        for (int i = 0; i < NODE_COUNT; ++i)
            if (Nodes[i].Insert(obj))
                return true;

        return false;
    }

    @SuppressWarnings("unchecked")
	private void Subdivide()
    {
        double subWidth = Boundary.getWidth() / 2;
        double subHeight = Boundary.getHeight() / 2;
        double x = Boundary.getX();
        double y = Boundary.getY();
        
        Nodes = (QuadTree<T>[]) new QuadTree<?>[NODE_COUNT];
        Nodes[NORTHWEST] = new QuadTree<T>(new Rectangle2D.Double(x,            y,             subWidth, subHeight));
        Nodes[NORTHEAST] = new QuadTree<T>(new Rectangle2D.Double(x + subWidth, y,             subWidth, subHeight));
        Nodes[SOUTHWEST] = new QuadTree<T>(new Rectangle2D.Double(x,            y + subHeight, subWidth, subHeight));
        Nodes[SOUTHEAST] = new QuadTree<T>(new Rectangle2D.Double(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    public List<T> QueryRange(Rectangle range)
    {
        List<T> inRange = new ArrayList<T>();

        if (!Boundary.intersects(range))
            return inRange;

        for (T obj : Entities.values())
            if (range.contains(obj.GetPosition()))
                inRange.add(obj);

        if (IsLeaf())
            return inRange;

        for (int i = 0; i < NODE_COUNT; ++i)
            inRange.addAll(Nodes[i].QueryRange(range));

        return inRange;
    }

    public List<T> QueryRange(Point point)
    {
        List<T> inRange = new ArrayList<T>();

        if (!Boundary.contains(point))
            return inRange;

        for (T obj : Entities.values())
            if (obj.GetPosition().equals(point))
                inRange.add(obj);

        if (IsLeaf())
            return inRange;

        for (int i = 0; i < NODE_COUNT; ++i)
            inRange.addAll(Nodes[i].QueryRange(point));

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
