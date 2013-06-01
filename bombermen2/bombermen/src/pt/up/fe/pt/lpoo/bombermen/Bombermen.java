package pt.up.fe.pt.lpoo.bombermen;

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

    private float timeStep;
    
    private Game _game;

    @Override
    public void create()
    {
        if (Gdx.app.getType() == ApplicationType.Android)
            timeStep = 1 / 45f;
        else
            timeStep = 1 / 60f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        
        Gdx.input.setInputProcessor(new Input(Game.Instance(), camera));
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        // batch.setProjectionMatrix(camera.combined);
        // batch.begin();
        // ...
        // batch.end();
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
