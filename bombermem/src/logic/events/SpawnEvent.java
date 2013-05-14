package logic.events;

import logic.WorldObjectType;

public class SpawnEvent extends Event
{
    public final WorldObjectType Type;
    
    public SpawnEvent(WorldObjectType type)
    {
        super(EventType.Spawn);
        
        Type = type;
    }
}
