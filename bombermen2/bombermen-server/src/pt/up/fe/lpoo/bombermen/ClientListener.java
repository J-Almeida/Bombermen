package pt.up.fe.lpoo.bombermen;

public interface ClientListener
{
    /**
     * Adds the client.
     *
     * @param ch the ch
     */
    void AddClient(ClientHandler ch);

    /**
     * Removes the client.
     *
     * @param guid the guid
     */
    void RemoveClient(int guid);

    /**
     * Update client.
     *
     * @param guid the guid
     */
    void UpdateClient(int guid);
}
