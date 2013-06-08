package pt.up.fe.lpoo.bombermen.messages;

public class SMSG_JOIN extends Message
{
    private static final long serialVersionUID = 1L;

    public final int Guid;
    public final int MapWidth;
    public final int MapHeight;
    
    public SMSG_JOIN(int guid, int mapWidth, int mapHeight)
    {
        super(SMSG_JOIN);
        Guid = guid;
        MapWidth = mapWidth;
        MapHeight = mapHeight;
    }

    @Override
    public String toString()
    {
        return "[SMSG_JOIN - Guid: " + Guid + " - MapWidth: " + MapWidth + " - MapHeight: " + MapHeight + "]";
    }

}
