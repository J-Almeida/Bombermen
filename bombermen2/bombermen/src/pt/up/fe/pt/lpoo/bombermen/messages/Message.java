package pt.up.fe.pt.lpoo.bombermen.messages;

import java.io.Serializable;

public abstract class Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    public Message(int type)
    {
        Type = type;
    }

    public final int Type;
}
