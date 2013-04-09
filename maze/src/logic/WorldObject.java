package logic;

import java.io.Serializable;

import model.Position;

/**
 * The WorldObject Unit.
 */
public abstract class WorldObject implements Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Type. */
    public final WorldObjectType Type;

    /**
     * Instantiates a new world object.
     *
     * @param type the type
     */
    public WorldObject(WorldObjectType type)
    {
        Type = type;
    }

    /** The last id assigned. */
    private static int _lastId = 0;

    /** The current id. */
    private int _id = ++_lastId;

    /**
     * Gets the id.
     *
     * @return the int
     */
    public int GetId() { return _id; }

    /** The Constant DEFAULT_POSITION. */
    public final static Position DEFAULT_POSITION = new Position();

    /** The position. */
    protected Position _position = DEFAULT_POSITION;

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Position GetPosition() { return _position; }

    /**
     * Sets the position.
     *
     * @param pos the position
     */
    public void SetPosition(Position pos) { _position = pos; }

    /**
     * Checks if is inanimated object.
     *
     * @return true, if successful
     */
    public boolean IsInanimatedObject() { return Type == WorldObjectType.InanimatedObject; }

    /**
     * Checks if is unit.
     *
     * @return true, if successful
     */
    public boolean IsUnit() { return Type == WorldObjectType.Unit; }

    /**
     * Cast to unit.
     *
     * @return the unit
     */
    public Unit ToUnit() { return IsUnit() ? (Unit)this : null; }

    /**
     * Cast to inanimated object.
     *
     * @return the inanimated object
     */
    public InanimatedObject ToInanimatedObject() { return IsInanimatedObject() ? (InanimatedObject)this : null; }

    /**
     * Gets the symbol.
     *
     * @return the char
     */
    public abstract char GetSymbol();

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() // subclasses shall not override this
    {
        return String.valueOf(GetSymbol());
    }
}
