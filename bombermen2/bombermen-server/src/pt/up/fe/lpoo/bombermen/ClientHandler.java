package pt.up.fe.lpoo.bombermen;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import pt.up.fe.lpoo.bombermen.messages.Message;
import pt.up.fe.lpoo.bombermen.messages.SMSG_DISCONNECT;
import pt.up.fe.lpoo.bombermen.messages.SMSG_PING;

/**
 * The Class ClientHandler.
 */
public class ClientHandler
{

    /**
     * Stop the handler.
     */
    public void Stop()
    {
        ClientSender.Send(new SMSG_DISCONNECT());
        ClientReceiver.Finish();
    }

    /** The Guid. */
    public final int Guid;

    /** The socket. */
    private Socket _socket;

    /** The server. */
    private final BombermenServer _server;

    /** Sstill connected. */
    private boolean _stillConnected = true;

    /** The timer. */
    private int _timer = 0;

    /** The time ping sent. */
    private long _timePingSent;

    /** The ping. */
    private long _ping = 0;

    /**
     * Gets the ip.
     *
     * @return the string
     */
    public String GetIp()
    {
        return _socket.getInetAddress().getHostAddress();
    }

    /** The Client sender. */
    public final Sender<Message> ClientSender;

    /**
     * The Class ServerReceiver.
     */
    public class ServerReceiver extends Receiver<Message>
    {

        /**
         * Instantiates a new server receiver.
         *
         * @param socket the socket
         */
        public ServerReceiver(Socket socket)
        {
            super(socket);
        }

        /* (non-Javadoc)
         * @see pt.up.fe.lpoo.bombermen.Receiver#run()
         */
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

                        System.out.println("Receiver: " + msg);
                        _server.PushMessage(Guid, msg);
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

    /** The Client receiver. */
    public final Receiver<Message> ClientReceiver;

    /**
     * Instantiates a new client handler.
     *
     * @param guid the guid
     * @param socket the socket
     * @param server the server
     */
    public ClientHandler(int guid, Socket socket, BombermenServer server)
    {
        Guid = guid;
        _socket = socket;
        _server = server;
        ClientSender = new Sender<Message>(_socket);
        ClientReceiver = new ServerReceiver(_socket);
    }

    /**
     * Checks if is still connected.
     *
     * @return true, if successful
     */
    public boolean IsStillConnected()
    {
        return _stillConnected;
    }

    /**
     * Gets the ping.
     *
     * @return the long
     */
    public long GetPing()
    {
        return _ping;
    }

    /**
     * On ping sent.
     */
    public void OnPingSent()
    {
        _timePingSent = System.currentTimeMillis();
    }

    /**
     * On ping received.
     */
    public void OnPingReceived()
    {
        _ping = System.currentTimeMillis() - _timePingSent;
        _ping /= 2;
    }

    /**
     * Update.
     *
     * @param diff the diff
     */
    public void Update(int diff)
    {
        _timer += diff;

        if (_timer >= 1000)
        {
            ClientSender.Send(new SMSG_PING());
            OnPingSent();

            _timer = 0;
            try
            {
                ClientSender.TrySend(null);
                _stillConnected = true;
            }
            catch (IOException e)
            {
                _stillConnected = false;
            }
        }
    }
}
