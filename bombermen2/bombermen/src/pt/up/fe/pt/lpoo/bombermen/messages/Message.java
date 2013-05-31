package pt.up.fe.pt.lpoo.bombermen.messages;

import java.io.Serializable;

public abstract class Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final int TYPE_PING = 0;
    public static final int TYPE_SPAWN = 1;

    public Message(int type)
    {
        Type = type;
    }

    public final int Type;
}
