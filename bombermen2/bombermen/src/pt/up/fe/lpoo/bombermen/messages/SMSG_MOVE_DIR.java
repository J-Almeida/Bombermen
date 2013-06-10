package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

/**
 * The Class SMSG_MOVE_DIR.
 */
public class SMSG_MOVE_DIR extends Message
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Guid. */
    public final int Guid;

    /** The direction. */
    public final int Dir;

    /** True if keypressed, false if keyreleased. */
    public final boolean Val;

    /**
     * Instantiates a new smsg move dir.
     *
     * @param guid the guid
     * @param dir the dir
     * @param val the val
     */
    public SMSG_MOVE_DIR(int guid, int dir, boolean val)
    {
        super(SMSG_MOVE_DIR);
        Guid = guid;
        Dir = dir;
        Val = val;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_MOVE_DIR - Guid: " + Guid + ", Dir: " + Dir + ", Val: " + Val + "]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_MOVE_DIR_Handler(this);
    }
}
