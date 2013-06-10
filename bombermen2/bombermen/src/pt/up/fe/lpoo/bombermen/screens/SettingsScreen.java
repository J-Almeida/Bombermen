package pt.up.fe.lpoo.bombermen.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import pt.up.fe.lpoo.bombermen.Assets;
import pt.up.fe.lpoo.bombermen.Bombermen;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * The Class SettingsScreen.
 */
public class SettingsScreen implements Screen
{

    /** The game. */
    private Bombermen _game;

    // Actors
    /** The background image. */
    private Image _backgroundImage;

    /** The ui group. */
    private Group _uiGroup;

    /**
     * Instantiates a new settings screen.
     *
     * @param game the game
     */
    public SettingsScreen(Bombermen game)
    {
        _game = game;
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        _game.GetStage().act(delta);
        _game.GetStage().draw();
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#resize(int, int)
     */
    @Override
    public void resize(int width, int height)
    {
        _game.GetStage().setViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, true);
        _game.GetStage().getCamera().translate(-_game.GetStage().getGutterWidth(), -_game.GetStage().getGutterHeight(), 0);
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#show()
     */
    @Override
    public void show()
    {
        _uiGroup = new Group();

        _backgroundImage = new Image(Assets.GetTexture("background"));
        _backgroundImage.setBounds(0, 0, _game.GetStage().getWidth(), _game.GetStage().getHeight());
        _backgroundImage.setTouchable(Touchable.disabled);
        _game.GetStage().addActor(_backgroundImage);

        Slider soundSlider = new Slider(0, 1, 0.05f, false, _game.GetSkin());
        soundSlider.setValue(Assets.GetSoundVolume());
        soundSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Assets.ChangeSoundVolume(((Slider) actor).getValue());
                Settings.SoundVolume = Assets.GetMusicVolume();
            }
        });

        Slider musicSlider = new Slider(0, 1, 0.05f, false, _game.GetSkin());
        musicSlider.setValue(Assets.GetMusicVolume());
        musicSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Assets.ChangeMusicVolume(((Slider) actor).getValue());
                Settings.MusicVolume = Assets.GetMusicVolume();
            }
        });

        CheckBox fullscreenCheckbox = new CheckBox(null, _game.GetSkin());
        fullscreenCheckbox.setChecked(Settings.Fullscreen);
        fullscreenCheckbox.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Settings.Fullscreen = ((CheckBox) actor).isChecked();
                Gdx.graphics.setDisplayMode(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, Settings.Fullscreen);
            }
        });

        SelectBox keysBox = new SelectBox(new String[] { "W/A/S/D", "I/J/K/L", "arrow keys" }, _game.GetSkin());
        keysBox.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Settings.Keys = ((SelectBox) actor).getSelectionIndex();
            }
        });

        Button b = new Button(_game.GetSkin());
        b.add("Back");
        b.pack();
        b.setPosition(750, 10);
        b.addListener(new ChangeListener()
        {

            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Assets.PlaySound("menu_back");

                Settings.Save();
                _game.setScreen(_game.GetMainMenu());
            }
        });
        _uiGroup.addActor(b);

        Table soundTable = new Table(_game.GetSkin());

        soundTable.add("Sound Volume:").right().padRight(10);
        soundTable.add(soundSlider);
        soundTable.row();
        soundTable.add("Music Volume:").right().padRight(10);
        soundTable.add(musicSlider);
        soundTable.row();
        soundTable.add("Fullscreen:").right().padRight(10);
        soundTable.add(fullscreenCheckbox).left();
        soundTable.row();
        soundTable.add("Keys:").right().padRight(10);
        soundTable.add(keysBox).left();
        soundTable.setPosition(150, 350);
        _uiGroup.addActor(soundTable);

        _uiGroup.addAction(sequence(alpha(0), fadeIn(1)));
        _game.GetStage().addActor(_uiGroup);
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#hide()
     */
    @Override
    public void hide()
    {
        _uiGroup.addAction(sequence(fadeOut(1), removeActor()));
        _backgroundImage.remove();
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#pause()
     */
    @Override
    public void pause()
    {
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#resume()
     */
    @Override
    public void resume()
    {
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#dispose()
     */
    @Override
    public void dispose()
    {
    }

}
