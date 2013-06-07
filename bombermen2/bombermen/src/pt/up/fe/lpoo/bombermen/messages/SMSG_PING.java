package pt.up.fe.lpoo.bombermen.messages;

public class SMSG_PING extends Message
{
    private static final long serialVersionUID = 1L;

    public SMSG_PING()
    {
        super(Message.SMSG_PING);
    }

    @Override
    public String toString()
    {
        return "[SMSG_PING]";
    }
}
