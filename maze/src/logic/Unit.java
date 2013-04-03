package logic;


// TODO: Auto-generated Javadoc
/**
 * The Class Unit.
 */
public abstract class Unit extends WorldObject
{
    public final UnitType Type;

    /** The _alive. */
    protected boolean _alive;

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

    /** Method called each iteration
     * @param maze */
    public abstract void Update(Maze maze);

    public void OnCollision(Unit other) { };
    public void OnMovement(Unit other, Direction dir) { }

    public boolean IsHero() { return Type == UnitType.Hero; }
    public boolean IsDragon() { return Type == UnitType.Dragon; }
    public boolean IsSword() { return Type == UnitType.Sword; }
    public boolean IsEagle() { return Type == UnitType.Eagle; }
    public boolean IsSameType(Unit other) { return Type == other.Type; }
    public boolean Is(UnitType typ) { return Type == typ; }

    public Hero ToHero() { return IsHero() ? (Hero)this : null; }
    public Dragon ToDragon() { return IsDragon() ? (Dragon)this : null; }
    public Sword ToSword() { return IsSword() ? (Sword)this : null; }
    public Eagle ToEagle() { return IsEagle() ? (Eagle)this : null; }

    public abstract void HandleEvent(Maze maze, Event ev);
}
