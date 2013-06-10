package pt.up.fe.lpoo.bombermen.messages;

import pt.up.fe.lpoo.bombermen.ClientMessageHandler;

/**
 * The Class SMSG_SCORE.
 */
public class SMSG_SCORE extends Message
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Guid. */
    public final int Guid;

    /** The Score. */
    public final int Score;

    /**
     * Instantiates a new smsg score.
     *
     * @param guid the guid
     * @param score the score
     */
    public SMSG_SCORE(int guid, int score)
    {
        super(SMSG_SCORE);

        Guid = guid;
        Score = score;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#toString()
     */
    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.lpoo.bombermen.messages.Message#Handle(pt.up.fe.lpoo.bombermen.ClientMessageHandler)
     */
    @Override
    public void Handle(ClientMessageHandler cmh)
    {
        cmh.SMSG_SCORE_Handler(this);
    }

}
