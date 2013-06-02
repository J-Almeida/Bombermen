package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.Message;

public abstract class MessageHandler
{
    public final void HandleMessage(ClientMessage CMsg)
    {
        switch (CMsg.Msg.Type)
        {
            case Message.CMSG_MOVE:
                CMSG_MOVE_Handler(CMsg.Guid, (CMSG_MOVE) CMsg.Msg);
                break;
            case Message.CMSG_PLACE_BOMB:
                CMSG_PLACE_BOMB_Handler(CMsg.Guid, (CMSG_PLACE_BOMB) CMsg.Msg);
                break;
            case Message.CMSG_JOIN:
                CMSG_JOIN_Handler(CMsg.Guid, (CMSG_JOIN) CMsg.Msg);
                break;
            default:
                Default_Handler(CMsg.Guid, CMsg.Msg);
                break;
        }
    }

    protected abstract void CMSG_MOVE_Handler(int guid, CMSG_MOVE msg);

    protected abstract void CMSG_PLACE_BOMB_Handler(int guid, CMSG_PLACE_BOMB msg);

    protected abstract void CMSG_JOIN_Handler(int guid, CMSG_JOIN msg);

    protected abstract void Default_Handler(int guid, Message msg);
}
