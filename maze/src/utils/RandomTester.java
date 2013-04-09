package utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * The Class RandomTester.
 *
 * Explicitly define what values Random returns (used in unit testing)
 */
public class RandomTester extends Random {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The booleans to be used. */
    private Queue<Boolean> _nextBooleans = new LinkedList<Boolean>();

    /** The default boolean. */
    private boolean _defaultBoolean = true;

    /** The integers to be used. */
    private Queue<Integer> _nextInts = new LinkedList<Integer>();

    /** The default integer. */
    private int _defaultInt = 0;

    /**
     * Push int.
     *
     * @param val the int
     */
    public void PushInt(int val)
    {
        _nextInts.add(val);
    }

    /**
     * Push multiple ints.
     *
     * @param val the array of ints
     */
    public void PushInts(int[] val)
    {
        for (int i : val)
            _nextInts.add(i);
    }

    /**
     * Push boolean.
     *
     * @param val the boolean
     */
    public void PushBoolean(boolean val)
    {
        _nextBooleans.add(val);
    }

    /**
     * Push multiple booleans.
     *
     * @param val the array of booleans.
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
