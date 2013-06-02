package pt.up.fe.pt.lpoo.bombermen;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_MOVE;
import pt.up.fe.pt.lpoo.bombermen.messages.CMSG_PLACE_BOMB;
import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_PING;
import pt.up.fe.pt.lpoo.bombermen.messages.SMSG_SPAWN;

public class BombermenServer implements Runnable
{
    private ServerSocket _socket = null;
    private int _lastId = 0;
    private HashMap<Integer, ClientHandler> _clients = new HashMap<Integer, ClientHandler>();
    private int _numberOfClients = 0;
    private MessageHandler _messageHandler;

    private Queue<Message> _messageQueue = new LinkedList<Message>();

    public void PushMessage(Message msg)
    {
        _messageQueue.add(msg);
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

        synchronized (_clients)
        {
            Iterator<ClientHandler> it = _clients.values().iterator();
            while (it.hasNext())
            {
                ClientHandler ch = it.next();

                ch.Update(diff);

                if (!ch.IsStillConnected())
                {
                    it.remove();
                    System.out.println("Client Removed.");
                }
            }
        }

        if (_numberOfClients != _clients.size())
        {
            _numberOfClients = _clients.size();
            System.out.println("Number of Clients: " + _numberOfClients);
        }

    }

    public BombermenServer(int port) throws IOException
    {
        _socket = new ServerSocket(port);
        System.out.println("Server created - " + InetAddress.getLocalHost().getHostAddress() + ":" + _socket.getLocalPort());

        _messageHandler = new MessageHandler()
        {
            @Override
            protected void CMSG_MOVE_Handler(CMSG_MOVE msg)
            {
                System.out.println("Move message received: " + msg);
            }

            @Override
            protected void CMSG_PLACE_BOMB_Handler(CMSG_PLACE_BOMB msg)
            {
                System.out.println("Place bomb message received: " + msg);
            }

            @Override
            protected void Default_Handler(Message msg)
            {
                System.out.println("Unhandled message received: " + msg);
            }
        };

        new Thread(this).start();
    }

    public static void main(String[] args) throws IOException
    {
        BombermenServer sv = new BombermenServer(7777);

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

                int clientId = _lastId++;

                ClientHandler ch;

                ch = new ClientHandler(clientId, socket, this);

                synchronized (_clients)
                {
                    _clients.put(clientId, ch);
                }

                ch.ClientSender.Send(new SMSG_SPAWN());
                ch.ClientSender.Send(new SMSG_PING());

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        while (true);
    }
}

class ClientHandler
{
    public final int Guid;
    private Socket _socket;
    private final BombermenServer _server;
    private boolean _stillConnected = true;
    private int _timer = 0;

    public final Sender<Message> ClientSender;

    public class ServerReceiver extends Receiver<Message>
    {
        public ServerReceiver(Socket socket)
        {
            super(socket);
        }

        @Override
        public void run()
        {
            try
            {
                ObjectInputStream in = new ObjectInputStream(_socket.getInputStream());

                while (!_done)
                {
                    try
                    {
                        Message msg = (Message) in.readObject();
                        if (_done) break;
                        if (msg == null) continue;

                        _server.PushMessage(msg);

                    }
                    catch (ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    catch (EOFException e)
                    {
                    }

                }

                in.close();

            }
            catch (SocketException e)
            {
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }

    };

    public final Receiver<Message> ClientReceiver;

    public ClientHandler(int guid, Socket socket, BombermenServer server)
    {
        Guid = guid;
        _socket = socket;
        _server = server;
        ClientSender = new Sender<Message>(_socket);
        ClientReceiver = new ServerReceiver(_socket);
    }

    public boolean IsStillConnected()
    {
        return _stillConnected;
    }

    public void Update(int diff)
    {
        _timer += diff;

        if (_timer >= 1000)
        {
            _timer = 0;
            try
            {
                ClientSender.Send(null);
                _stillConnected = true;
            }
            catch (IOException e)
            {
                _stillConnected = false;
            }
        }
    }

}
