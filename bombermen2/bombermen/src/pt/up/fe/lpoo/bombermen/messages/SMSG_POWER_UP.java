package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

/**
 * The Class SMSG_POWER_UP.
 */
public class SMSG_POWER_UP extends Message
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Type. */
    public final int Type;

    /**
     * Instantiates a new smsg power up.
     *
     * @param powerUpType the power up type
     */
    public SMSG_POWER_UP(int powerUpType)
    {
        super(Message.SMSG_POWER_UP);

        Type = powerUpType;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_POWER_UP - Type: " + Type + "]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_POWER_UP_Handler(this);
    }
}
