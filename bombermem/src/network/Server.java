package network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Server extends UnicastRemoteObject implements ServerInterface
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, ClientCallbackInterface> clients = new HashMap<String, ClientCallbackInterface>();

	protected Server() throws RemoteException {
		super();
	}

	@Override
	public boolean join(String user, ClientCallbackInterface client) throws RemoteException {
		if (clients.containsKey(user))
			return false;
		
		clients.put(user, client);
		
		notifyOthers(user, user + " joined");
		
		for (String name : clients.keySet())
			if (!name.equals(user))
				client.notify(name + " is logged in");
		
		return true;
	}
	
	private void notifyOthers(String source, String message) throws RemoteException
	{
		for (String name : clients.keySet())
			if (!name.equals(source))
				clients.get(name).notify(message);
	}
	
}
/*
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
}*/
