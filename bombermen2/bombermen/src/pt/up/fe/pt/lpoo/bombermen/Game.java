package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.utils.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Game implements Input.Commands
{
    private static Game _instance = null;
    private MessageHandler _messageHandler;

    public static Game Instance()
    {
        if (_instance == null) _instance = new Game();
        return _instance;
    }

    private Game()
    {
        _world = new World();
        _builder = new EntityBuilder();
        _messageHandler = new MessageHandler()
        {
            @Override
            protected void SMSG_PING_Handler(SMSG_PING msg)
            {
                // TODO Auto-generated method stub

            }

            @Override
            protected void SMSG_SPAWN_Handler(SMSG_SPAWN msg)
            {
                // TODO Auto-generated method stub

            }

            @Override
            protected void Default_Handler(Message msg)
            {
                // TODO Auto-generated method stub

            }
        };

        try
        {
            _socket = Gdx.net.newClientSocket(Protocol.TCP, "192.168.1.3", 7777, null);
            _receiver = new Receiver<Message>(_socket);
            _sender = new Sender<Message>(_socket);
            _sender.Send(new SMSG_PING());
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