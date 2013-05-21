package logic;

import java.util.HashMap;
import java.util.Map;

public class StateManager
{
    private Map<String, IState> _states;
    private String _currentState;

    public StateManager()
    {
        _states = new HashMap<String, IState>();
        _currentState = null;
    }

    public void Update(int diff)
    {
        _states.get(_currentState).Update(diff);
    }

    public void ChangeState(String newState)
    {
        if (_currentState != null)
            _states.get(_currentState).UnloadContents();
        _currentState = newState;
        _states.get(_currentState).Initialize();
        _states.get(_currentState).LoadContents();
    }

    public void AddState(String name, IState state)
    {
        _states.put(name, state);
    }

    public String GetCurrentStateStr()
    {
        return _currentState;
    }

    public IState GetCurrentState()
    {
        return GetState(_currentState);
    }

    public IState GetState(String string)
    {
        return _states.get(string);
    }
}
