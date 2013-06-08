package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

public class SMSG_DEATH extends Message
{
    private static final long serialVersionUID = 1L;

    public SMSG_DEATH()
    {
        super(Message.SMSG_DEATH);
    }

    @Override
    public String toString()
    {
        return "[SMSG_DEATH]";
    }

    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_DEATH_Handler(this);
    }
}
