package pt.up.fe.pt.lpoo.bombermen;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public void Send(T msg) throws IOException
    {
        _out.writeObject(msg);
    }
}
