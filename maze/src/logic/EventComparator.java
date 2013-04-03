package logic;

import java.util.Comparator;


public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        if (o1.IsSameType(o2))
            return 0;

        for (EventType evt : EventType.values())
        {
            if (o1.Is(evt)) return -1;
            else if (o2.Is(evt)) return 1;
        }
        return 0;
    }

}
