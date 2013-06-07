package pt.up.fe.lpoo.bombermen.messages;

public class SMSG_VICTORY extends Message
{
    private static final long serialVersionUID = 1L;

    public SMSG_VICTORY()
    {
        super(Message.SMSG_VICTORY);
    }

    @Override
    public String toString()
    {
        return "[SMSG_VICTORY]";
    }
}
