package logic;

import logic.events.Event;

/**
 * The Class UnitEventEntry, used in GameState.
 */
public class UnitEventEntry
{
    /**
     * Instantiates a new unit event.
     *
     * @param wo the worldobject
     * @param e the event
     */
    public UnitEventEntry(WorldObject wo, WorldObject src, Event e)
    {
        _entity = wo;
        _source = src;
        _event = e;
    }

    /**
     * Forward event to unit.
     *
     * @param maze the maze
     */
    public void HandleEvent(GameState gs)
    {
        if (_entity == null || gs == null || _event == null)
            return;

        _entity.HandleEvent(gs, _source, _event);
    }

    /** The unit. */
    private final WorldObject _entity;

    /** Unit who originated the event. */
    private final WorldObject _source;

    /** The event. */
    private final Event _event;
}
