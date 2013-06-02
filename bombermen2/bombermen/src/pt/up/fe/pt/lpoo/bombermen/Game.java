package pt.up.fe.pt.lpoo.bombermen;

import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;
import pt.up.fe.pt.lpoo.utils.Direction;
import pt.up.fe.pt.lpoo.utils.Ref;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
            protected void SMSG_MOVE_Handler(SMSG_MOVE msg)
            {
                System.out.println("Move Handler: " + msg);
                Entity e = _world.GetEntity(msg.Guid);
                if (e == null) return;
                e.SetX(msg.x);
                e.SetY(msg.y);
                System.out.println("Move Handler: " + e);
            }

            @Override
            protected void SMSG_PING_Handler(SMSG_PING msg)
            {
            }

            @Override
            protected void SMSG_SPAWN_Handler(SMSG_SPAWN msg)
            {
                switch (msg.EntityType)
                {
                    case Entity.TYPE_PLAYER:
                    {
                        SMSG_SPAWN_PLAYER playerMsg = (SMSG_SPAWN_PLAYER) msg;
                        _world.AddEntity(_builder.CreatePlayer(msg.Guid, playerMsg.Name, playerMsg.x, playerMsg.y));
                        break;
                    }
                    case Entity.TYPE_BOMB:
                    {
                        SMSG_SPAWN_BOMB bomgMsg = (SMSG_SPAWN_BOMB) msg;
                        _world.AddEntity(_builder.CreateBomb(msg.Guid, _world.GetEntity(bomgMsg.CreatorId).ToPlayer()));
                        break;
                    }
                    case Entity.TYPE_EXPLOSION:
                    {
                        // SMSG_SPAWN_EXPLOSION explosionMsg =
                        // (SMSG_SPAWN_EXPLOSION) msg;
                        // _entities.put(msg.Guid,
                        // _builder.CreateExplosion(msg.Guid, bomb, tileX,
                        // tileY)
                        break;
                    }
                    case Entity.TYPE_POWER_UP:
                    {
                        // SMSG_SPAWN_PLAYER playerMsg = (SMSG_SPAWN_PLAYER)
                        // msg;
                        // _entities.put(msg.Guid,
                        // _builder.CreatePlayer(msg.Guid, playerMsg.Name,
                        // playerMsg.Position.x, playerMsg.Position.y));
                        break;
                    }
                    case Entity.TYPE_WALL:
                    {
                        // SMSG_SPAWN_PLAYER playerMsg = (SMSG_SPAWN_PLAYER)
                        // msg;
                        // _entities.put(msg.Guid,
                        // _builder.CreatePlayer(msg.Guid, playerMsg.Name,
                        // playerMsg.Position.x, playerMsg.Position.y));
                        break;
                    }
                }
            }

            @Override
            protected void Default_Handler(Message msg)
            {
            }

            @Override
            protected void SMSG_DESTROY_Handler(SMSG_DESTROY msg)
            {
                _world.RemoveEntity(msg.Guid);

            }

        };

        try
        {
            _socket = Gdx.net.newClientSocket(Protocol.TCP, "192.168.1.3", 7777, null);
            _receiver = new Receiver<Message>(_socket);
            _sender = new Sender<Message>(_socket);
            _sender.Send(new CMSG_JOIN("Player 1"));
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

    public void draw(SpriteBatch batch)
    {
        _world.draw(batch);
    }

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

    public void Update()
    {
        while (!_receiver.IsEmpty())
            _messageHandler.HandleMessage(_receiver.Poll());
    }
}
