package logic;

import java.util.ArrayList;

/**
 * Builder.
 */
public abstract class MapGenerator
{
    /** The map size. */
    protected int _size = 50;

    /** The map. */
    protected Map _map;

    /**
     * Gets the map.
     *
     * @return the map
     */
    public Map GetMap() { return _map; }

    /**
     * Creates the new map.
     */
    public void CreateNewMap() { _map = new Map(new ArrayList<Wall>(), _size); }

    /**
     * Sets the map size.
     *
     * @param size the size
     */
    public void SetMapSize(int size) { _size = size; }

    /**
     * Builds the map.
     */
    public abstract void BuildMap();
}
