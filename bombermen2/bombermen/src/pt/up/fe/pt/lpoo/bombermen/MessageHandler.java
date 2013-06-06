package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_DEATH;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_MOVE_DIR;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_POWER_UP;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_VICTORY;

public abstract class MessageHandler
{
    public final void HandleMessage(Message msg)
    {
        System.out.println("Debug: " + msg);

        switch (msg.Type)
        {
            case Message.SMSG_MOVE:
                SMSG_MOVE_Handler((SMSG_MOVE) msg);
                break;
            case Message.SMSG_PING:
                SMSG_PING_Handler((SMSG_PING) msg);
                break;
            case Message.SMSG_SPAWN:
                SMSG_SPAWN_Handler((SMSG_SPAWN) msg);
                break;
            case Message.SMSG_DESTROY:
                SMSG_DESTROY_Handler((SMSG_DESTROY) msg);
                break;
            case Message.SMSG_DEATH:
                SMSG_DEATH_Handler((SMSG_DEATH) msg);
                break;
            case Message.SMSG_VICTORY:
                SMSG_VICTORY_Handler((SMSG_VICTORY) msg);
                break;
            case Message.SMSG_POWER_UP:
                SMSG_POWER_UP_Handler((SMSG_POWER_UP) msg);
                break;
            case Message.SMSG_MOVE_DIR:
                SMSG_MOVE_DIR_Handler((SMSG_MOVE_DIR) msg);
                break;
            default:
                Default_Handler(msg);
                break;
        }
    }

    protected abstract void SMSG_PING_Handler(SMSG_PING msg);

    protected abstract void SMSG_SPAWN_Handler(SMSG_SPAWN msg);

    protected abstract void SMSG_MOVE_Handler(SMSG_MOVE msg);

    protected abstract void SMSG_DESTROY_Handler(SMSG_DESTROY msg);

    protected abstract void SMSG_DEATH_Handler(SMSG_DEATH msg);

    protected abstract void SMSG_VICTORY_Handler(SMSG_VICTORY msg);

    protected abstract void SMSG_POWER_UP_Handler(SMSG_POWER_UP msg);
    
    protected abstract void SMSG_MOVE_DIR_Handler(SMSG_MOVE_DIR msg);

    protected abstract void Default_Handler(Message msg);

}
