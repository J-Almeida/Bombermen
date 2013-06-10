package pt.up.fe.lpoo.bombermen.messages;

import java.io.Serializable;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

import com.badlogic.gdx.math.Vector2;

/**
 * The Class SMSG_MOVE.
 */
public class SMSG_MOVE extends Message implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Guid. */
    public final int Guid;

    /** The x. */
    public final float X;

    /** The y. */
    public final float Y;

    /**
     * Instantiates a new smsg move.
     *
     * @param guid the guid
     * @param pos the pos
     */
    public SMSG_MOVE(int guid, Vector2 pos)
    {
        super(SMSG_MOVE);

        Guid = guid;
        X = pos.x;
        Y = pos.y;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_MOVE - Guid: " + Guid + ", X: " + X + ", Y: " + Y + "]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_MOVE_Handler(this);
    }
}
