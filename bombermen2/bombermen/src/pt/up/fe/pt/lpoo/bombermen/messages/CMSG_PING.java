package pt.up.fe.pt.lpoo.bombermen.messages;

public class CMSG_PING extends Message
{
    private static final long serialVersionUID = 1L;

    public CMSG_PING()
    {
        super(CMSG_PING);
    }

    @Override
    public String toString()
    {
        return "[CMSG_PING]";
    }

}
