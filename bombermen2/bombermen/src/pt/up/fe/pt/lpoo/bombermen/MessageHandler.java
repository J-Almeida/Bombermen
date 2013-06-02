package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;

public abstract class MessageHandler
{
    public final void HandleMessage(Message msg)
    {
        switch (msg.Type)
        {
            case Message.SMSG_PING:
                SMSG_PING_Handler((SMSG_PING) msg);
                break;
            case Message.SMSG_SPAWN:
                SMSG_SPAWN_Handler((SMSG_SPAWN) msg);
                break;
            default:
                Default_Handler(msg);
                break;
        }
    }

    protected abstract void SMSG_PING_Handler(SMSG_PING msg);

    protected abstract void SMSG_SPAWN_Handler(SMSG_SPAWN msg);

    protected abstract void Default_Handler(Message msg);
}
