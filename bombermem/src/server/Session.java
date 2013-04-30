package server;

import java.net.Socket;

public class Session implements Runnable
{
    private Socket _socket;
    
    public Session(Socket socket)
    {
        _socket = socket;
    }

    @Override
    public void run()
    {
        System.out.println("ServerThread.Run(): [localport, " + _socket.getLocalPort() + "], [port, " + _socket.getPort() + "]");
    }
    
    public void Update(long dt)
    {
        System.out.println("ServerThread.Update(): [dt, " + dt + "]");
    }
}
