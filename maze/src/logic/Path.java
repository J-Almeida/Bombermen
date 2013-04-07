package logic;

public class Path extends InanimatedObject
{
    private static final long serialVersionUID = 1L;

    public Path()
    {
        super(InanimatedObjectType.Path);
    }

    @Override
    public char GetSymbol()
    {
        return ' ';
    }
}
