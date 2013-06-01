package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.utils.Point;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;

public class World implements Disposable
{
    private IntMap<Entity> _entities = new IntMap<Entity>();

    public static Point GetTile(Vector2 position)
    {
        return new Point(MathUtils.floorPositive(position.x) / Constants.CELL_SIZE,
                         MathUtils.floorPositive(position.y) / Constants.CELL_SIZE);
    }

    public static Vector2 GetPositionAtCenter(Point point)
    {
        return new Vector2(point.GetX() * Constants.CELL_SIZE * 1.5f,
                           point.GetY() * Constants.CELL_SIZE * 1.5f);
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
