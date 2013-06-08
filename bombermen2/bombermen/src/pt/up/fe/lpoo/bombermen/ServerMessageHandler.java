package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PING;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.lpoo.bombermen.messages.Message;

public abstract class ServerMessageHandler
{
    public final void HandleMessage(ClientMessage CMsg)
    {
        CMsg.Msg.Handle(CMsg.Guid, this);
    }

    public abstract void CMSG_MOVE_Handler(int guid, CMSG_MOVE msg);

    public abstract void CMSG_PLACE_BOMB_Handler(int guid, CMSG_PLACE_BOMB msg);

    public abstract void CMSG_JOIN_Handler(int guid, CMSG_JOIN msg);

    public abstract void CMSG_PING_Handler(int guid, CMSG_PING msg);

    public abstract void Default_Handler(int guid, Message msg);
}
