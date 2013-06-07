package pt.up.fe.lpoo.bombermen.screens;

import pt.up.fe.lpoo.bombermen.Bombermen;
import pt.up.fe.lpoo.bombermen.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class SettingsScreen implements Screen
{
    private Bombermen _game;

    public SettingsScreen(Bombermen game)
    {
        _game = game;
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        _game.GetStage().act(delta);
        _game.GetStage().draw();
    }

    @Override
    public void resize(int width, int height)
    {
        _game.GetStage().setViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, true);
        _game.GetStage().getCamera().translate(-_game.GetStage().getGutterWidth(), -_game.GetStage().getGutterHeight(), 0);
    }

    @Override
    public void show()
    {
    }

    @Override
    public void hide()
    {
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
    }

}
