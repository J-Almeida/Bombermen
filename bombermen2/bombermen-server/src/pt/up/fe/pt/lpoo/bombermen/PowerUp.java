package pt.up.fe.pt.lpoo.bombermen;

import java.awt.Point;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;

public class PowerUp extends Entity
{
    public PowerUp(int guid, Point pos)
    {
        super(TYPE_POWER_UP, guid, pos);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_POWER_UP(GetGuid());
    }
}
