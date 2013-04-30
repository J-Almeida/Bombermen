package logic;

import java.util.Map;

public abstract class GameState implements IState
{
    Map<Integer, WorldObject> _entities;
    
    @Override
    public void Initialize()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void LoadContents()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void Update(int diff)
    {
        
    }

    @Override
    public void UnloadContents()
    {
        // TODO Auto-generated method stub

    }
}
