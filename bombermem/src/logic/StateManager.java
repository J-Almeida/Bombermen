package logic;

import java.util.HashMap;
import java.util.Map;

public class StateManager
{
    private Map<String, IState> _states;
    private IState _currentState;
    
    public StateManager()
    {
        _states = new HashMap<String, IState>();
        _currentState = null;
    }
    
    public void Update(int diff)
    {
        _currentState.Update(diff);
    }
    
    public void Draw()
    {
        _currentState.Draw();
    }
    
    public void ChangeState(String newState)
    {
        if (_currentState != null)
            _currentState.UnloadContents();
        _currentState = _states.get(newState);
        _currentState.Initialize();
        _currentState.LoadContents();
    }
    
    public void AddState(String name, IState state)
    {
        _states.put(name, state);
    }
}
