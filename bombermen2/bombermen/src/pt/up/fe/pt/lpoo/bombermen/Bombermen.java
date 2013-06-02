package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bombermen implements ApplicationListener
{
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ControlPad _controlPad = null;
    private BitmapFont font;

    private float timeStep;

    private Game _game;

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        if (Gdx.app.getType() == ApplicationType.Android)
            timeStep = 1 / 45f;
        else
            timeStep = 1 / 60f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT);
        batch = new SpriteBatch();
        font = new BitmapFont();

        Input in = new Input(Game.Instance(), camera);

        if (Gdx.app.getType() == ApplicationType.Android)
        {
            _controlPad = new ControlPad();
            _controlPad.SetSize(Constants.DEFAULT_PAD_WIDTH, Constants.DEFAULT_PAD_HEIGHT);
            in.SetControlPad(_controlPad);
        }

        Gdx.input.setInputProcessor(in);
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (_controlPad != null) _controlPad.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height)
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
}
