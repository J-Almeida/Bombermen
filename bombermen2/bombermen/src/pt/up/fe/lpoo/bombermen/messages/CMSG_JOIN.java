package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

/**
 * The Class CMSG_JOIN.
 */
public class CMSG_JOIN extends Message
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new cmsg join.
     *
     * @param name the name
     */
    public CMSG_JOIN(String name)
    {
        super(CMSG_JOIN);
        Name = name;
    }

    /** The Name. */
    public final String Name;

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[CMSG_JOIN - Name: " + Name + "]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(int, pt.up.fe.lpoo.bombermen.ServerMessageHandler)
     */
    @Override
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.CMSG_JOIN_Handler(guid, this);
    }
}
