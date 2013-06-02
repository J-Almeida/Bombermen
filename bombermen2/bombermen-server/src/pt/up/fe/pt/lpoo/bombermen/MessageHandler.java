package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.Message;

public abstract class MessageHandler
{
    public final void HandleMessage(Message msg)
    {
        switch (msg.Type)
        {
            case Message.CMSG_MOVE:
                CMSG_MOVE_Handler((CMSG_MOVE) msg);
                break;
            case Message.CMSG_PLACE_BOMB:
                CMSG_PLACE_BOMB_Handler((CMSG_PLACE_BOMB) msg);
                break;
            default:
                Default_Handler(msg);
                break;
        }
    }

    protected abstract void CMSG_MOVE_Handler(CMSG_MOVE msg);

    protected abstract void CMSG_PLACE_BOMB_Handler(CMSG_PLACE_BOMB msg);

    protected abstract void Default_Handler(Message msg);
}
