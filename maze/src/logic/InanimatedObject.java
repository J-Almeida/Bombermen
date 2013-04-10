package logic;

/**
 * Base class for basic objects that can't move (walls and pathways)
 */
public abstract class InanimatedObject extends WorldObject
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The inanimated object type. */
    public final InanimatedObjectType Type;

    /**
     * Instantiates a new inanimated object.
     *
     * @param type the type
     */
    public InanimatedObject(InanimatedObjectType type)
    {
        super(WorldObjectType.InanimatedObject);
        Type = type;
    }

    /**
     * Checks if is exit portal.
     *
     * @return true, if successful
     */
    public boolean IsExitPortal() { return Type == InanimatedObjectType.ExitPortal; }

    /**
     * Checks if is wall.
     *
     * @return true, if successful
     */
    public boolean IsWall() { return Type == InanimatedObjectType.Wall; }

    /**
     * Checks if is path.
     *
     * @return true, if successful
     */
    public boolean IsPath() { return Type == InanimatedObjectType.Path; }

    /**
     * To wall.
     *
     * @return the wall
     */
    public Wall ToWall() { return IsWall() ? (Wall)this : null; }

    /**
     * Cast to path.
     *
     * @return the path
     */
    public Path ToPath() { return IsPath() ? (Path)this : null; }

    /**
     * Cast to exit portal.
     *
     * @return the exit portal
     */
    public ExitPortal ToExitPortal() { return IsExitPortal() ? (ExitPortal)this : null; }
}
