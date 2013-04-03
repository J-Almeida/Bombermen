package logic;

public class UnitEvent implements Comparable<UnitEvent>
{
    @Override
    public int compareTo(UnitEvent arg0)
    {
        int eventCmp = _eventComparator.compare(_event, arg0._event);

        if (eventCmp == 0)
            return _unitComparator.compare(_unit, arg0._unit);

        return eventCmp;
    }

    public UnitEvent(Unit u, Event e)
    {
        _unit = u;
        _event = e;
    }

    public void HandleEvent(Maze maze)
    {
        if (_unit == null || maze == null || _event == null)
            return;

        _unit.HandleEvent(maze, _event);
    }

    private final Unit _unit;
    private final Event _event;

    private static final UnitComparator _unitComparator = new UnitComparator();
    private static final EventComparator _eventComparator = new EventComparator();
}
