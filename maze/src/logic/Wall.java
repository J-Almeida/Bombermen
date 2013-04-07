package logic;

public class Wall extends InanimatedObject
{
    private static final long serialVersionUID = 1L;

    public Wall()
    {
        super(InanimatedObjectType.Wall);
    }

    @Override
    public char GetSymbol()
    {
        return 'X';
    }
}
