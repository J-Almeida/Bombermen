package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

public abstract class SMSG_SPAWN extends Message
{
    private static final long serialVersionUID = 1L;

    public final int EntityType;
    public final int Guid;
    public final float X;
    public final float Y;

    public SMSG_SPAWN(int entityType, int guid, float x, float y)
    {
        super(Message.SMSG_SPAWN);
        EntityType = entityType;
        Guid = guid;
        X = x;
        Y = y;
    }

    @Override
    public abstract String toString();

    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_SPAWN_Handler(this);
    }
}
