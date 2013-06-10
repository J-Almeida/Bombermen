package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

/**
 * The Class SMSG_SPAWN.
 */
public abstract class SMSG_SPAWN extends Message
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Entity type. */
    public final int EntityType;

    /** The Guid. */
    public final int Guid;

    /** The x. */
    public final float X;

    /** The y. */
    public final float Y;

    /**
     * Instantiates a new smsg spawn.
     *
     * @param entityType the entity type
     * @param guid the guid
     * @param x the x
     * @param y the y
     */
    public SMSG_SPAWN(int entityType, int guid, float x, float y)
    {
        super(Message.SMSG_SPAWN);
        EntityType = entityType;
        Guid = guid;
        X = x;
        Y = y;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public abstract String toString();

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_SPAWN_Handler(this);
    }
}
