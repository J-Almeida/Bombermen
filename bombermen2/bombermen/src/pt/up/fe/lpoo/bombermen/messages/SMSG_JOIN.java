package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

/**
 * The Class SMSG_JOIN.
 */
public class SMSG_JOIN extends Message
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Guid. */
    public final int Guid;

    /** The Map width. */
    public final int MapWidth;

    /** The Map height. */
    public final int MapHeight;

    /**
     * Instantiates a new smsg join.
     *
     * @param guid the guid
     * @param mapWidth the map width
     * @param mapHeight the map height
     */
    public SMSG_JOIN(int guid, int mapWidth, int mapHeight)
    {
        super(SMSG_JOIN);
        Guid = guid;
        MapWidth = mapWidth;
        MapHeight = mapHeight;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        return "[SMSG_JOIN - Guid: " + Guid + " - MapWidth: " + MapWidth + " - MapHeight: " + MapHeight + "]";
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_JOIN_Handler(this);
    }
}
