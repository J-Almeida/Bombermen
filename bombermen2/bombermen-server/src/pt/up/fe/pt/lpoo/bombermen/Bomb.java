package pt.up.fe.pt.lpoo.bombermen;

import java.awt.Point;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;

public class Bomb extends Entity
{

    Bomb(int guid, Point pos)
    {
        super(TYPE_BOMB, guid, pos);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_BOMB(GetGuid(), 0);
    }

}
