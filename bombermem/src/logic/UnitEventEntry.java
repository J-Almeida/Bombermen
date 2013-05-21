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
    public UnitEventEntry(int wo, int src, Event e)
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
    	WorldObject sourceWo = gs.GetEntity(_source);
    	WorldObject entityWo = gs.GetEntity(_entity);
    	
        if (sourceWo == null || entityWo == null || gs == null || _event == null)
            return;

        entityWo.HandleEvent(gs, sourceWo, _event);
    }

    /** The unit. */
    private final int _entity;

    /** Unit who originated the event. */
    private final int _source;

    /** The event. */
    private final Event _event;
}
