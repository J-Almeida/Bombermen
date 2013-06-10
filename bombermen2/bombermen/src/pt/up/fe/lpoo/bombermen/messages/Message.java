package pt.up.fe.lpoo.bombermen.messages;

import java.io.Serializable;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;
import pt.up.fe.lpoo.bombermen.ServerMessageHandler;

/**
 * The Class Message.
 */
public abstract class Message implements Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant SMSG_PING. */
    public static final int SMSG_PING = 0;

    /** The Constant SMSG_SPAWN. */
    public static final int SMSG_SPAWN = 1;

    /** The Constant SMSG_MOVE. */
    public static final int SMSG_MOVE = 2;

    /** The Constant SMSG_DESTROY. */
    public static final int SMSG_DESTROY = 3;

    /** The Constant CMSG_MOVE. */
    public static final int CMSG_MOVE = 4;

    /** The Constant CMSG_PLACE_BOMB. */
    public static final int CMSG_PLACE_BOMB = 5;

    /** The Constant CMSG_JOIN. */
    public static final int CMSG_JOIN = 6;

    /** The Constant CMSG_PING. */
    public static final int CMSG_PING = 7;

    /** The Constant SMSG_DEATH. */
    public static final int SMSG_DEATH = 8;

    /** The Constant SMSG_VICTORY. */
    public static final int SMSG_VICTORY = 9;

    /** The Constant SMSG_POWER_UP. */
    public static final int SMSG_POWER_UP = 10;

    /** The Constant SMSG_MOVE_DIR. */
    public static final int SMSG_MOVE_DIR = 11;

    /** The Constant SMSG_PLAYER_SPEED. */
    public static final int SMSG_PLAYER_SPEED = 12;

    /** The Constant SMSG_JOIN. */
    public static final int SMSG_JOIN = 13;

    /** The Constant SMSG_SCORE. */
    public static final int SMSG_SCORE = 14;

    /** The Constant SMSG_DISCONNECT. */
    public static final int SMSG_DISCONNECT = 15;

    /** The message type (RTTI). */
    public final int Type;

    /**
     * Instantiates a new message.
     *
     * @param type the type
     */
    public Message(int type)
    {
        Type = type;
    }

    /**
     * Handle.
     *
     * @param cmh the client message handler
     */
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.Default_Handler(this);
    }

    /**
     * Handle.
     *
     * @param guid the guid
     * @param cmh the cmh
     */
    public void Handle(int guid, ServerMessageHandler cmh)
    {
        cmh.Default_Handler(guid, this);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public abstract String toString();
}
