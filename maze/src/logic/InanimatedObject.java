package logic;

public abstract class InanimatedObject extends WorldObject
{
    private static final long serialVersionUID = 1L;

    public final InanimatedObjectType Type;

    public InanimatedObject(InanimatedObjectType type)
    {
        super(WorldObjectType.InanimatedObject);
        Type = type;
    }

    public boolean IsExitPortal() { return Type == InanimatedObjectType.ExitPortal; }
    public boolean IsWall() { return Type == InanimatedObjectType.Wall; }
    public boolean IsPath() { return Type == InanimatedObjectType.Path; }

    public Wall ToWall() { return IsWall() ? (Wall)this : null; }
    public Path ToPath() { return IsPath() ? (Path)this : null; }
    public ExitPortal ToExitPortal() { return IsExitPortal() ? (ExitPortal)this : null; }
}
