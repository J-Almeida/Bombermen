package pt.up.fe.lpoo.bombermen;

import java.io.IOException;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;

public class Sender<T>
{
    private ObjectOutputStream _out;

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

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        _out.close();
    }

    public void Send(T msg)
    {
        try
        {
            _out.writeObject(msg);
            System.out.println("Sent message " + msg);
            Gdx.app.debug("Sender", "Sent message " + msg);
        }
        catch (IOException e)
        {
            Gdx.app.error("Sender", "Could not send message " + msg);
        }
    }
}
