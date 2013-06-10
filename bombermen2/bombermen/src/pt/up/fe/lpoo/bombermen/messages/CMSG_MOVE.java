package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

/**
 * The Class CMSG_MOVE.
 */
public class CMSG_MOVE extends Message
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Direction. */
    public final int Dir;

    /** True if keypressed, false if keyreleased. */
    public final boolean Val;

    /**
     * Instantiates a new cmsg move.
     *
     * @param dir the dir
     * @param val the val
     */
    public CMSG_MOVE(int dir, boolean val)
    {
        super(CMSG_MOVE);

        Dir = dir;
        Val = val;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[CMSG_MOVE - Dir: " + Dir + ", Val: " + Val + "]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(int, pt.up.fe.lpoo.bombermen.ServerMessageHandler)
     */
    @Override
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.CMSG_MOVE_Handler(guid, this);
    }
}
