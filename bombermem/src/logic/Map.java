package logic;

import java.util.ArrayList;

public class Map
{
    private final ArrayList<Wall> _walls;
    private final int _size;

    public Map(ArrayList<Wall> walls, int size)
    {
        _walls = walls;
        _size = size;
    }

    ArrayList<Wall> GetWalls() { return _walls; }
    int GetSize() { return _size; }
}
