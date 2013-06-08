package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

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

    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_PING_Handler(this);
    }
}
