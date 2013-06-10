package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * The Class Input.
 */
public class Input implements InputProcessor
{
    /** The Constant A_UP. */
    public static final int A_UP = 0;

    /** The Constant A_DOWN. */
    public static final int A_DOWN = 1;

    /** The Constant A_LEFT. */
    public static final int A_LEFT = 2;

    /** The Constant A_RIGHT. */
    public static final int A_RIGHT = 3;

    /** The Constant A_BOMB. */
    public static final int A_BOMB = 4;

    /** The Constant A_NUM_OF_ACTIONS. */
    public static final int A_NUM_OF_ACTIONS = 5;

    /** The active actions. */
    private boolean[] _activeActions = { false, false, false, false };

    /**
     * The Interface Commands.
     */
    public interface Commands
    {

        /**
         * Execute action.
         *
         * @param action the action
         * @param val the val
         */
        void ExecuteAction(int action, boolean val);
    }

    /** The commands. */
    private Commands _commands;

    /** The camera. */
    private Camera _camera;

    /** The control pad. */
    private ControlPad _controlPad = null;

    /** The temp vector. */
    private Vector3 _tempVector = new Vector3();

    /**
     * Sets the control pad.
     *
     * @param controlPad the control pad
     */
    public void SetControlPad(ControlPad controlPad)
    {
        _controlPad = controlPad;
    }

    /**
     * Instantiates a new input.
     *
     * @param commands the commands
     * @param camera the camera
     */
    public Input(Commands commands, Camera camera)
    {
        _commands = commands;
        _camera = camera;
    }

    /**
     * Convert keycode.
     *
     * @param keycode the keycode
     * @return the int
     */
    private static int ConvertKeycode(int keycode)
    {
        switch (Settings.Keys)
        {
            case Settings.KEYS_IJKL:
            {
                switch (keycode)
                {
                    case Keys.I:
                        return Keys.W;
                    case Keys.J:
                        return Keys.A;
                    case Keys.S:
                        return Keys.K;
                    case Keys.D:
                        return Keys.L;
                    default:
                        return keycode;
                }
            }
            case Settings.KEYS_ARROWS:
            {
                switch (keycode)
                {
                    case Keys.UP:
                        return Keys.W;
                    case Keys.LEFT:
                        return Keys.A;
                    case Keys.DOWN:
                        return Keys.S;
                    case Keys.RIGHT:
                        return Keys.D;
                    default:
                        return keycode;
                }
            }
            case Settings.KEYS_WASD:
            default:
                return keycode;
        }
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.InputProcessor#keyDown(int)
     */
    @Override
    public boolean keyDown(int keycode)
    {
        switch (ConvertKeycode(keycode))
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

    /* (non-Javadoc)
     * @see com.badlogic.gdx.InputProcessor#keyUp(int)
     */
    @Override
    public boolean keyUp(int keycode)
    {
        switch (ConvertKeycode(keycode))
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

    /* (non-Javadoc)
     * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
     */
    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
     */
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

    /* (non-Javadoc)
     * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        _tempVector.set(screenX, screenY, 0);
        _camera.unproject(_tempVector);

        if (_controlPad != null)
        {
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

    /* (non-Javadoc)
     * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.InputProcessor#scrolled(int)
     */
    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
