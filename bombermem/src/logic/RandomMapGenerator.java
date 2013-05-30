package logic;

/**
 * ConcreteBuilder - Randomized NxN map (using Depth First Search with backtracking).
 */
public class RandomMapGenerator extends MapGenerator
{
    /* (non-Javadoc)
     * @see logic.MapGenerator#BuildMap()
     */
    @Override
    public void BuildMap()
    {
        /*
        for (int x = 0; x < _size; ++x)
            for (int y = 0; y < _size; ++y)
                if (x == 0 || x == _size - 3 || y == 0 || y == _size - 3 || (x % 2 == 0 && y % 2 == 0))
                    _map.GetWalls().add(new Wall)
                    _objectBuilder.CreateWall(-1, new Point(x, y));

        Map<Integer, Point> map = new HashMap<Integer, Point>();

        Random r = new Random();

        while (map.size() != 500)
        {
            int x = r.nextInt(50);
            int y = r.nextInt(50);

            int hash = (int)(1.0/2.0 * (x + y) * (x + y + 1) + y);
            if (map.containsKey(hash))
                continue;

            if (x == 0 || y == 0)
                continue;

            if ((x % 2) == 0 && (y % 2) == 0)
                continue;

            if ((x == 1 && y == 1) || ((x == 1) && (y == 2)) || ((x == 2) && (y == 1)))
                continue;

            map.put(hash, new Point(x, y));
        }

        for (Point p : map.values())
            _map.GetWalls().add(e)
        */
    }
}
