package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;

public class World implements Disposable
{
    private IntMap<Entity> _entities = new IntMap<Entity>();

    public static int GetTileX(float x)
    {
        return MathUtils.floorPositive(x) / Constants.CELL_SIZE;
    }

    public static int GetTileY(float y)
    {
        return MathUtils.floorPositive(y) / Constants.CELL_SIZE;
    }

    public static float GetPositionAtCenterX(int tileX)
    {
        return tileX * Constants.CELL_SIZE * 1.5f;
    }

    public static float GetPositionAtCenterY(int tileY)
    {
        return tileY * Constants.CELL_SIZE * 1.5f;
    }

    @Override
    public void dispose()
    {
        _entities.clear();
    }

    public void AddEntity(Entity entity)
    {
        _entities.put(entity.GetGuid(), entity);
    }

    public Entity GetEntity(int guid)
    {
        return _entities.get(guid);
    }

    public void RemoveEntity(Entity entity)
    {
        entity.OnDestroy();
        _entities.remove(entity.GetGuid());
    }
}
