package logic;

/**
 * Director - responsible for managing the correct sequence of Map creation.
 */
public class Architect
{
    /** Underlying MapGenerator. */
    private MapGenerator _mapGenerator;

    /**
     * Defines the concrete MapGenerator.
     *
     * @param mg the mapgenerator
     */
    public void SetMapGenerator(MapGenerator mg) { _mapGenerator = mg; }

    /**
     * Returns the concrete MapGenerator, call after ConstructMap.
     *
     * @return the map
     */
    public Map GetMap() { return _mapGenerator.GetMap(); }

    /**
     * Builds the Map.
     *
     * @param size the size
     */
    public void ConstructMaze(int size)
    {
        _mapGenerator.SetMapSize(size);
        _mapGenerator.CreateNewMap();

        _mapGenerator.BuildMap();
    }
}
