package logic;

/**
 * The Class ExitPortal.
 */
public class ExitPortal extends InanimatedObject
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new exit portal.
     */
    public ExitPortal()
    {
        super(InanimatedObjectType.ExitPortal);
    }

    /* (non-Javadoc)
     * @see logic.WorldObject#GetSymbol()
     */
    @Override
    public char GetSymbol()
    {
        return 'S';
    }
}
