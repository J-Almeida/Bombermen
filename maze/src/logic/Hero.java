package logic;

public class Hero extends Unit
{

    public Hero()
    {
        super(UnitType.Hero);
    }

    public boolean IsArmed()
    {
        return _armed;
    }

    public void EquipSword()
    {
        _armed = true;
    }

    private boolean _armed;
}
