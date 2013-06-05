package pt.up.fe.pt.lpoo.bombermen;

import java.net.InetAddress;
import java.net.UnknownHostException;

import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_WALL;
import pt.up.fe.pt.lpoo.utils.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Game implements Input.Commands, Disposable
{
    public Game(Stage stage)
    {
        _stage = stage;

        _textureManager = new TextureManager();
        _textureManager.Load("bomb");
        _textureManager.Load("bomberman");
        _textureManager.Load("dpad");
        _textureManager.Load("explosion");
        _textureManager.Load("powerup");
        _textureManager.Load("wall");

        _world = new World(_stage, this);
        _builder = new EntityBuilder(_textureManager);

        _messageHandler = new MessageHandler()
        {
            @Override
            protected void SMSG_MOVE_Handler(SMSG_MOVE msg)
            {
                System.out.println("Move Handler: " + msg);
                Entity e = _world.GetEntity(msg.Guid);
                if (e == null) return;

                e.setX(msg.x);
                e.setY(msg.y);

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
                        _world.AddEntity(_builder.CreatePlayer(msg.Guid, playerMsg.Name, playerMsg.X, playerMsg.Y));
                        break;
                    }
                    case Entity.TYPE_BOMB:
                    {
                        SMSG_SPAWN_BOMB bombMsg = (SMSG_SPAWN_BOMB) msg;
                        _world.AddEntity(_builder.CreateBomb(bombMsg.Guid, bombMsg.X, bombMsg.Y));
                        break;
                    }
                    case Entity.TYPE_EXPLOSION:
                    {
                        SMSG_SPAWN_EXPLOSION exMsg = (SMSG_SPAWN_EXPLOSION) msg;
                        _world.AddEntity(_builder.CreateExplosion(exMsg.Guid, exMsg.X, exMsg.Y, exMsg.Direction, exMsg.End));
                        break;
                    }
                    case Entity.TYPE_POWER_UP:
                    {
                        SMSG_SPAWN_POWER_UP puMsg = (SMSG_SPAWN_POWER_UP) msg;
                        _world.AddEntity(_builder.CreatePowerUp(puMsg.Guid, puMsg.X, puMsg.Y, puMsg.Type));
                        break;
                    }
                    case Entity.TYPE_WALL:
                    {
                        SMSG_SPAWN_WALL wallMsg = (SMSG_SPAWN_WALL) msg;
                        _world.AddEntity(_builder.CreateWall(msg.Guid, wallMsg.HP, wallMsg.X, wallMsg.Y));
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
            _socket = Gdx.net.newClientSocket(Protocol.TCP, InetAddress.getLocalHost().getHostAddress(), 7777, null);

            _receiver = new Receiver<Message>(_socket);
            _sender = new Sender<Message>(_socket);
            _sender.Send(new CMSG_JOIN("Player 1"));
        }
        catch (GdxRuntimeException GdxE)
        {
            GdxE.printStackTrace();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
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
    private Stage _stage;
    private MessageHandler _messageHandler;

    @Override
    public void ExecuteAction(int action, boolean val)
    {
        switch (action)
        {
            case Input.A_UP:
                _sender.Send(new CMSG_MOVE(Direction.NORTH, val));
                break;
            case Input.A_DOWN:
                _sender.Send(new CMSG_MOVE(Direction.SOUTH, val));
                break;
            case Input.A_LEFT:
                _sender.Send(new CMSG_MOVE(Direction.WEST, val));
                break;
            case Input.A_RIGHT:
                _sender.Send(new CMSG_MOVE(Direction.EAST, val));
                break;
            case Input.A_BOMB:
                _sender.Send(new CMSG_PLACE_BOMB());
                break;
        }
    }

    @Override
    public void dispose()
    {
        _socket.dispose();
        _textureManager.dispose();
        _world.dispose();
    }

    public void Update(/*int diff*/)
    {
        while (!_receiver.IsEmpty())
            _messageHandler.HandleMessage(_receiver.Poll());
    }
}
