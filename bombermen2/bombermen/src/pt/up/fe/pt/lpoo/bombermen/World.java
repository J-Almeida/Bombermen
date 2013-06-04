package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public class World implements Disposable
{
    private Stage _stage;
    private Game _game;

    public World(Stage stage, Game game)
    {
        _stage = stage;
        _game = game;
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

        if (entity.IsPlayer())
        {
            _stage.addListener(new InputListener()
            {
                @Override
                public boolean keyDown(InputEvent event, int keycode)
                {
                    switch (keycode)
                    {
                        case Keys.S:
                            _game.MovePlayerDown();
                            Gdx.app.debug("Input", "Player move down.");
                            return true;
                        case Keys.W:
                            Gdx.app.debug("Input", "Player move up.");
                            _game.MovePlayerUp();
                            return true;
                        case Keys.A:
                            Gdx.app.debug("Input", "Player move left.");
                            _game.MovePlayerLeft();
                            return true;
                        case Keys.D:
                            Gdx.app.debug("Input", "Player move right.");
                            _game.MovePlayerRight();
                            return true;
                        case Keys.SPACE:
                            Gdx.app.debug("Input", "Player place bomb.");
                            _game.PlaceBomb();
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
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
