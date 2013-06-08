package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

public class CMSG_JOIN extends Message
{
    private static final long serialVersionUID = 1L;

    public CMSG_JOIN(String name)
    {
        super(CMSG_JOIN);
        Name = name;
    }

    public final String Name;

    @Override
    public String toString()
    {
        return "[CMSG_JOIN - Name: " + Name + "]";
    }

    @Override
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.CMSG_JOIN_Handler(guid, this);
    }
}
