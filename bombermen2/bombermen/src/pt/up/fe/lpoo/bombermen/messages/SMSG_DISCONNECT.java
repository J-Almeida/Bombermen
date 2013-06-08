package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

public class SMSG_DISCONNECT extends Message
{
    private static final long serialVersionUID = 1L;

    public SMSG_DISCONNECT()
    {
        super(SMSG_DISCONNECT);
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_DISCONNECT_Handler(this);
    }

}
