package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

/**
 * The Class SMSG_DEATH.
 */
public class SMSG_DEATH extends Message
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new smsg death.
     */
    public SMSG_DEATH()
    {
        super(Message.SMSG_DEATH);
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_DEATH]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_DEATH_Handler(this);
    }
}
