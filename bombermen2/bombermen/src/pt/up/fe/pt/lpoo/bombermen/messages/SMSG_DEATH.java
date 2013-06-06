package pt.up.fe.pt.lpoo.bombermen.messages;

public class SMSG_DEATH extends Message
{
    private static final long serialVersionUID = 1L;

    public SMSG_DEATH()
    {
        super(Message.SMSG_DEATH);
    }

    @Override
    public String toString()
    {
        return "[SMSG_DEATH]";
    }
}
