package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class Input implements InputProcessor
{
    public static final int A_UP = 0;
    public static final int A_DOWN = 1;
    public static final int A_LEFT = 2;
    public static final int A_RIGHT = 3;
    public static final int A_BOMB = 4;
    public static final int A_NUM_OF_ACTIONS = 5;

    private boolean[] _activeActions = { false, false, false, false };

    public interface Commands
    {
        void ExecuteAction(int action, boolean val);
    }

    private Commands _commands;
    private Camera _camera;
    private ControlPad _controlPad = null;
    private Vector3 _tempVector = new Vector3();

    public void SetControlPad(ControlPad controlPad)
    {
        _controlPad = controlPad;
    }

    public Input(Commands commands, Camera camera)
    {
        _commands = commands;
        _camera = camera;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Keys.S:
                _commands.ExecuteAction(A_DOWN, true);
                System.out.println("Player move down.");
                Gdx.app.debug("Input", "Player move down.");
                return true;
            case Keys.W:
                Gdx.app.debug("Input", "Player move up.");
                System.out.println("Player move up.");
                _commands.ExecuteAction(A_UP, true);
                return true;
            case Keys.A:
                Gdx.app.debug("Input", "Player move left.");
                _commands.ExecuteAction(A_LEFT, true);
                return true;
            case Keys.D:
                Gdx.app.debug("Input", "Player move right.");
                _commands.ExecuteAction(A_RIGHT, true);
                return true;
            case Keys.SPACE:
                Gdx.app.debug("Input", "Player place bomb.");
                _commands.ExecuteAction(A_BOMB, true);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case Keys.S:
                _commands.ExecuteAction(A_DOWN, false);
                return true;
            case Keys.W:
                Gdx.app.debug("Input", "Player move up.");
                _commands.ExecuteAction(A_UP, false);
                return true;
            case Keys.A:
                Gdx.app.debug("Input", "Player move left.");
                _commands.ExecuteAction(A_LEFT, false);
                return true;
            case Keys.D:
                Gdx.app.debug("Input", "Player move right.");
                _commands.ExecuteAction(A_RIGHT, false);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        _tempVector.set(screenX, screenY, 0);
        _camera.unproject(_tempVector);

        if (_controlPad != null)
        {
            if (button == Buttons.LEFT)
            {
                int dir = _controlPad.Click(_tempVector);
                Gdx.app.debug("Input", "Pad direction " + dir);

                switch (dir)
                {
                    case Direction.NORTH:
                        _activeActions[A_UP] = true;
                        _commands.ExecuteAction(A_UP, true);
                        return true;
                    case Direction.SOUTH:
                        _activeActions[A_DOWN] = true;
                        _commands.ExecuteAction(A_DOWN, true);
                        return true;
                    case Direction.WEST:
                        _activeActions[A_LEFT] = true;
                        _commands.ExecuteAction(A_LEFT, true);
                        return true;
                    case Direction.EAST:
                        _activeActions[A_RIGHT] = true;
                        _commands.ExecuteAction(A_RIGHT, true);
                        return true;
                    case ControlPad.PLACE_BOMB:
                        _commands.ExecuteAction(A_BOMB, true);
                        return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        _tempVector.set(screenX, screenY, 0);
        _camera.unproject(_tempVector);

        if (_controlPad != null)
        {
            /*if (button == Buttons.LEFT)
            {
                int dir = _controlPad.Click(_tempVector);

                switch (dir)
                {
                    case Direction.NORTH:
                        _commands.ExecuteAction(A_UP, false);
                        return true;
                    case Direction.SOUTH:
                        _commands.ExecuteAction(A_DOWN, false);
                        return true;
                    case Direction.WEST:
                        _commands.ExecuteAction(A_LEFT, false);
                        return true;
                    case Direction.EAST:
                        _commands.ExecuteAction(A_RIGHT, false);
                        return true;
                }
            }*/

            for (int i = 0; i < _activeActions.length; ++i)
            {
                if (_activeActions[i])
                {
                    _commands.ExecuteAction(i, false);
                    _activeActions[i] = false;
                }
            }
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
