package logic;

import java.io.Serializable;
import java.util.Comparator;

/**
 * The Class UnitComparator, compares two units (needed for ordering)
 */
public class UnitComparator implements Comparator<Unit>, Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Unit arg0, Unit arg1)
    {
        if (arg0.IsSameType(arg1))
            return arg0.GetId() - arg1.GetId();

        for (UnitType unt : UnitType.values())
        {
            if (arg0.Is(unt))
                return -1;
            else if (arg1.Is(unt))
                return 1;
        }

        return 0;
    }
}
