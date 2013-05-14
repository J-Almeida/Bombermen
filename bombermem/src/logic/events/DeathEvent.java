package logic.events;

import logic.DeathReason;

public class DeathEvent extends Event
{
    public final DeathReason Reason;
    
    public DeathEvent(DeathReason reason)
    {
        super(EventType.Death);

        Reason = reason;
    }
}
