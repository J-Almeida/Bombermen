package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

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

    @Override
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.CMSG_PING_Handler(guid, this);
    }
}
