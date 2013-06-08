package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.entities.Player;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Bombermen2 implements ApplicationListener
{

    private Stage _stage;
    public static Game _game;
    private Input _input;
    private ControlPad _controlPad;
    private FPSLogger _fpsLogger;
    private Skin _skin;

    @Override
    public void create()
    {
        _stage = new Stage();
        _game = new Game(_stage);
        _fpsLogger = new FPSLogger();

        _skin = new Skin(Gdx.files.internal("data/skins/uiskin.json"));
        _skin.getAtlas().getTextures().iterator().next().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        Slider slider = new Slider(0, 1, 0.05f, false, _skin);
        slider.setBounds(-180, 400, 100, 100);
        slider.setValue(Assets.GetSoundVolume());
        slider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Assets.ChangeSoundVolume(((Slider)actor).getValue());
                Assets.ChangeMusicVolume(((Slider)actor).getValue());
            }
        });

        _stage.addActor(slider);

        _input = new Input(_game, _stage.getCamera());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(_stage);
        multiplexer.addProcessor(_input);
        Gdx.input.setInputProcessor(multiplexer);

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

        manager.finishLoading(); // loads all assets (proper way should be to call manager.update() in render method)

        if (Gdx.app.getType() == ApplicationType.Android || Constants.SHOW_PAD)
        {
            _controlPad = new ControlPad();
            _controlPad.SetSize(Constants.DEFAULT_PAD_WIDTH, Constants.DEFAULT_PAD_HEIGHT);
            _input.SetControlPad(_controlPad);
        }

        Assets.PlayMusic("bgm_02", true); // todo: move me somewhere else
    }



    @Override
    public void resize(int width, int height)
    {
        _stage.setViewport(width, height, true);
        _stage.getCamera().translate(- Constants.WINDOW_XMARGIN, 0, 0);
    }

    private static float offsetMinX = 0;
    private static float offsetMinY = 0;

    private float _oldCamX = 0;
    private float _oldCamY = 0;

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(16f / 255, 120f / 255, 48f / 255, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        //int dt = (int) (Gdx.graphics.getRawDeltaTime() * 1000.f);
        _game.Update(/*dt*/);

        _stage.getCamera().update();

        _stage.getCamera().translate(-_oldCamX, -_oldCamY, 0f);

        Player p =_game.GetCurrentPlayer();
        if (p != null)
        {
            float camX = p.getX() - Constants.DEFAULT_WIDTH / 2f;
            float camY = p.getY() - Constants.DEFAULT_HEIGHT / 2f;
            
            float offsetMaxX = _game.GetMapWidth() * Constants.CELL_SIZE - Constants.DEFAULT_WIDTH + 2*Constants.WINDOW_XMARGIN;;
            float offsetMaxY = _game.GetMapHeight() * Constants.CELL_SIZE - Constants.DEFAULT_HEIGHT;
            
            if (camX > offsetMaxX)
                camX = offsetMaxX;
            else if (camX < offsetMinX)
                camX = offsetMinX;

            if (camY > offsetMaxY)
                camY = offsetMaxY;
            else if (camY < offsetMinY)
                camY = offsetMinY;

            _oldCamX = camX;
            _oldCamY = camY;

            _stage.getCamera().translate(camX, camY, 0f);
        }
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
        Assets.Manager.dispose();
        _skin.dispose();
    }
}
