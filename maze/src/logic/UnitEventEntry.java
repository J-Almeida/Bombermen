package logic;

/**
 * The Class UnitEventEntry, used in Maze.
 */
public class UnitEventEntry implements Comparable<UnitEventEntry>
{
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(UnitEventEntry arg0)
    {
        int eventCmp = _eventComparator.compare(_event, arg0._event);

        if (eventCmp == 0)
            return _unitComparator.compare(_unit, arg0._unit);

        return eventCmp;
    }

    /**
     * Instantiates a new unit event.
     *
     * @param u the unit
     * @param e the event
     */
    public UnitEventEntry(Unit u, Event e)
    {
        _unit = u;
        _event = e;
    }

    /**
     * Forward event to unit.
     *
     * @param maze the maze
     */
    public void HandleEvent(Maze maze)
    {
        if (_unit == null || maze == null || _event == null)
            return;

        _unit.HandleEvent(maze, _event);
    }

    /** The unit. */
    private final Unit _unit;

    /** The event. */
    private final Event _event;

    /** The Constant _unitComparator. */
    private static final UnitComparator _unitComparator = new UnitComparator();

    /** The Constant _eventComparator. */
    private static final EventComparator _eventComparator = new EventComparator();
}
