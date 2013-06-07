package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.messages.Message;

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
