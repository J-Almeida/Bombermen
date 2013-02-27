package logic;

// TODO: Auto-generated Javadoc
/**
 * The Class Hero.
 */
public class Hero extends Unit
{

    /**
     * Instantiates a new hero.
     */
    public Hero()
    {
        super(UnitType.Hero);
    }

    /**
     * Checks if is armed.
     *
     * @return true, if successful
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

    /** The _armed. */
    private boolean _armed;
}
