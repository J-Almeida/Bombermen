package pt.up.fe.lpoo.bombermen;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The Class Sender.
 *
 * @param <T> the generic type
 */
public class Sender<T>
{

    /** The out. */
    private ObjectOutputStream _out;

    /**
     * Instantiates a new sender.
     *
     * @param socket the socket
     */
    public Sender(Socket socket)
    {
        try
        {
            _out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        _out.close();
    }

    /**
     * Send.
     *
     * @param msg the msg
     */
    public void Send(T msg)
    {
        System.out.println("Sender: " + msg);
        try
        {
            _out.writeObject(msg);
        }
        catch (IOException e)
        {
        }
    }

    /**
     * Try send.
     *
     * @param msg the msg
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void TrySend(T msg) throws IOException
    {
        _out.writeObject(msg);
    }
}
