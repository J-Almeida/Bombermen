package pt.up.fe.pt.lpoo.bombermen.messages;

public class CMSG_MOVE extends Message
{
    private static final long serialVersionUID = 1L;

    private int _direction;

    public CMSG_MOVE(int dir)
    {
        super(CMSG_MOVE);

        _direction = dir;
    }

    @Override
    public String toString()
    {
        return "[CMSG_MOVE - dir: " + _direction + "]";
    }
}
