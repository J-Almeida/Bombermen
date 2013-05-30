package pt.up.fe.pt.lpoo.bombermen.messages;

public class PingMessage extends Message
{
    private static final long serialVersionUID = 1L;

    public PingMessage()
    {
        super(MessageType.PING);
    }

    @Override
    public String toString()
    {
        return "I'm a Ping message!";
    }

}
