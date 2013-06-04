package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Bombermen2 implements ApplicationListener
{
    private Stage _stage;
    private Game _game;

    @Override
    public void create()
    {
        _stage = new Stage();
        Gdx.input.setInputProcessor(_stage);

        _game = new Game(_stage);

    }

    @Override
    public void resize(int width, int height)
    {
        _stage.setViewport(width, height, true);
        _stage.getCamera().translate(-200, 0, 0);
    }

    @Override
    public void render()
    {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        _game.Update();

        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
        _stage.dispose();
    }
}
