package pt.up.fe.lpoo.bombermen.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.regex.Pattern;

import pt.up.fe.lpoo.bombermen.Assets;
import pt.up.fe.lpoo.bombermen.Bombermen;
import pt.up.fe.lpoo.bombermen.Constants;
import pt.up.fe.lpoo.bombermen.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SelectServerScreen implements Screen
{
    private Bombermen _game;

    // Actors
    private Image _backgroundImage;
    private Group _uiGroup;

    public SelectServerScreen(Bombermen game)
    {
        _game = game;
    }

    boolean changed = false;

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
        Settings.Load(); // saved server ipaddresses
        _uiGroup = new Group();
        final Skin skin = _game.GetSkin();

        _backgroundImage = new Image(Assets.GetTexture("background"));
        _backgroundImage.setBounds(0, 0, _game.GetStage().getWidth(), _game.GetStage().getHeight());
        _backgroundImage.setTouchable(Touchable.disabled);
        _game.GetStage().addActor(_backgroundImage);

        Button b = new Button(skin);
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


        final List serverNamesList = new List(new String[] { }, skin);
        serverNamesList.setPosition(150, 350);
        serverNamesList.pack();
        _uiGroup.addActor(serverNamesList);

        final List ipsList = new List(new String[] { }, skin);
        ipsList.setPosition(330, 350 - ipsList.getHeight() / 2);
        ipsList.setSelection("");
        ipsList.setTouchable(Touchable.disabled);
        ipsList.pack();
        _uiGroup.addActor(ipsList);

        final List playersList = new List(new String[] { }, skin);
        playersList.setPosition(550, 350);
        playersList.setSelection("");
        playersList.setTouchable(Touchable.disabled);
        playersList.pack();
        _uiGroup.addActor(playersList);

        Label serverLabel = new Label("Server", skin);
        serverLabel.setPosition(150, 360);
        _uiGroup.addActor(serverLabel);

        Label ipLabel = new Label("IP Address", skin);
        ipLabel.setPosition(330, 360);
        _uiGroup.addActor(ipLabel);

        Label playersLabel = new Label("Players", skin);
        playersLabel.setPosition(550, 360);
        _uiGroup.addActor(playersLabel);

        Label addIpLabel = new Label("Add IP:", skin);
        addIpLabel.setPosition(200, 100);
        _uiGroup.addActor(addIpLabel);

        final TextField enterIpField = new TextField("", skin);
        final Button enterIpButton = new Button(skin);

        enterIpField.setPosition(260, 98);
        enterIpField.setWidth(180);
        enterIpField.setTextFieldFilter(new TextFieldFilter()
        {
            @Override
            public boolean acceptChar(TextField textField, char key)
            {
                if (!Character.isDigit(key) && key != '.' && key != ':')
                    return false;

                return true;
            }
        });
        _uiGroup.addActor(enterIpField);

        enterIpButton.add("Ok");
        enterIpButton.setPosition(260 + 180 + 5, 100);
        enterIpButton.pack();

        enterIpField.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                if (keycode == Keys.ENTER)
                {
                    enterIpButton.toggle();
                    return true;
                }

                return false;
            }
        });

        enterIpButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                String ip = enterIpField.getText();

                if (!Pattern.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,5}", ip))
                {
                    Dialog d = new Dialog("Error", skin);
                    d.text("Invalid IP Address specified.\nValid IP example: 127.0.0.1:7777");
                    d.button("Ok").key(Keys.ENTER, null);
                    d.show(_game.GetStage());
                }
                else
                {
                    AddItem(ipsList, ip);
                    GetServerNameAndPlayers(ip, serverNamesList, playersList);
                    ipsList.setSelection(null);
                    playersList.setSelection(null);
                    Settings.SaveServers(ipsList.getItems());
                }
            }
        });

        _uiGroup.addActor(enterIpButton);

        _uiGroup.addAction(sequence(alpha(0), fadeIn(1)));
        _game.GetStage().addActor(_uiGroup);

        Button playButton = new Button(skin);
        playButton.add("Play!");
        playButton.pack();
        playButton.setPosition(500, 100);
        _uiGroup.addActor(playButton);

        playButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Assets.PlaySound("menu_select");

                int index = serverNamesList.getSelectedIndex();
                String ip = ipsList.getItems()[index];
                Screen s = _game.GetPlayScreen(ip);
                if (s != null)
                    _game.setScreen(s);
                else
                {
                    Dialog d = new Dialog("Error", skin);
                    d.text("Could not connect to server " + serverNamesList.getSelection() + " (" + ip + ")");
                    d.button("Ok").key(Keys.ENTER, null);
                    d.show(_game.GetStage());
                }
            }
        });

        for (String s : Settings.SavedServers.values())
        {
            AddItem(ipsList, s);
            GetServerNameAndPlayers(s, serverNamesList, playersList);
            ipsList.setSelection(null);
            playersList.setSelection(null);
        }
    }

    @Override
    public void hide()
    {
        _uiGroup.addAction(sequence(fadeOut(1), removeActor()));
        _backgroundImage.remove();
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

    private void GetServerNameAndPlayers(String ip, final List nameList, final List playerList)
    {
        String name = "unknown";
        String players = "X";

        AddItem(nameList, name);
        AddItem(playerList, players);
    }

    private void AddItem(final List list, String newItem)
    {
        String[] currItems = list.getItems();

        String[] newItems = new String[Math.min(7, currItems.length + 1)];
        for (int i = 0; i < newItems.length - 1; ++i)
            newItems[i + 1] = currItems[i];
        newItems[0] = newItem;
        list.setItems(newItems);
        list.setPosition(list.getX(), 350 - list.getPrefHeight());
        list.pack();
    }
}
