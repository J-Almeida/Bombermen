package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class Input implements InputProcessor
{
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

    public ControlPad GetControlPad()
    {
        return _controlPad;
    }

    public void SetControlPad(ControlPad controlPad)
    {
        _controlPad = controlPad;
    }

    private Vector3 _tempVector = new Vector3();

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
                Gdx.app.debug("Input", "Player move down.");
                return true;
            case Keys.W:
                Gdx.app.debug("Input", "Player move up.");
                _commands.MovePlayerUp();
                return true;
            case Keys.A:
                Gdx.app.debug("Input", "Player move left.");
                _commands.MovePlayerLeft();
                return true;
            case Keys.D:
                Gdx.app.debug("Input", "Player move right.");
                _commands.MovePlayerRight();
                return true;
            case Keys.SPACE:
                Gdx.app.debug("Input", "Player place bomb.");
                _commands.PlaceBomb();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
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

                System.out.println("Direction: " + dir);

                switch (dir)
                {
                    case ControlPad.DIR_UP:
                        _commands.MovePlayerUp();
                        break;
                    case ControlPad.DIR_DOWN:
                        _commands.MovePlayerDown();
                        break;
                    case ControlPad.DIR_LEFT:
                        _commands.MovePlayerLeft();
                        break;
                    case ControlPad.DIR_RIGHT:
                        _commands.MovePlayerRight();
                        break;
                }
            }
        }

        // if (_tempVector.x > ... && _tempVector.x < ...
        // && _tempVector.y > ... && _tempVector.y < ...)
        // _commands.PlaceBomb();
        // ...

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
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
