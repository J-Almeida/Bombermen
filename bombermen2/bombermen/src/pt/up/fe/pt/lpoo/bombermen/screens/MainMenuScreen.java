package pt.up.fe.pt.lpoo.bombermen.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import pt.up.fe.pt.lpoo.bombermen.Assets;
import pt.up.fe.pt.lpoo.bombermen.Bombermen;
import pt.up.fe.pt.lpoo.bombermen.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
                event.cancel();
                if (((TextButton)actor).isPressed())
                    _game.setScreen(_game.GetSelectServerScreen());
            }
        });
        
        _game.GetStage().addAction(alpha(0));
        _game.GetStage().addAction(fadeIn(5, Interpolation.bounce));
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
