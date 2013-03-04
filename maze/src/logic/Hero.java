package logic;

/**
 * The Class Hero.
 */
public class Hero extends Unit
{
    /** Has sword? */
    private boolean _armed;

    /**
     * Instantiates a new hero.
     */
    public Hero()
    {
        super(UnitType.Hero);
    }

    /**
     * Checks if hero is armed.
     *
     * @return true, if hero has sword
     */
    public boolean IsArmed()
    {
        return _armed;
    }

    /**
     * Equip sword.
     */
    public void EquipSword()
    {
        _armed = true;
    }

    @Override
    public String toString()
    {
        return _armed ? "A" : "H";
    }
}
