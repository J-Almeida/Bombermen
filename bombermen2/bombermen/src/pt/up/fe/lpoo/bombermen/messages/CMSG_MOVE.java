package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

public class CMSG_MOVE extends Message
{
    private static final long serialVersionUID = 1L;

    public final int Dir;
    public final boolean Val;

    public CMSG_MOVE(int dir, boolean val)
    {
        super(CMSG_MOVE);

        Dir = dir;
        Val = val;
    }

    @Override
    public String toString()
    {
        return "[CMSG_MOVE - Dir: " + Dir + ", Val: " + Val + "]";
    }

    @Override
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.CMSG_MOVE_Handler(guid, this);
    }
}
