package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

/**
 * The Class CMSG_PING.
 */
public class CMSG_PING extends Message
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new cmsg ping.
     */
    public CMSG_PING()
    {
        super(CMSG_PING);
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[CMSG_PING]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(int, pt.up.fe.lpoo.bombermen.ServerMessageHandler)
     */
    @Override
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.CMSG_PING_Handler(guid, this);
    }
}
