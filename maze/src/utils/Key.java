package utils;

// TODO: Auto-generated Javadoc
/**
 * The Enum Key.
 */
public enum Key
{

    /** The up. */
    UP('W', 1),

    /** The down. */
    DOWN('S', 2),

    /** The left. */
    LEFT('A', 3),

    /** The right. */
    RIGHT('D', 4);

    /**
     * Instantiates a new key.
     *
     * @param c the c
     * @param i the i
     */
    Key(char c, int i) { _val = c;    _num = i; }

    /**
     * Gets the value.
     *
     * @return the char
     */
    public char GetValue() { return _val; }

    /**
     * To enum.
     *
     * @param val the val
     * @return the key
     */
    public static Key toEnum(char val)
    {
        for (Key v : Key.values())
            if (v._val == val)
                return v;

        return null;
    }

    /**
     * To enum.
     *
     * @param num the num
     * @return the key
     */
    public static Key toEnum(int num)
    {
        for (Key v : Key.values())
            if (v._num == num)
                return v;

        return null;
    }

    /** The _val. */
    private char _val;

    /** The _num. */
    private int _num;
}
