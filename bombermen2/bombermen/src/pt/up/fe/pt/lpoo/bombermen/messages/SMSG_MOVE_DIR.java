package pt.up.fe.pt.lpoo.bombermen.messages;

public class SMSG_MOVE_DIR extends Message
{
    private static final long serialVersionUID = 1L;

    public final int Guid;
    public final int Dir;
    public final boolean Val;

    public SMSG_MOVE_DIR(int guid, int dir, boolean val)
    {
        super(SMSG_MOVE_DIR);
        Guid = guid;
        Dir = dir;
        Val = val;
    }

    @Override
    public String toString()
    {
        return "[SMSG_MOVE_DIR - Guid: " + Guid + ", Dir: " + Dir + ", Val: " + Val + "]";
    }

}
