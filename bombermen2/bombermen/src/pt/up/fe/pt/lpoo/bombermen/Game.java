package pt.up.fe.pt.lpoo.bombermen;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.GdxRuntimeException;

import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.utils.Direction;

public class Game implements Input.Commands
{
    private static Game _instance = null;
    public static Game Instance()
    {
        if (_instance == null)
            _instance = new Game();
        return _instance;
    }
    
    private Game()
    {
        _world = new World();
        _builder = new EntityBuilder();
        
        try
        {
            _socket = Gdx.net.newClientSocket(Protocol.TCP, "127.0.0.1", 7777, null);
            _receiver = new Receiver<Message>(_socket);
            _sender = new Sender<Message>(_socket);
        }
        catch (GdxRuntimeException GdxE)
        {
            GdxE.printStackTrace();
        }
    }
    
    public World GetWorld()
    {
        return _world;
    }
    
    public EntityBuilder GetBuilder()
    {
        return _builder;
    }
    
    public Sender<Message> GetSender()
    {
        return _sender;
    }

    private World _world;
    private EntityBuilder _builder;
    private Receiver<Message> _receiver;
    private Sender<Message> _sender;
    private Socket _socket;

    @Override
    public void MovePlayerDown()
    {
        _sender.Send(new CMSG_MOVE(Direction.SOUTH));
    }

    @Override
    public void MovePlayerUp()
    {
        _sender.Send(new CMSG_MOVE(Direction.NORTH));
    }

    @Override
    public void MovePlayerLeft()
    {
        _sender.Send(new CMSG_MOVE(Direction.WEST));
    }

    @Override
    public void MovePlayerRight()
    {
        _sender.Send(new CMSG_MOVE(Direction.EAST));
    }

    @Override
    public void PlaceBomb()
    {
        _sender.Send(new CMSG_PLACE_BOMB());
    }
}
