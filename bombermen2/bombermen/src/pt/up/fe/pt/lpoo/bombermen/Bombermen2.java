package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Bombermen2 implements ApplicationListener
{
    private Stage _stage;
    private Game _game;
    private Input _input;
    private ControlPad _controlPad;
    private FPSLogger _fpsLogger;

    @Override
    public void create()
    {
        _stage = new Stage();
        _game = new Game(_stage);
        _fpsLogger = new FPSLogger();

        _input = new Input(_game, _stage.getCamera());
        //Gdx.input.setInputProcessor(_stage);
        Gdx.input.setInputProcessor(_input);

        if (Gdx.app.getType() == ApplicationType.Android || Constants.SHOW_PAD)
        {
            _controlPad = new ControlPad();
            _controlPad.SetSize(Constants.DEFAULT_PAD_WIDTH, Constants.DEFAULT_PAD_HEIGHT);
            _input.SetControlPad(_controlPad);
        }

        AssetManager manager = new AssetManager();
        Assets.Manager = manager;
        // Texture.setAssetManager(manager);

        manager.load("data/images/bomb.png", Texture.class);
        manager.load("data/images/bombButton.png", Texture.class);
        manager.load("data/images/bomberman.png", Texture.class);
        manager.load("data/images/dpad.png", Texture.class);
        manager.load("data/images/explosion.png", Texture.class);
        manager.load("data/images/powerup.png", Texture.class);
        manager.load("data/images/wall.png", Texture.class);

        manager.load("data/musics/bgm_01.mp3", Music.class);
        manager.load("data/musics/bgm_02.mp3", Music.class);
        manager.load("data/musics/bgm_03.mp3", Music.class);
        manager.load("data/musics/bgm_menu.mp3", Music.class);
        manager.load("data/musics/bgm_startGame.mp3", Music.class);

        manager.load("data/sounds/bomb_explosion.wav", Sound.class);
        manager.load("data/sounds/bomb_place.wav", Sound.class);
        manager.load("data/sounds/dying.wav", Sound.class);
        manager.load("data/sounds/menu_back.wav", Sound.class);
        manager.load("data/sounds/menu_select.wav", Sound.class);
        manager.load("data/sounds/powerup.wav", Sound.class);
        manager.load("data/sounds/victory.wav", Sound.class);

        manager.finishLoading(); // loads all assets (proper way should be to call manager.update() in render method
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
        Gdx.gl.glClearColor(16f / 255, 120f / 255, 48f / 255, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        //int dt = (int) (Gdx.graphics.getRawDeltaTime() * 1000.f);
        _game.Update(/*dt*/);

        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();

        if (_controlPad != null)
        {
            _stage.getSpriteBatch().setProjectionMatrix(_stage.getCamera().combined);
            _stage.getSpriteBatch().begin();
            _controlPad.draw(_stage.getSpriteBatch());
            _stage.getSpriteBatch().end();
        }

        _fpsLogger.log();
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
