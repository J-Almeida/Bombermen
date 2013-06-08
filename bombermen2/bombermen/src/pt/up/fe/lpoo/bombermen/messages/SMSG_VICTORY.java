package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

public class SMSG_VICTORY extends Message
{
    private static final long serialVersionUID = 1L;

    public SMSG_VICTORY()
    {
        super(Message.SMSG_VICTORY);
    }

    @Override
    public String toString()
    {
        return "[SMSG_VICTORY]";
    }

    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_VICTORY_Handler(this);
    }
}
