package utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class RandomTester.
 */
public class RandomTester extends Random {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The _next booleans. */
    private Queue<Boolean> _nextBooleans = new LinkedList<Boolean>();

    /** The _default boolean. */
    private boolean _defaultBoolean = true;

    /** The _next ints. */
    private Queue<Integer> _nextInts = new LinkedList<Integer>();

    /** The _default int. */
    private int _defaultInt = 0;

    /**
     * Push int.
     *
     * @param val the val
     */
    public void PushInt(int val)
    {
        _nextInts.add(val);
    }

    /**
     * Push ints.
     *
     * @param val the val
     */
    public void PushInts(int[] val)
    {
        for (int i : val)
            _nextInts.add(i);
    }

    /**
     * Push boolean.
     *
     * @param val the val
     */
    public void PushBoolean(boolean val)
    {
        _nextBooleans.add(val);
    }

    /**
     * Push booleans.
     *
     * @param val the val
     */
    public void PushBooleans(boolean[] val)
    {
        for (boolean b : val)
            _nextBooleans.add(b);
    }

    /* (non-Javadoc)
     * @see java.util.Random#nextBoolean()
     */
    @Override
    public boolean nextBoolean()
    {
        return _nextBooleans.isEmpty() ? _defaultBoolean : _nextBooleans.poll();
    }

    /* (non-Javadoc)
     * @see java.util.Random#nextInt()
     */
    @Override
    public int nextInt()
    {
        return _nextInts.isEmpty() ? _defaultInt : _nextInts.poll();
    }

    /* (non-Javadoc)
     * @see java.util.Random#nextInt(int)
     */
    @Override
    public int nextInt(int arg0)
    {
        return _nextInts.isEmpty() ? _defaultInt : _nextInts.poll();
    }



}
