package pt.up.fe.pt.lpoo.bombermen.messages;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class SMSG_MOVE extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    public final int Guid;
    public final float X;
    public final float Y;

    public SMSG_MOVE(int guid, Vector2 pos)
    {
        super(SMSG_MOVE);

        Guid = guid;
        X = pos.x;
        Y = pos.y;
    }

    @Override
    public String toString()
    {
        return "[SMSG_MOVE - Guid: " + Guid + ", X: " + X + ", Y: " + Y + "]";
    }
}
