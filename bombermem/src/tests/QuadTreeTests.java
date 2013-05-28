package tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.awt.Point;
import java.awt.Rectangle;

import logic.GameState;
import logic.WorldObject;
import logic.events.Event;
import model.QuadTree;

import org.junit.Test;


public class QuadTreeTests
{
    private class DummyWorldObject extends WorldObject
    {
        public DummyWorldObject(int guid, Point pos)
        {
            super(null, guid, pos);
        }

        @Override public void Update(GameState gs, int diff) { }
        @Override public boolean IsAlive() { return true; }
        @Override public void HandleEvent(GameState gs, WorldObject src, Event event) { }
    }

    @Test
    public void TestQuadTree()
    {
        QuadTree<DummyWorldObject> qt = new QuadTree<DummyWorldObject>(new Rectangle(0, 0, 5, 5));

        assertTrue(qt.Insert(new DummyWorldObject(1, new Point(1, 1))));
        assertTrue(qt.Insert(new DummyWorldObject(2, new Point(3, 1))));
        assertTrue(qt.Insert(new DummyWorldObject(3, new Point(1, 3))));
        assertTrue(qt.Insert(new DummyWorldObject(4, new Point(3, 3))));
        assertTrue(qt.Insert(new DummyWorldObject(5, new Point(3, 4)))); // forces subdivision, max 4 elements

        assertThat(qt.QueryRange(new Rectangle(0, 0, 5, 5)).size(), is(5));
        assertThat(qt.QueryRange(new Rectangle(2, 2, 3, 3)).size(), is(2));

        assertThat(qt.QueryRange(new Point(1, 1)).size(), is(1));

        qt.Clear();
        assertThat(qt.QueryRange(new Rectangle(0, 0, 5, 5)).size(), is(0));
    }
}
