package logic;

import model.Position;

/**
 * The WorldObject Unit.
 */
public abstract class WorldObject
{
    public final WorldObjectType Type;

    public WorldObject(WorldObjectType type)
    {
        Type = type;
    }

    private static int _lastId = 0;
    private int _id = ++_lastId;

    public int GetId() { return _id; }

    public final static Position DEFAULT_POSITION = new Position();
    protected Position _position = DEFAULT_POSITION;
    public Position GetPosition() { return _position; }
    public void SetPosition(Position pos) { _position = pos; }

    public boolean IsInanimatedObject() { return Type == WorldObjectType.InanimatedObject; }
    public boolean IsUnit() { return Type == WorldObjectType.Unit; }

    public Unit ToUnit() { return IsUnit() ? (Unit)this : null; }
    public InanimatedObject ToInanimatedObject() { return IsInanimatedObject() ? (InanimatedObject)this : null; }

    public abstract char GetSymbol();

    @Override
    public final String toString()
    {
        return String.valueOf(GetSymbol());
    }
}
