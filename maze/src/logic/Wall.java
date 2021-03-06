package logic;

/**
 * Represents a wall in the maze, can't be transfixed.
 */
public class Wall extends InanimatedObject
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new wall.
     */
    public Wall()
    {
        super(InanimatedObjectType.Wall);
    }

    /* (non-Javadoc)
     * @see logic.WorldObject#GetSymbol()
     */
    @Override
    public char GetSymbol()
    {
        return 'X';
    }
}
