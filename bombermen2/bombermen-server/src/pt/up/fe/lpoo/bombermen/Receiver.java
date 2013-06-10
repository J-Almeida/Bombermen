package pt.up.fe.lpoo.bombermen;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Class Receiver.
 *
 * @param <T> the generic type
 */
public class Receiver<T> implements Runnable
{

    /** The socket. */
    private Socket _socket;

    /** The done. */
    protected boolean _done;

    /** The thread. */
    private Thread _thread;

    /**
     * Instantiates a new receiver.
     *
     * @param socket the socket
     */
    public Receiver(Socket socket)
    {
        _socket = socket;
        _done = false;
        _thread = new Thread(this);
        _thread.start();
    }

    /**
     * Finish.
     */
    public void Finish()
    {
        _done = true;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @SuppressWarnings("unchecked")
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
                    T msg = (T) in.readObject();
                    if (_done) break;
                    if (msg == null) continue;

                    synchronized (_messageQueue)
                    {
                        _messageQueue.add(msg);
                    }
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
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    /**
     * Poll.
     *
     * @return the t
     */
    public T Poll()
    {
        synchronized (_messageQueue)
        {
            if (_messageQueue.isEmpty()) return null;

            return _messageQueue.poll();
        }
    }

    /**
     * Checks if is empty.
     *
     * @return true, if successful
     */
    public boolean IsEmpty()
    {
        synchronized (_messageQueue)
        {
            return _messageQueue.isEmpty();
        }
    }

    /** The message queue. */
    private Queue<T> _messageQueue = new LinkedList<T>();
}
