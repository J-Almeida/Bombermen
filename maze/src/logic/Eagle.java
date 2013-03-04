package logic;

public class Eagle extends Unit
{
    public enum EagleState
    {
        OnFlight,
        OnFloor,
        FollowingHero
    }
    
    private EagleState _state;
    
    public Eagle(UnitType type)
    {
        super(UnitType.Eagle);
        _state = EagleState.FollowingHero;
    }

    @Override
    public String toString()
    {
        return "V";
    }
}
