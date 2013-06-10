package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.screens.MainMenuScreen;
import pt.up.fe.lpoo.bombermen.screens.PlayScreen;
import pt.up.fe.lpoo.bombermen.screens.SelectServerScreen;
import pt.up.fe.lpoo.bombermen.screens.SettingsScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Bombermen extends Game
{
    private Stage _stage;

    private MainMenuScreen _mainMenuScreen;
    private SelectServerScreen _selectServerScreen;
    private SettingsScreen _settingsScreen;
    private PlayScreen _playScreen;

    @Override
    public void create()
    {
        Settings.Load();
        Gdx.graphics.setDisplayMode(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, Settings.Fullscreen);

        _stage = new Stage(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, true);
        Gdx.input.setInputProcessor(_stage);

        AssetManager manager = new AssetManager();
        Assets.Manager = manager;
        // Texture.setAssetManager(manager);

        manager.load("data/skins/uiskin.json", Skin.class);

        manager.load("data/images/bomb.png", Texture.class);
        manager.load("data/images/bombButton.png", Texture.class);
        manager.load("data/images/bomberman.png", Texture.class);
        manager.load("data/images/dpad.png", Texture.class);
        manager.load("data/images/explosion.png", Texture.class);
        manager.load("data/images/powerup.png", Texture.class);
        manager.load("data/images/wall.png", Texture.class);
        manager.load("data/images/background.png", Texture.class);

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

        _mainMenuScreen = new MainMenuScreen(this);
        _selectServerScreen = new SelectServerScreen(this);
        _settingsScreen = new SettingsScreen(this);
        _playScreen = new PlayScreen(this);
        setScreen(_mainMenuScreen);
    }

    public Stage GetStage()
    {
        return _stage;
    }

    public Skin GetSkin()
    {
        return Assets.GetSkin("uiskin");
    }

    public MainMenuScreen GetMainMenu()
    {
        return _mainMenuScreen;
    }

    @Override
    public void dispose()
    {
        super.dispose();
        _stage.dispose();

        Settings.Save();
    }

    public SelectServerScreen GetSelectServerScreen()
    {
        return _selectServerScreen;
    }

    public SettingsScreen GetSettingsScreen()
    {
        return _settingsScreen;
    }

    public PlayScreen GetPlayScreen(String ip, String playerName)
    {
        int indexOfPortSeparator = ip.indexOf(':');

        String ipStr = ip.substring(0, indexOfPortSeparator);
        int port = Integer.parseInt(ip.substring(indexOfPortSeparator + 1));

        try
        {
            _playScreen.SetServerIPAddress(ipStr);
            _playScreen.SetServerPort(port);
            _playScreen.SetPlayerName(playerName);
            _playScreen.Connect();
            return _playScreen;
        }
        catch (GdxRuntimeException e)
        {
            return null;
        }
    }
}
