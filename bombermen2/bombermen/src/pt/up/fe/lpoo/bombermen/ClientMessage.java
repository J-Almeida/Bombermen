package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.messages.Message;

/**
 * The Class ClientMessage.
 */
public class ClientMessage
{

    /** The message. */
    public final Message Msg;

    /** The player guid. */
    public final int Guid;

    /**
     * Instantiates a new client message.
     *
     * @param guid the guid
     * @param msg the msg
     */
    public ClientMessage(int guid, Message msg)
    {
        Msg = msg;
        Guid = guid;
    }
}
