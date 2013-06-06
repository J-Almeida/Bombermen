package pt.up.fe.pt.lpoo.bombermen;

public interface ClientListener
{
    void AddClient(ClientHandler ch);
    void RemoveClient(int guid);
    void UpdateClient(int guid);
}
