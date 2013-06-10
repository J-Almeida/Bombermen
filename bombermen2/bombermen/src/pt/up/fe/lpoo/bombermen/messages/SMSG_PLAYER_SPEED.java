package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

/**
 * The Class SMSG_PLAYER_SPEED.
 */
public class SMSG_PLAYER_SPEED extends Message
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Speed. */
    public final float Speed;

    /** The Guid. */
    public final int Guid;

    /**
     * Instantiates a new smsg player speed.
     *
     * @param guid the guid
     * @param speed the speed
     */
    public SMSG_PLAYER_SPEED(int guid, float speed)
    {
        super(SMSG_PLAYER_SPEED);
        Guid = guid;
        Speed = speed;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_PLAYER_SPEED - Speed : " + Speed + "]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_PLAYER_SPEED_Handler(this);
    }
}
