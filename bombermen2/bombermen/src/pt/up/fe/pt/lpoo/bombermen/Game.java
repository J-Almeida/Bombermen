package pt.up.fe.pt.lpoo.bombermen;

import java.net.InetAddress;
import java.net.UnknownHostException;

import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_DEATH;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_POWER_UP;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN_WALL;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_VICTORY;
import pt.up.fe.pt.lpoo.utils.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Game implements Input.Commands, Disposable
{
    public Game(Stage stage)
    {
        _stage = stage;

        _messageHandler = new MessageHandler()
        {
            @Override
            protected void SMSG_MOVE_Handler(SMSG_MOVE msg)
            {
                System.out.println("Move Handler: " + msg);
                Entity e = GetEntity(msg.Guid);
                if (e == null) return;

                e.setX(msg.X);
                e.setY(msg.Y);

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
                        AddEntity(EntityBuilder.CreatePlayer(msg.Guid, playerMsg.Name, playerMsg.X, playerMsg.Y));
                        break;
                    }
                    case Entity.TYPE_BOMB:
                    {
                        SMSG_SPAWN_BOMB bombMsg = (SMSG_SPAWN_BOMB) msg;
                        AddEntity(EntityBuilder.CreateBomb(bombMsg.Guid, bombMsg.X, bombMsg.Y));
                        AssetManagerHelper.GetSound("bomb_place").play();
                        break;
                    }
                    case Entity.TYPE_EXPLOSION:
                    {
                        SMSG_SPAWN_EXPLOSION exMsg = (SMSG_SPAWN_EXPLOSION) msg;
                        AddEntity(EntityBuilder.CreateExplosion(exMsg.Guid, exMsg.X, exMsg.Y, exMsg.Direction, exMsg.End));
                        break;
                    }
                    case Entity.TYPE_POWER_UP:
                    {
                        SMSG_SPAWN_POWER_UP puMsg = (SMSG_SPAWN_POWER_UP) msg;
                        AddEntity(EntityBuilder.CreatePowerUp(puMsg.Guid, puMsg.X, puMsg.Y, puMsg.Type));
                        break;
                    }
                    case Entity.TYPE_WALL:
                    {
                        SMSG_SPAWN_WALL wallMsg = (SMSG_SPAWN_WALL) msg;
                        AddEntity(EntityBuilder.CreateWall(msg.Guid, wallMsg.HP, wallMsg.X, wallMsg.Y));
                        break;
                    }
                }
            }

            @Override
            protected void SMSG_DESTROY_Handler(SMSG_DESTROY msg)
            {
                RemoveEntity(msg.Guid);
            }

            @Override
            protected void SMSG_DEATH_Handler(SMSG_DEATH msg)
            {
                AssetManagerHelper.GetSound("dying").play();
            }

            @Override
            protected void SMSG_VICTORY_Handler(SMSG_VICTORY msg)
            {
                AssetManagerHelper.GetSound("victory").play();
            }

            @Override
            protected void SMSG_POWER_UP_Handler(SMSG_POWER_UP msg)
            {
                AssetManagerHelper.GetSound("powerup").play();
                // TODO: add this to UI
            }

            @Override
            protected void Default_Handler(Message msg)
            {
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

    public void AddEntity(final Entity entity)
    {
        _stage.addActor(entity);
    }

    public Entity GetEntity(int guid)
    {
        for (Actor a : _stage.getActors())
        {
            Entity e = (Entity)a;
            if (e.GetGuid() == guid)
                return e;
        }

        return null;
    }

    public void RemoveEntity(int guid)
    {
        Entity e = GetEntity(guid);
        if (e != null)
            RemoveEntity(e);
    }

    public void RemoveEntity(Entity entity)
    {
        entity.remove();
    }

    public Sender<Message> GetSender()
    {
        return _sender;
    }

    private Receiver<Message> _receiver;
    private Sender<Message> _sender;
    private Socket _socket;
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
    }

    public void Update(/*int diff*/)
    {
        if (_receiver != null)
            while (!_receiver.IsEmpty())
                _messageHandler.HandleMessage(_receiver.Poll());
    }
}
