package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.utils.Direction;

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
    public static final int TIME_OF_INPUT = 500;

    private int[] _timers = { 0, 0, 0, 0, 0 };
    private boolean[] _actions = { false, false, false, false, false };

    public void Update(int diff)
    {
        for (int i = 0; i < _timers.length; ++i)
        {
            if (_actions[i])
            {
                _timers[i] += diff;
                if (_timers[i] > TIME_OF_INPUT)
                {
                    _timers[i] = 0;
                    CallCommand(i);
                }
            }
        }
    }

    public void CallCommand(int act)
    {
        switch (act)
        {
            case A_UP:
                _commands.MovePlayerUp();
                break;
            case A_DOWN:
                _commands.MovePlayerDown();
                break;
            case A_LEFT:
                _commands.MovePlayerLeft();
                break;
            case A_RIGHT:
                _commands.MovePlayerRight();
                break;
            case A_BOMB:
                _commands.PlaceBomb();
                break;
        }
    }

    public interface Commands
    {
        void MovePlayerDown();

        void MovePlayerUp();

        void MovePlayerLeft();

        void MovePlayerRight();

        void PlaceBomb();
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
                _commands.MovePlayerDown();
                _actions[A_DOWN] = true;
                _timers[A_DOWN] = 0;
                Gdx.app.debug("Input", "Player move down.");
                return true;
            case Keys.W:
                Gdx.app.debug("Input", "Player move up.");
                _commands.MovePlayerUp();
                _actions[A_UP] = true;
                _timers[A_UP] = 0;
                return true;
            case Keys.A:
                Gdx.app.debug("Input", "Player move left.");
                _commands.MovePlayerLeft();
                _actions[A_LEFT] = true;
                _timers[A_LEFT] = 0;
                return true;
            case Keys.D:
                Gdx.app.debug("Input", "Player move right.");
                _commands.MovePlayerRight();
                _actions[A_RIGHT] = true;
                _timers[A_RIGHT] = 0;
                return true;
            case Keys.SPACE:
                Gdx.app.debug("Input", "Player place bomb.");
                _commands.PlaceBomb();
                _actions[A_BOMB] = true;
                _timers[A_BOMB] = 0;
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
                _actions[A_DOWN] = false;
                return true;
            case Keys.W:
                _actions[A_UP] = false;
                return true;
            case Keys.A:
                _actions[A_LEFT] = false;
                return true;
            case Keys.D:
                _actions[A_RIGHT] = false;
                return true;
            case Keys.SPACE:
                _actions[A_BOMB] = false;
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
                        _commands.MovePlayerUp();
                        _actions[A_UP] = true;
                        _timers[A_UP] = 0;
                        return true;
                    case Direction.SOUTH:
                        _commands.MovePlayerDown();
                        _actions[A_DOWN] = true;
                        _timers[A_DOWN] = 0;
                        return true;
                    case Direction.WEST:
                        _commands.MovePlayerLeft();
                        _actions[A_LEFT] = true;
                        _timers[A_LEFT] = 0;
                        return true;
                    case Direction.EAST:
                        _commands.MovePlayerRight();
                        _actions[A_RIGHT] = true;
                        _timers[A_RIGHT] = 0;
                        return true;
                    case ControlPad.PLACE_BOMB:
                        _commands.PlaceBomb();
                        _actions[A_BOMB] = true;
                        _timers[A_BOMB] = 0;
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
            if (button == Buttons.LEFT)
            {
                int dir = _controlPad.Click(_tempVector);

                switch (dir)
                {
                    case Direction.NORTH:
                        _actions[A_UP] = false;
                        return true;
                    case Direction.SOUTH:
                        _actions[A_DOWN] = false;
                        return true;
                    case Direction.WEST:
                        _actions[A_LEFT] = false;
                        return true;
                    case Direction.EAST:
                        _actions[A_RIGHT] = false;
                        return true;
                    case ControlPad.PLACE_BOMB:
                        _actions[A_BOMB] = false;
                        return true;
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
