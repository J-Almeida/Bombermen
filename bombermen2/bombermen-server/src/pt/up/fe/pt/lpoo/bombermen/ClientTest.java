package pt.up.fe.pt.lpoo.bombermen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import pt.up.fe.pt.lpoo.bombermen.messages.Message;
import pt.up.fe.pt.lpoo.bombermen.messages.PingMessage;

public class ClientTest
{
    private Receiver<Message> _receiver;
    private Sender<Message> _sender;

    public ClientTest(String host, int port) throws UnknownHostException, IOException
    {
        _socket = new Socket(host, port);

        _sender = new Sender<Message>(_socket);
        _receiver = new Receiver<Message>(_socket);
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        _socket.close();
    }

    private Socket _socket;

    public void Disconnect() throws IOException
    {
        _receiver.Finish();
    }

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
    {
        ClientTest ct = new ClientTest(InetAddress.getLocalHost().getHostAddress(), 7777);
        ct._sender.Send(new PingMessage());

        while (ct._receiver.IsEmpty());

        while (!ct._receiver.IsEmpty())
        {
            System.out.println(ct._receiver.Poll());
        }

        ct.Disconnect();

    }
}
