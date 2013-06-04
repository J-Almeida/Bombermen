package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bombermen implements ApplicationListener
{
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ControlPad _controlPad = null;

    //private float timeStep;
    private Input _input;

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //if (Gdx.app.getType() == ApplicationType.Android)
        //    timeStep = 1 / 45f;
        //else
        //    timeStep = 1 / 60f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT);
        batch = new SpriteBatch();

        _input = new Input(Game.Instance(), camera);

        if (Gdx.app.getType() == ApplicationType.Android || Constants.SHOW_PAD)
        {
            _controlPad = new ControlPad();
            _controlPad.SetSize(Constants.DEFAULT_PAD_WIDTH, Constants.DEFAULT_PAD_HEIGHT);
            _input.SetControlPad(_controlPad);
        }

        Gdx.input.setInputProcessor(_input);
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(16f / 255, 120f / 255, 48f / 255, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        // center 15 by 13 cells
        camera.combined.translate(Constants.DEFAULT_WIDTH / 2 - 15 * Constants.CELL_SIZE / 2, Constants.DEFAULT_HEIGHT / 2 - 13 * Constants.CELL_SIZE / 2 - 26, 0);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        int dt = (int) (Gdx.graphics.getRawDeltaTime() * 1000.f);

        Game.Instance().Update(dt);
        _input.Update(dt);

        batch.end();

        camera.combined.translate(-(Constants.DEFAULT_WIDTH / 2 - 15 * Constants.CELL_SIZE / 2), -(Constants.DEFAULT_HEIGHT / 2 - 13 * Constants.CELL_SIZE / 2 - 26), 0);
        batch.setProjectionMatrix(camera.combined);

        if (_controlPad != null)
        {
            batch.begin();
            _controlPad.draw(batch);
            batch.end();
        }
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
