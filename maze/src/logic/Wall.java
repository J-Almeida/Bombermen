package logic;

public class Wall extends InanimatedObject
{
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
