package logic;

public class Path extends InanimatedObject
{
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
