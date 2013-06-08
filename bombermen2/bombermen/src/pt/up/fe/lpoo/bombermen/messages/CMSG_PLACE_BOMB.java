package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

public class CMSG_PLACE_BOMB extends Message
{
    private static final long serialVersionUID = 1L;

    public CMSG_PLACE_BOMB()
    {
        super(CMSG_PLACE_BOMB);
    }

    @Override
    public String toString()
    {
        return "[CMSG_PLACE_BOMB]";
    }

    @Override
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.CMSG_PLACE_BOMB_Handler(guid, this);
    }
}
