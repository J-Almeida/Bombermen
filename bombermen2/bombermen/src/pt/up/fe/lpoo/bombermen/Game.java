package pt.up.fe.lpoo.bombermen;

import java.net.InetAddress;
import java.net.UnknownHostException;

import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PING;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.lpoo.bombermen.messages.Message;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DEATH;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.lpoo.bombermen.messages.SMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.SMSG_MOVE_DIR;
import pt.up.fe.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.lpoo.bombermen.messages.SMSG_PLAYER_SPEED;
import pt.up.fe.lpoo.bombermen.messages.SMSG_POWER_UP;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_WALL;
import pt.up.fe.lpoo.bombermen.messages.SMSG_VICTORY;
import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Game implements Input.Commands, Disposable
{
    private int _playerGuid = -1;

    public Player GetCurrentPlayer()
    {
        Entity e = GetEntity(_playerGuid);
        return e == null ? null : e.ToPlayer();
    }

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
                _sender.Send(new CMSG_PING());
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
                        AddEntity(EntityBuilder.CreateBomb(bombMsg.Guid, bombMsg.CreatorGuid, bombMsg.X, bombMsg.Y));
                        Assets.PlaySound("bomb_place");
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
                Assets.PlaySound("dying");
            }

            @Override
            protected void SMSG_VICTORY_Handler(SMSG_VICTORY msg)
            {
                Assets.PlaySound("victory");
            }

            @Override
            protected void SMSG_POWER_UP_Handler(SMSG_POWER_UP msg)
            {
                Assets.PlaySound("powerup");
                // TODO: add this to UI
            }

            @Override
            protected void SMSG_MOVE_DIR_Handler(SMSG_MOVE_DIR msg)
            {
                Entity e = GetEntity(msg.Guid);
                if (e == null) return;
                Player p = e.ToPlayer();
                if (p == null) return;

                p.SetMoving(msg.Dir, msg.Val);
            }

            protected void SMSG_PLAYER_SPEED_Handler(SMSG_PLAYER_SPEED msg)
            {
                Entity e = GetEntity(msg.Guid);
                if (e == null) return;
                Player p = e.ToPlayer();
                if (p == null) return;
                p.SetSpeed(msg.Speed);
            }

            @Override
            protected void SMSG_JOIN_Handler(SMSG_JOIN msg)
            {
                _playerGuid = msg.Guid;
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

    private Array<Entity> _entities = new Array<Entity>();

    public Array<Entity> GetEntities()
    {
        _entities.clear();
        for (Actor a : _stage.getActors())
        {
            if (!(a instanceof Entity))
                continue;
            _entities.add((Entity)a);
        }

        return _entities;
    }

    public Entity GetEntity(int guid)
    {
        for (Actor a : _stage.getActors())
        {
            if (a instanceof Slider)
                continue;
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
