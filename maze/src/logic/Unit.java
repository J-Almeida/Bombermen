package logic;


/**
 * Base class for all living objects
 */
public abstract class Unit extends WorldObject
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The unit type. */
    public final UnitType Type;

    /** Is alive? */
    protected boolean _alive;

    /** The direction between last position and unit position. */
    protected Direction _direction = Direction.South;

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public Direction GetDirection()
    {
        return _direction;
    }

    /**
     * Instantiates a new unit.
     *
     * @param type the type
     */
    public Unit(UnitType type)
    {
        super(WorldObjectType.Unit);
        Type = type;
        _alive = true;
    }

    /**
     * Checks if is alive.
     *
     * @return true, if successful
     */
    public boolean IsAlive()
    {
        return _alive;
    }

    /**
     * Kill.
     */
    public void Kill()
    {
        _alive = false;
    }

    /**
     * Method called each iteration.
     *
     * @param maze the maze
     */
    public void Update(Maze maze) { };

    /**
     * Method called when an unit in maze moves
     *
     * @param other the unit that moved
     * @param dir the direction it moved
     */
    public void OnMovement(Unit other, Direction dir) { }

    /**
     * Checks if is hero.
     *
     * @return true, if successful
     */
    public boolean IsHero() { return Type == UnitType.Hero; }

    /**
     * Checks if is dragon.
     *
     * @return true, if successful
     */
    public boolean IsDragon() { return Type == UnitType.Dragon; }

    /**
     * Checks if is sword.
     *
     * @return true, if successful
     */
    public boolean IsSword() { return Type == UnitType.Sword; }

    /**
     * Checks if is eagle.
     *
     * @return true, if successful
     */
    public boolean IsEagle() { return Type == UnitType.Eagle; }

    /**
     * Checks if is same type.
     *
     * @param other the other
     * @return true, if successful
     */
    public boolean IsSameType(Unit other) { return Type == other.Type; }

    /**
     * Checks if is unit type.
     *
     * @param type the type
     * @return true, if successful
     */
    public boolean Is(UnitType type) { return Type == type; }

    /**
     * Cast to hero.
     *
     * @return the hero
     */
    public Hero ToHero() { return IsHero() ? (Hero)this : null; }

    /**
     * Cast to dragon.
     *
     * @return the dragon
     */
    public Dragon ToDragon() { return IsDragon() ? (Dragon)this : null; }

    /**
     * Cast to sword.
     *
     * @return the sword
     */
    public Sword ToSword() { return IsSword() ? (Sword)this : null; }

    /**
     * Cast to eagle.
     *
     * @return the eagle
     */
    public Eagle ToEagle() { return IsEagle() ? (Eagle)this : null; }

    /**
     * Handle event.
     *
     * @param maze the maze
     * @param ev the event to handle
     */
    public abstract void HandleEvent(Maze maze, Event ev);
}
