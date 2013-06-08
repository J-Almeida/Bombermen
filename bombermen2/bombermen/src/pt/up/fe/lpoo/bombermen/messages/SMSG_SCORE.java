package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

public class SMSG_SCORE extends Message
{
    private static final long serialVersionUID = 1L;

    public final int Guid;
    public final int Score;

    public SMSG_SCORE(int guid, int score)
    {
        super(SMSG_SCORE);

        Guid = guid;
        Score = score;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_SCORE_Handler(this);
    }

}
