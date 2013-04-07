package logic;

public class ExitPortal extends InanimatedObject
{
    private static final long serialVersionUID = 1L;

    public ExitPortal()
    {
        super(InanimatedObjectType.ExitPortal);
    }

    @Override
    public char GetSymbol()
    {
        return 'S';
    }
}
