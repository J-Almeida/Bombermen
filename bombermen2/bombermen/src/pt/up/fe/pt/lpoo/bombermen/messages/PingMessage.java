package pt.up.fe.pt.lpoo.bombermen.messages;

public class PingMessage extends Message
{
    private static final long serialVersionUID = 1L;

    public PingMessage()
    {
        super(Message.TYPE_PING);
    }

    @Override
    public String toString()
    {
        return "[Message: Ping]";
    }
}
