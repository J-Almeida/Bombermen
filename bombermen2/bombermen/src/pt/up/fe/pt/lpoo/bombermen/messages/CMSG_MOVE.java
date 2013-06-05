package pt.up.fe.pt.lpoo.bombermen.messages;

public class CMSG_MOVE extends Message
{
    private static final long serialVersionUID = 1L;

    public final int Dir;
    public final boolean Val;

    public CMSG_MOVE(int dir, boolean val)
    {
        super(CMSG_MOVE);

        Dir = dir;
        Val = val;
    }

    @Override
    public String toString()
    {
        return "[CMSG_MOVE - Dir: " + Dir + ", Val: " + Val + "]";
    }
}
