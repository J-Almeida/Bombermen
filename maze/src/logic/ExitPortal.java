package logic;

public class ExitPortal extends InanimatedObject
{
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
