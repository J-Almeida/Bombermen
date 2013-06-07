package pt.up.fe.pt.lpoo.bombermen.messages;

public class SMSG_JOIN extends Message
{
    private static final long serialVersionUID = 1L;
    
    public final int Guid;
    
    public SMSG_JOIN(int guid)
    {
        super(SMSG_JOIN);
        Guid = guid;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
