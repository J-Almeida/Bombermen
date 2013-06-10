package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PING;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.lpoo.bombermen.messages.Message;

/**
 * The Class ServerMessageHandler.
 */
public abstract class ServerMessageHandler
{
    /**
     * Handle message.
     *
     * @param CMsg the message
     */
    public final void HandleMessage(ClientMessage CMsg)
    {
        CMsg.Msg.Handle(CMsg.Guid, this);
    }

    /**
     * Move request handler
     *
     * @param guid the guid
     * @param msg the msg
     */
    public abstract void CMSG_MOVE_Handler(int guid, CMSG_MOVE msg);

    /**
     * Place bomb request handler
     *
     * @param guid the guid
     * @param msg the msg
     */
    public abstract void CMSG_PLACE_BOMB_Handler(int guid, CMSG_PLACE_BOMB msg);

    /**
     * Join request handler
     *
     * @param guid the guid
     * @param msg the msg
     */
    public abstract void CMSG_JOIN_Handler(int guid, CMSG_JOIN msg);

    /**
     * Ping handler
     *
     * @param guid the guid
     * @param msg the msg
     */
    public abstract void CMSG_PING_Handler(int guid, CMSG_PING msg);

    /**
     * Default handler.
     *
     * @param guid the guid
     * @param msg the msg
     */
    public abstract void Default_Handler(int guid, Message msg);
}
