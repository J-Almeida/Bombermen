package pt.up.fe.lpoo.bombermen.messages;

public class SMSG_POWER_UP extends Message
{
    private static final long serialVersionUID = 1L;

    public final int Type;

    public SMSG_POWER_UP(int powerUpType)
    {
        super(Message.SMSG_POWER_UP);

        Type = powerUpType;
    }

    @Override
    public String toString()
    {
        return "[SMSG_POWER_UP - Type: " + Type + "]";
    }
}
