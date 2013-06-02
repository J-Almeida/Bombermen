package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.Message;

public class ClientMessage
{
    public final Message Msg;
    public final int Guid;

    public ClientMessage(int guid, Message msg)
    {
        Msg = msg;
        Guid = guid;
    }
}
