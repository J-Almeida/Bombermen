package pt.up.fe.lpoo.bombermen.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.io.IOException;

import pt.up.fe.lpoo.bombermen.Assets;
import pt.up.fe.lpoo.bombermen.Bombermen;
import pt.up.fe.lpoo.bombermen.Constants;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuScreen implements Screen
{
    private Bombermen _game;

    // Actors
    private Image _backgroundImage;
    private Table _buttonsTable;
    private TextButton _playButton;
    private TextButton _createServerButton;
    private TextButton _settingsButton;
    private TextButton _exitButton;
    private Label _feupLabel;

    public MainMenuScreen(Bombermen game)
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
        _backgroundImage = new Image(Assets.GetTexture("background"));
        _backgroundImage.setBounds(0, 0, _game.GetStage().getWidth(), _game.GetStage().getHeight());
        _backgroundImage.setTouchable(Touchable.disabled);
        _game.GetStage().addActor(_backgroundImage);

        _playButton = new TextButton("Play", _game.GetSkin());
        _createServerButton = new TextButton("Create Server", _game.GetSkin());
        _settingsButton = new TextButton("Settings", _game.GetSkin());
        _exitButton = new TextButton("Exit", _game.GetSkin());

        _buttonsTable = new Table(_game.GetSkin());

        _buttonsTable.add(_playButton).left();
        _buttonsTable.row();
        _buttonsTable.add().height(10);
        _buttonsTable.row();
        _buttonsTable.add(_createServerButton).left();
        _buttonsTable.row();
        _buttonsTable.add().height(10);
        _buttonsTable.row();
        _buttonsTable.add(_settingsButton).left();
        _buttonsTable.row();
        _buttonsTable.add().height(10);
        _buttonsTable.row();
        _buttonsTable.add(_exitButton).left();

        _buttonsTable.setPosition(80, 100);
        _game.GetStage().addActor(_buttonsTable);

        _feupLabel = new Label("FEUP 2013/2013 - MIEIC", _game.GetSkin());
        _feupLabel.setPosition(620, 5);
        _game.GetStage().addActor(_feupLabel);

        _playButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                if (((TextButton)actor).isPressed())
                    _game.setScreen(_game.GetSelectServerScreen());
            }
        });

        _createServerButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                try
                {
                    Runtime.getRuntime().exec("jva -classpath ..\\bombermen-server\\bin;..\\bombermen\\bin;..\\bombermen-server\\forms-1.3.0.jar pt.up.fe.lpoo.bombermen.gui.BombermenServerGui");
                }
                catch (IOException e)
                {
                    Dialog dialog = new Dialog("Error", _game.GetSkin());
                    dialog.text("Could not launch server process.\nDetails printed to console.");
                    dialog.button("Okay");
                    dialog.pack();
                    dialog.setPosition(800/2 - dialog.getWidth() / 2, 480/2 - dialog.getHeight() / 2);
                    _game.GetStage().addActor(dialog);

                    e.printStackTrace();
                }
            }
        });

        _settingsButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                if (((TextButton)actor).isPressed())
                    _game.setScreen(_game.GetSettingsScreen());
            }
        });

        _exitButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Gdx.app.exit();
            }
        });

        if (Gdx.app.getType() != ApplicationType.Desktop)
            _createServerButton.setDisabled(true);

        Assets.PlayMusic("bgm_03", true);
        _game.GetStage().addAction(sequence(alpha(0), fadeIn(3)));
    }

    @Override
    public void hide()
    {
        _backgroundImage.remove();
        _buttonsTable.remove();
        _feupLabel.remove();
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
