package pt.up.fe.pt.lpoo.bombermen;

import java.awt.Point;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;

public class Explosion extends Entity
{
    public Explosion(int guid, Point pos)
    {
        super(TYPE_EXPLOSION, guid, pos);
    }

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_EXPLOSION(GetGuid());
    }
}
