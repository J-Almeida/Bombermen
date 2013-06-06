package pt.up.fe.pt.lpoo.bombermen.messages;

public class SMSG_PLAYER_SPEED extends Message
{
    private static final long serialVersionUID = 1L;
    
    public final float Speed;
    public final int Guid;
    
    public SMSG_PLAYER_SPEED(int guid, float speed)
    {
        super(SMSG_PLAYER_SPEED);
        Guid = guid;
        Speed = speed;
    }

    @Override
    public String toString()
    {
        return "[SMSG_PLAYER_SPEED - Speed : " + Speed + "]";
    }

}
