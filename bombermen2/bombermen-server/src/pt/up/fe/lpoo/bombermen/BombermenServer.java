package pt.up.fe.lpoo.bombermen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pt.up.fe.lpoo.bombermen.entities.Bomb;
import pt.up.fe.lpoo.bombermen.entities.Bomb.ExplodeHandler;
import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.messages.CMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PING;
import pt.up.fe.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.lpoo.bombermen.messages.Message;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DESTROY;
import pt.up.fe.lpoo.bombermen.messages.SMSG_JOIN;
import pt.up.fe.lpoo.bombermen.messages.SMSG_MOVE_DIR;
import pt.up.fe.lpoo.bombermen.messages.SMSG_SPAWN;
import pt.up.fe.lpoo.utils.Ref;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BombermenServer implements Runnable
{
    private ArrayList<Vector2> _playersPositions = new ArrayList<Vector2>();
    private int _nextPlayerPositionIndex = 0;
    private ClientListener _clientListener = null;
    private ServerSocket _socket = null;
    private int _lastId = 0;
    private HashMap<Integer, ClientHandler> _clients = new HashMap<Integer, ClientHandler>();
    private HashMap<Integer, Entity> _entities = new HashMap<Integer, Entity>();
    private int _numberOfClients = 0;
    private MessageHandler _messageHandler;
    private boolean _running = true;
    private MapLoader _builder;

    private int _numberOfPlayers = 0;

    private String _mapName;

    public String GetMapName()
    {
        return _mapName;
    }

    private ArrayList<Entity> _entitiesToAdd = new ArrayList<Entity>();
    private HashSet<Integer> _entitiesToRemove = new HashSet<Integer>();

    private Queue<ClientMessage> _messageQueue = new LinkedList<ClientMessage>();

    public void AddNewPlayerPosition(Vector2 v)
    {
        _playersPositions.add(new Vector2(v.x * Constants.CELL_SIZE + 4, v.y * Constants.CELL_SIZE + 4));
    }

    private Vector2 GetNewPlayerPosition()
    {
        Vector2 result = _playersPositions.get(_nextPlayerPositionIndex++);
        _nextPlayerPositionIndex %= _playersPositions.size();
        return result;
    }

    public void ShufflePlayerPositions()
    {
        Collections.shuffle(_playersPositions);
    }

    public ClientHandler GetClient(int guid)
    {
        return _clients.get(guid);
    }

    public void SetClientListener(ClientListener cl)
    {
        _clientListener = cl;
    }

    public HashMap<Integer, Entity> GetEntities()
    {
        return _entities;
    }

    public void RemoveEntityNextUpdate(int guid)
    {
        _entitiesToRemove.add(guid);
    }

    public void CreateEntityNextUpdate(Entity e)
    {
        _entitiesToAdd.add(e);
    }

    public int IncLastId() // returns previous id
    {
        return _lastId++;
    }

    public void PushMessage(int guid, Message msg)
    {
        _messageQueue.add(new ClientMessage(guid, msg));
    }

    public void Update(int diff)
    {
        synchronized (_messageQueue)
        {
            while (!_messageQueue.isEmpty())
            {
                _messageHandler.HandleMessage(_messageQueue.poll());
            }
        }

        synchronized (_entities)
        {
            for (Entity e : _entities.values())
                e.Update(diff);

            for (Entity e : _entitiesToAdd)
                _entities.put(e.GetGuid(), e);
            _entitiesToAdd.clear();

            Iterator<Entity> it = _entities.values().iterator();
            while (it.hasNext())
            {
                Entity e = it.next();
                if (_entitiesToRemove.contains(e.GetGuid()))
                {
                    if (!e.IsExplosion()) SendAll(new SMSG_DESTROY(e.GetGuid()));

                    if (e.IsPlayer())
                    {
                        Player p = new Player(e.GetGuid(), e.ToPlayer().GetName(), GetNewPlayerPosition(), this);
                        CreateEntityNextUpdate(p);
                        SendAll(p.GetSpawnMessage());
                    }

                    e.OnDestroy(); // last chance to do anything
                    it.remove();
                }
            }

            _entitiesToRemove.clear();

            for (Entity e1 : _entities.values())
                for (Entity e2 : _entities.values())
                    if (e1.GetGuid() != e2.GetGuid()) if (e1.Collides(e2)) e1.OnCollision(e2);
        }

        synchronized (_clients)
        {
            ArrayList<Integer> removed = new ArrayList<Integer>();
            Iterator<ClientHandler> it = _clients.values().iterator();
            while (it.hasNext())
            {
                ClientHandler ch = it.next();

                ch.Update(diff);

                if (!ch.IsStillConnected())
                {
                    if (_clientListener != null) _clientListener.RemoveClient(ch.Guid);
                    removed.add(ch.Guid);
                    it.remove();
                    System.out.println("Client Removed.");
                }
            }

            for (Integer i : removed)
            {
                _entities.remove(i);
                SMSG_DESTROY msg = new SMSG_DESTROY(i);
                for (ClientHandler ch : _clients.values())
                {
                    ch.ClientSender.Send(msg);
                }
            }
        }

        if (_numberOfClients != _clients.size())
        {
            _numberOfClients = _clients.size();
            System.out.println("Number of Clients: " + _numberOfClients);
        }

    }

    public BombermenServer(int port, String mapName) throws IOException
    {
        _socket = new ServerSocket(port);
        _mapName = mapName;
        System.out.println("Server created - " + InetAddress.getLocalHost().getHostAddress() + ":" + _socket.getLocalPort());

        _messageHandler = new MessageHandler()
        {
            @Override
            protected void CMSG_MOVE_Handler(int guid, CMSG_MOVE msg)
            {
                Entity e = _entities.get(guid);
                if (e == null) return;
                Player p = e.ToPlayer();
                if (p == null) return;

                p.SetMoving(msg.Val, msg.Dir);

                SendAll(new SMSG_MOVE_DIR(guid, msg.Dir, msg.Val));

            }

            @Override
            protected void CMSG_PLACE_BOMB_Handler(int guid, CMSG_PLACE_BOMB msg)
            {
                System.out.println("Place bomb message received from " + guid + " : " + msg);

                final Player p = _entities.get(guid).ToPlayer();
                if (p == null)
                {
                    System.out.println("Player sent unknown guid (" + guid + "). Ignored.");
                    return;
                }

                if (p.GetCurrentBombs() >= p.GetMaxBombs())
                {
                    System.out.println("Player tried to place bomb without max bombs available.");
                    return;
                }

                p.UpdateCurrentBombs(1);

                float playerX = p.GetX() + Constants.PLAYER_WIDTH / 2.f;
                float playerY = p.GetY() + Constants.PLAYER_HEIGHT / 2.f;

                int tileX = MathUtils.floor(playerX / Constants.CELL_SIZE);
                int tileY = MathUtils.floor(playerY / Constants.CELL_SIZE);

                float x = tileX * Constants.CELL_SIZE + 0.1f * Constants.CELL_SIZE;
                float y = tileY * Constants.CELL_SIZE + 0.1f * Constants.CELL_SIZE;

                Vector2 position = new Vector2(x, y); // (0.9, 0.9)
                Bomb b = new Bomb(BombermenServer.this.IncLastId(), p.GetGuid(), position, p.GetExplosionRadius(), BombermenServer.this);
                BombermenServer.this.CreateEntityNextUpdate(b);

                b.AddOnExplodeHandler(new ExplodeHandler()
                {
                    @Override
                    public void OnExplode()
                    {
                        p.UpdateCurrentBombs(-1);
                    }
                });

                SMSG_SPAWN bombMsg = b.GetSpawnMessage();
                for (ClientHandler ch : _clients.values())
                    ch.ClientSender.Send(bombMsg);
            }

            @Override
            protected void Default_Handler(int guid, Message msg)
            {
                System.out.println("Unhandled message received from " + guid + " : " + msg);
            }

            @Override
            protected void CMSG_JOIN_Handler(int guid, CMSG_JOIN msg)
            {
                if (_numberOfPlayers == _playersPositions.size()) return; // Send message informing that the server is full
                
                Vector2 pos = GetNewPlayerPosition();

                Player p = new Player(guid, msg.Name, pos, BombermenServer.this);
                _entities.put(guid, p);
                System.out.println("Player '" + msg.Name + "' (guid: " + guid + ") just joined.");

                SMSG_SPAWN msg1 = p.GetSpawnMessage();
                for (ClientHandler ch : _clients.values())
                    if (ch.Guid != guid) ch.ClientSender.Send(msg1);

                ClientHandler ch = _clients.get(guid);
                for (Entity e : _entities.values())
                    ch.ClientSender.Send(e.GetSpawnMessage());

                ch.ClientSender.Send(new SMSG_JOIN(guid, _builder.GetMaxWidth(), _builder.GetMaxHeight()));

                if (_clientListener != null) _clientListener.UpdateClient(guid);

                _numberOfPlayers++;
            }

            @Override
            protected void CMSG_PING_Handler(int guid, CMSG_PING msg)
            {
                ClientHandler ch = _clients.get(guid);

                ch.OnPingReceived();
                if (_clientListener != null) _clientListener.UpdateClient(guid);
            }
        };

        _builder = new MapLoader(this);

        Ref<Integer> width = new Ref<Integer>(0);
        Ref<Integer> height = new Ref<Integer>(0);

        if (!_builder.TryLoad(mapName, width, height))
        {
            System.out.println("Could not load map " + 0);
            return;
        }

        new Thread(this).start();
    }

    public static void main(String[] args) throws IOException
    {
        BombermenServer sv = new BombermenServer(7777, "map0");

        long millis = System.currentTimeMillis();

        while (true)
        {
            int dt = (int) (System.currentTimeMillis() - millis);
            millis = System.currentTimeMillis();

            sv.Update(dt);

            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run()
    {
        do
        {
            Socket socket;
            try
            {
                socket = _socket.accept();

                int clientId = IncLastId();

                ClientHandler ch;

                ch = new ClientHandler(clientId, socket, this);

                synchronized (_clients)
                {
                    _clients.put(clientId, ch);
                }

                if (_clientListener != null) _clientListener.AddClient(ch);

            }
            catch (java.net.SocketException e)
            {
                _running = false;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        while (_running);
    }

    public void SendAll(Message msg)
    {
        for (ClientHandler ch : _clients.values())
            ch.ClientSender.Send(msg);
    }

    public void SendTo(int guid, Message msg)
    {
        ClientHandler ch = _clients.get(guid);
        if (ch != null) ch.ClientSender.Send(msg);
    }

    public void Stop()
    {
        _running = false;
        _clients.clear();
        try
        {
            _socket.close();
        }
        catch (IOException e)
        {
        }
    }
}
