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

/**
 * The Class SelectServerScreen.
 */
public class SelectServerScreen implements Screen
{

    /** The game. */
    private Bombermen _game;

    // Actors
    /** The background image. */
    private Image _backgroundImage;

    /** The ui group. */
    private Group _uiGroup;

    /**
     * Instantiates a new select server screen.
     *
     * @param game the game
     */
    public SelectServerScreen(Bombermen game)
    {
        _game = game;
    }

    /** The changed. */
    boolean changed = false;

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

        final List serverNamesList = new List(new String[] {}, skin);
        serverNamesList.setPosition(150, 350);
        serverNamesList.pack();
        _uiGroup.addActor(serverNamesList);

        final List ipsList = new List(new String[] {}, skin);
        ipsList.setPosition(330, 350 - ipsList.getHeight() / 2);
        ipsList.setSelection("");
        ipsList.setTouchable(Touchable.disabled);
        ipsList.pack();
        _uiGroup.addActor(ipsList);

        final List playersList = new List(new String[] {}, skin);
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
                if (!Character.isDigit(key) && key != '.' && key != ':') return false;

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

                final TextField playerNameField = new TextField("", skin);

                final Dialog d = new Dialog("Name", skin);
                d.add(playerNameField);
                d.add();
                d.button("Ok");
                d.show(_game.GetStage());
                d.addListener(new ChangeListener()
                {
                    @Override
                    public void changed(ChangeEvent event, Actor actor)
                    {
                        if (playerNameField.getText().length() == 0)
                        {
                            Dialog d2 = new Dialog("Error", skin);
                            d2.text("Name cannot be empty.");
                            d2.button("Ok").key(Keys.ENTER, null);
                            d2.show(_game.GetStage());
                            d.cancel();
                        }
                        else
                        {
                            int index = serverNamesList.getSelectedIndex();
                            String ip = ipsList.getItems()[index];
                            Screen s = _game.GetPlayScreen(ip, playerNameField.getText());
                            if (s != null)
                                _game.setScreen(s);
                            else
                            {
                                Dialog d3 = new Dialog("Error", skin);
                                d3.text("Could not connect to server " + serverNamesList.getSelection() + " (" + ip + ")");
                                d3.button("Ok").key(Keys.ENTER, null);
                                d3.show(_game.GetStage());
                            }
                        }
                    }
                });
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

    /**
     * Gets the server name and players.
     *
     * @param ip the ip
     * @param nameList the name list
     * @param playerList the player list
     */
    private void GetServerNameAndPlayers(String ip, final List nameList, final List playerList)
    {
        String name = "unknown";
        String players = "X";

        AddItem(nameList, name);
        AddItem(playerList, players);
    }

    /**
     * Adds the item.
     *
     * @param list the list
     * @param newItem the new item
     */
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
