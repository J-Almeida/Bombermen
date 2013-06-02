package pt.up.fe.pt.lpoo.bombermen;

import java.awt.Point;

import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;

public class Player extends Entity
{
    Player(int guid, String name, Point pos)
    {
        super(TYPE_PLAYER, guid, pos);
        _name = name;
    }

    public String GetName()
    {
        return _name;
    }

    public void SetName(String name)
    {
        _name = name;
    }

    private String _name;

    @Override
    public SMSG_SPAWN GetSpawnMessage()
    {
        return new SMSG_SPAWN_PLAYER(GetGuid(), GetName(), GetPosition());
    }
}
