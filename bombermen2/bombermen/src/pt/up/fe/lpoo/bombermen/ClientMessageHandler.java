package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.messages.Message;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DEATH;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DISCONNECT;
import pt.up.fe.lpoo.bombermen.messages.SMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.SMSG_MOVE_DIR;
import pt.up.fe.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.lpoo.bombermen.messages.SMSG_PLAYER_SPEED;
import pt.up.fe.lpoo.bombermen.messages.SMSG_POWER_UP;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SCORE;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_VICTORY;

/**
 * The Class ClientMessageHandler.
 */
public abstract class ClientMessageHandler
{
    /**
     * Handle message.
     *
     * @param msg the msg
     */
    public final void HandleMessage(Message msg)
    {
        System.out.println("Debug: " + msg);

        msg.Handle(this);
    }

    /**
     * ping handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_PING_Handler(SMSG_PING msg);

    /**
     * entity spawned handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_SPAWN_Handler(SMSG_SPAWN msg);

    /**
     * entity moved handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_MOVE_Handler(SMSG_MOVE msg);

    /**
     * entity removed handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_DESTROY_Handler(SMSG_DESTROY msg);

    /**
     * player killed handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_DEATH_Handler(SMSG_DEATH msg);

    /**
     * victory handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_VICTORY_Handler(SMSG_VICTORY msg);

    /**
     * pickup powerup handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_POWER_UP_Handler(SMSG_POWER_UP msg);

    /**
     * move direction change handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_MOVE_DIR_Handler(SMSG_MOVE_DIR msg);

    /**
     * player speed change handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_PLAYER_SPEED_Handler(SMSG_PLAYER_SPEED msg);

    /**
     * join game handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_JOIN_Handler(SMSG_JOIN msg);

    /**
     * score change handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_SCORE_Handler(SMSG_SCORE msg);

    /**
     * disconnect handler
     *
     * @param msg the msg
     */
    public abstract void SMSG_DISCONNECT_Handler(SMSG_DISCONNECT msg);

    /**
     * default handler
     *
     * @param msg the msg
     */
    public abstract void Default_Handler(Message msg);

}
