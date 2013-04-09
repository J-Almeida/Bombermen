package logic;

import java.io.Serializable;
import java.util.Comparator;

/**
 * The Class EventComparator, compares two events (needed for ordering)
 */
public class EventComparator implements Comparator<Event>, Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
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
