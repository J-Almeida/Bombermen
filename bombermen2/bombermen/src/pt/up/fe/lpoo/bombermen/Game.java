package pt.up.fe.lpoo.bombermen;

import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PING;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.lpoo.bombermen.messages.Message;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DEATH;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DISCONNECT;
import pt.up.fe.lpoo.bombermen.messages.SMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.SMSG_MOVE_DIR;
import pt.up.fe.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.lpoo.bombermen.messages.SMSG_PLAYER_SPEED;
import pt.up.fe.lpoo.bombermen.messages.SMSG_POWER_UP;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SCORE;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_BOMB;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_EXPLOSION;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_PLAYER;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_POWER_UP;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN_WALL;
import pt.up.fe.lpoo.bombermen.messages.SMSG_VICTORY;
import pt.up.fe.lpoo.utils.Direction;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * The Class Game - world
 */
public class Game implements Input.Commands, Disposable
{
    /** The current player guid. */
    private int _playerGuid = -1;

    /** The map width. */
    private int _mapWidth = 0;

    /** The map height. */
    private int _mapHeight = 0;

    /**
     * Gets the map width.
     *
     * @return the int
     */
    public int GetMapWidth()
    {
        return _mapWidth;
    }

    /**
     * Gets the map height.
     *
     * @return the int
     */
    public int GetMapHeight()
    {
        return _mapHeight;
    }

    /**
     * Gets the current player.
     *
     * @return the player
     */
    public Player GetCurrentPlayer()
    {
        Entity e = GetEntity(_playerGuid);
        return e == null ? null : e.ToPlayer();
    }

    /**
     * Instantiates a new game.
     *
     * @param stage the stage
     * @param s the socket
     * @param playerName the player name
     */
    public Game(Stage stage, Socket s, String playerName)
    {
        _stage = stage;
        _socket = s;
        _receiver = new Receiver<Message>(_socket);
        _sender = new Sender<Message>(_socket);
        _sender.Send(new CMSG_JOIN(playerName));
    }

    /**
     * Adds the entity.
     *
     * @param entity the entity
     */
    public void AddEntity(final Entity entity)
    {
        _stage.addActor(entity);
    }

    /** The entities. */
    private Array<Entity> _entities = new Array<Entity>();

    /**
     * Gets the entities.
     *
     * @return the array
     */
    public Array<Entity> GetEntities()
    {
        _entities.clear();
        for (Actor a : _stage.getActors())
        {
            if (!(a instanceof Entity)) continue;
            _entities.add((Entity) a);
        }

        return _entities;
    }

    /**
     * Gets the entity.
     *
     * @param guid the guid
     * @return the entity
     */
    public Entity GetEntity(int guid)
    {
        for (Actor a : _stage.getActors())
        {
            if (!(a instanceof Entity)) continue;
            Entity e = (Entity) a;
            if (e.GetGuid() == guid) return e;
        }

        return null;
    }

    /**
     * Removes the entity.
     *
     * @param guid the guid
     */
    public void RemoveEntity(int guid)
    {
        Entity e = GetEntity(guid);
        if (e != null) RemoveEntity(e);
    }

    /**
     * Removes the entity.
     *
     * @param entity the entity
     */
    public void RemoveEntity(Entity entity)
    {
        entity.remove();
    }

    /**
     * Gets the sender.
     *
     * @return the sender
     */
    public Sender<Message> GetSender()
    {
        return _sender;
    }

    /** The receiver. */
    private Receiver<Message> _receiver;

    /** The sender. */
    private Sender<Message> _sender;

    /** The socket. */
    private Socket _socket;

    /** The stage. */
    private Stage _stage;

    /** The message handler. */
    private ClientMessageHandler _messageHandler = new ClientMessageHandler()
    {
        @Override
        public void SMSG_MOVE_Handler(SMSG_MOVE msg)
        {
            System.out.println("Move Handler: " + msg);
            Entity e = GetEntity(msg.Guid);
            if (e == null) return;

            e.setX(msg.X);
            e.setY(msg.Y);

            System.out.println("Move Handler: " + e);
        }

        @Override
        public void SMSG_PING_Handler(SMSG_PING msg)
        {
            _sender.Send(new CMSG_PING());
        }

        @Override
        public void SMSG_SPAWN_Handler(SMSG_SPAWN msg)
        {
            switch (msg.EntityType)
            {
                case Entity.TYPE_PLAYER:
                {
                    SMSG_SPAWN_PLAYER playerMsg = (SMSG_SPAWN_PLAYER) msg;
                    AddEntity(EntityBuilder.CreatePlayer(msg.Guid, playerMsg.Name, playerMsg.Score, playerMsg.X, playerMsg.Y));
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
        public void SMSG_DESTROY_Handler(SMSG_DESTROY msg)
        {
            RemoveEntity(msg.Guid);
        }

        @Override
        public void SMSG_DEATH_Handler(SMSG_DEATH msg)
        {
            Assets.PlaySound("dying");
        }

        @Override
        public void SMSG_VICTORY_Handler(SMSG_VICTORY msg)
        {
            Assets.PlaySound("victory");
        }

        @Override
        public void SMSG_POWER_UP_Handler(SMSG_POWER_UP msg)
        {
            Assets.PlaySound("powerup");
            // TODO: add this to UI
        }

        @Override
        public void SMSG_MOVE_DIR_Handler(SMSG_MOVE_DIR msg)
        {
            Entity e = GetEntity(msg.Guid);
            if (e == null) return;
            Player p = e.ToPlayer();
            if (p == null) return;

            p.SetMoving(msg.Dir, msg.Val);
        }

        @Override
        public void SMSG_PLAYER_SPEED_Handler(SMSG_PLAYER_SPEED msg)
        {
            Entity e = GetEntity(msg.Guid);
            if (e == null) return;
            Player p = e.ToPlayer();
            if (p == null) return;
            p.SetSpeed(msg.Speed);
        }

        @Override
        public void SMSG_JOIN_Handler(SMSG_JOIN msg)
        {
            _playerGuid = msg.Guid;
            _mapWidth = msg.MapWidth;
            _mapHeight = msg.MapHeight;
        }

        @Override
        public void Default_Handler(Message msg)
        {

        }

        @Override
        public void SMSG_SCORE_Handler(SMSG_SCORE msg)
        {
            Entity e = GetEntity(msg.Guid);
            if (e == null) return;
            Player p = e.ToPlayer();
            if (p == null) return;
            p.SetScore(msg.Score);

        }

        @Override
        public void SMSG_DISCONNECT_Handler(SMSG_DISCONNECT msg)
        {
            System.out.println("The server was shutdown.");
            _stage.clear();

        }
    };

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.Input.Commands#ExecuteAction(int, boolean)
     */
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

    /* (non-Javadoc)
     * @see com.badlogic.gdx.utils.Disposable#dispose()
     */
    @Override
    public void dispose()
    {
        _socket.dispose();
    }

    /**
     * Update, process messages from the server
     */
    public void Update(/* int diff */)
    {
        if (_receiver != null)
            while (!_receiver.IsEmpty())
                _messageHandler.HandleMessage(_receiver.Poll());
    }
}
