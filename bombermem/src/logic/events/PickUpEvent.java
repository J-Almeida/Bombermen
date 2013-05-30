package logic.events;

import logic.Player;

public class PickUpEvent extends Event
{
    public final Player Player;
    
    public boolean Handled = false;
    
    public PickUpEvent(Player player)
    {
        super(EventType.PickUp);

        Player = player;
    }

}
