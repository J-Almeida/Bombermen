package logic;

/**
 * The Class Dragon.
 */
public class Dragon extends Unit
{
    private boolean _asleep;

    /**
     * Instantiates a new dragon.
     */
    public Dragon()
    {
        super(UnitType.Dragon);
    }

    @Override
    public String toString()
    {
        return _asleep ? "d" : "D";
    }
}
