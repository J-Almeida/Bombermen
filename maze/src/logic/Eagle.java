package logic;

import java.util.Stack;

import utils.Pair;

public class Eagle extends Unit
{
    public enum EagleState
    {
        OnFlight,
        OnFloor,
        FollowingHero,
        ReachedSword,
        OnFlightBack
    }
    
    private EagleState _state;
    private static final double _speed = 1;
    private boolean _armed = false;
    
    public Eagle()
    {
        super(UnitType.Eagle);
        _state = EagleState.FollowingHero;
    }
    
    public void SetState(EagleState state)
    {
        _state = state;
    }
    
    public EagleState GetState()
    {
        return _state;
    }
    
    public void EquipSword()
    {
        _armed = true;
    }
    
    public void UnequipSword()
    {
        _armed = false;
    }
    
    public boolean IsArmed()
    {
        return _armed;
    }
    
    private Pair<Integer> _initialPosition = null;
    private Pair<Integer> _swordPosition = null;
    private Stack<Pair<Integer>> _wayPath = new Stack<Pair<Integer>>();
    
    public void SetSwordPosition(Pair<Integer> pos)
    {
        _swordPosition = pos;
    }

    @Override
    public void Update()
    {
        if (_state == EagleState.OnFlight)
        {
            if (_initialPosition == null) // takeoff
            {
                _initialPosition = GetPosition();
                _wayPath.push(_initialPosition);
            }
            else
            {
                double deltaX = _swordPosition.first - GetPosition().first;
                double deltaY = _swordPosition.second - GetPosition().second;
                
                double dist = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
                if (dist > _speed)
                {
                    double ratio = _speed / dist;
                    double xMove = ratio * deltaX;
                    double yMove = ratio * deltaY;
                    double newXPos = xMove + GetPosition().first;
                    double newYPos = yMove + GetPosition().second;
                    SetPosition(Pair.IntN(Math.round((float)newXPos), Math.round((float)newYPos)));
                }
                else
                {
                    SetPosition(_swordPosition);
                }
                
                if (GetPosition().equals(_swordPosition))
                    _state = EagleState.ReachedSword;
                else
                    _wayPath.push(GetPosition());
            }
        }
        else if (_state == EagleState.OnFlightBack)
        {
            if (!_wayPath.isEmpty())
            {
                SetPosition(_wayPath.pop());
            }
            else
            {
                SetState(EagleState.OnFloor);
            }
        }
        
        while (!_eventQueue.isEmpty())
        {
            if (_eventQueue.peek().Type == EventType.Colision)
            {
                Collision ev = (Collision)_eventQueue.peek();
                if (ev.Other.Type == UnitType.Sword)
                {
                    this.EquipSword();
                    SetState(EagleState.OnFlightBack);
                }
                else if (ev.Other.Type == UnitType.Hero)
                {
                    this.UnequipSword();
                    SetState(EagleState.FollowingHero);
                }
            }
            _eventQueue.poll();
        }
    }

    @Override
    public String toString()
    {
        return "V";
    }
}
