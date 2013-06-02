package pt.up.fe.pt.lpoo.bombermen.messages;

public abstract class SMSG_SPAWN extends Message
{
    private static final long serialVersionUID = 1L;

    public final int EntityType;
    public final int Guid;

    public SMSG_SPAWN(int entityType, int guid)
    {
        super(Message.SMSG_SPAWN);
        EntityType = entityType;
        Guid = guid;
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN]";
    }
}
