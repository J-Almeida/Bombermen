package pt.up.fe.pt.lpoo.bombermen.messages;

public class CMSG_JOIN extends Message
{
    private static final long serialVersionUID = 1L;

    public CMSG_JOIN(String name)
    {
        super(CMSG_JOIN);
        Name = name;
    }

    public final String Name;

}
