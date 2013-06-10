package pt.up.fe.lpoo.bombermen;

public interface ClientListener
{
    void AddClient(ClientHandler ch);

    void RemoveClient(int guid);

    void UpdateClient(int guid);
}
