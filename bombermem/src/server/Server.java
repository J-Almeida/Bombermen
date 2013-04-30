package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server
{
    public static final int SERVER_PORT = 6666;

    public static void main(String[] args) throws IOException
    {
        final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

        final Vector<Session> _sessions = new Vector<Session>();
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                boolean listening = true;
                while (listening)
                {
                    Session session;
                    try
                    {
                        session = new Session(serverSocket.accept());
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                        break;
                    }

                    _sessions.add(session);
                    
                    Thread thread = new Thread(session);
                    thread.start();
                }
            }
        }).start();

        long millis = System.currentTimeMillis();
        boolean running = true;
        while (running)
        {
            long dt = System.currentTimeMillis() - millis;
            for (Session s : _sessions)
            {
                s.Update(dt);
            }
            
            millis -= dt;
        }

        serverSocket.close();
    }
}
