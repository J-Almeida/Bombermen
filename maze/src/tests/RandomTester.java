package tests;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class RandomTester extends Random {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Queue<Boolean> _nextBooleans = new LinkedList<Boolean>();
    private boolean _defaultBoolean = true;

    private Queue<Integer> _nextInts = new LinkedList<Integer>();
    private int _defaultInt = 0;

    public void PushInt(int val)
    {
        _nextInts.add(val);
    }

    public void PushInts(int[] val)
    {
        for (int i : val)
            _nextInts.add(i);
    }

    public void PushBoolean(boolean val)
    {
        _nextBooleans.add(val);
    }

    public void PushBooleans(boolean[] val)
    {
        for (boolean b : val)
            _nextBooleans.add(b);
    }

    @Override
    public boolean nextBoolean()
    {
        return _nextBooleans.isEmpty() ? _defaultBoolean : _nextBooleans.poll();
    }

    @Override
    public int nextInt()
    {
        return _nextInts.isEmpty() ? _defaultInt : _nextInts.poll();
    }

    @Override
    public int nextInt(int arg0)
    {
        return _nextInts.isEmpty() ? _defaultInt : _nextInts.poll();
    }



}
