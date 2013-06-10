package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

/**
 * The Class CMSG_PLACE_BOMB.
 */
public class CMSG_PLACE_BOMB extends Message
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new cmsg place bomb.
     */
    public CMSG_PLACE_BOMB()
    {
        super(CMSG_PLACE_BOMB);
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[CMSG_PLACE_BOMB]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(int, pt.up.fe.lpoo.bombermen.ServerMessageHandler)
     */
    @Override
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.CMSG_PLACE_BOMB_Handler(guid, this);
    }
}
