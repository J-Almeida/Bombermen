package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public class World implements Disposable
{
    private Stage _stage;
    //private Game _game;

    public World(Stage stage, Game game)
    {
        _stage = stage;
        //_game = game;
    }

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
        return (tileX * Constants.CELL_SIZE) + Constants.CELL_SIZE / 2;
    }

    public static float GetPositionAtCenterY(int tileY)
    {
        return (tileY * Constants.CELL_SIZE) + Constants.CELL_SIZE / 2;
    }

    @Override
    public void dispose()
    {
    }

    public void AddEntity(final Entity entity)
    {
        _stage.addActor(entity);
    }

    public Entity GetEntity(int guid)
    {
        for (Actor a : _stage.getActors())
        {
            Entity e = (Entity)a;
            if (e.GetGuid() == guid)
                return e;
        }

        return null;
    }

    public void RemoveEntity(int guid)
    {
        Entity e = GetEntity(guid);
        if (e != null)
            RemoveEntity(e);
    }

    public void RemoveEntity(Entity entity)
    {
        entity.remove();
        //entity.OnDestroy();
        //_entities.remove(entity.GetGuid());
    }
}
