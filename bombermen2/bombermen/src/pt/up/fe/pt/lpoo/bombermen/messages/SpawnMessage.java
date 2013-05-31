package pt.up.fe.pt.lpoo.bombermen.messages;

public class SpawnMessage extends Message
{
    private static final long serialVersionUID = 1L;

    public SpawnMessage()
    {
        super(Message.TYPE_SPAWN);
    }

    @Override
    public String toString()
    {
        return "[Message: Spawn]";
    }
}
