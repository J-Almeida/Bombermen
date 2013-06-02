package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.*;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.utils.Direction;
import pt.up.fe.pt.lpoo.utils.Ref;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Game implements Input.Commands, Disposable
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
        _textureManager = new TextureManager();
        _textureManager.Load("bomb");
        _textureManager.Load("bomberman");
        _textureManager.Load("dpad");
        _textureManager.Load("explosion");
        _textureManager.Load("powerup");
        _textureManager.Load("wall");

        _world = new World();
        _builder = new EntityBuilder(_textureManager);
        
        MapLoader builder = new MapLoader(_world, _builder);
        
        Ref<Integer> width = new Ref<Integer>(0);
        Ref<Integer> height = new Ref<Integer>(0);
        builder.TryLoad(0, width, height);

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
            _socket = Gdx.net.newClientSocket(Protocol.TCP, "192.168.1.71", 7777, null);
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
    private TextureManager _textureManager;

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

    @Override
    public void dispose()
    {
        _socket.dispose();
        _textureManager.dispose();
        _world.dispose();
    }
}
