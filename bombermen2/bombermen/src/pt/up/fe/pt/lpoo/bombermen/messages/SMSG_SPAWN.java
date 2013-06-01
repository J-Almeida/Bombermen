package pt.up.fe.pt.lpoo.bombermen.messages;

public class SMSG_SPAWN extends Message
{
    private static final long serialVersionUID = 1L;

    public SMSG_SPAWN()
    {
        super(Message.SMSG_SPAWN);
    }

    @Override
    public String toString()
    {
        return "[SMSG_SPAWN]";
    }
}
