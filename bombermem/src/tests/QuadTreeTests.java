package tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.awt.Point;
import java.awt.Rectangle;

import logic.WorldObject;
import model.QuadTree;

import org.junit.Test;


public class QuadTreeTests
{
    private class DummyWorldObject extends WorldObject
    {
        public DummyWorldObject(int guid, Point pos)
        {
            super(guid, pos);
        }

        @Override
        public void Update(int diff) { }

        @Override
        public boolean IsAlive()
        {
            return true;
        }
    }

    @Test
    public void TestQuadTree()
    {
        WorldObject obj1 = new DummyWorldObject(1, new Point(1, 1));
        WorldObject obj2 = new DummyWorldObject(2, new Point(3, 1));
        WorldObject obj3 = new DummyWorldObject(3, new Point(1, 3));
        WorldObject obj4 = new DummyWorldObject(4, new Point(3, 3));
        WorldObject obj5 = new DummyWorldObject(5, new Point(3, 4));
        
        QuadTree qt = new QuadTree(new Rectangle(0, 0, 5, 5));
        assertTrue(qt.Insert(obj1));
        assertTrue(qt.Insert(obj2));
        assertTrue(qt.Insert(obj3));
        assertTrue(qt.Insert(obj4));
        assertTrue(qt.Insert(obj5));
        
        assertThat(qt.QueryRange(new Rectangle(0, 0, 5, 5)).size(), is(5));
        assertThat(qt.QueryRange(new Rectangle(2, 2, 3, 3)).size(), is(2));

        qt.Clear();
        assertThat(qt.QueryRange(new Rectangle(0, 0, 5, 5)).size(), is(0));
    }
}
