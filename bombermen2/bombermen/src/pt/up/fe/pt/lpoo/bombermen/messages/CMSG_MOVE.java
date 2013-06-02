package pt.up.fe.pt.lpoo.bombermen.messages;

public class CMSG_MOVE extends Message
{
    private static final long serialVersionUID = 1L;

    public final int Dir;

    public CMSG_MOVE(int dir)
    {
        super(CMSG_MOVE);

        Dir = dir;
    }

    @Override
    public String toString()
    {
        return "[CMSG_MOVE - dir: " + Dir + "]";
    }
}
