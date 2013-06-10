package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

/**
 * The Class SMSG_DESTROY.
 */
public class SMSG_DESTROY extends Message
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Guid. */
    public final int Guid;

    /**
     * Instantiates a new smsg destroy.
     *
     * @param guid the guid
     */
    public SMSG_DESTROY(int guid)
    {
        super(SMSG_DESTROY);
        Guid = guid;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_DESTROY - Guid: " + Guid + "]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_DESTROY_Handler(this);
    }
}
