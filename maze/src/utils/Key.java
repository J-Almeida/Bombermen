package utils;

/**
 * Represents a keyboard key.
 */
public enum Key
{
    /** Up arrow (W). */
    UP('W', 1),

    /** Down arrow (S). */
    DOWN('S', 2),

    /** Left arrow (A). */
    LEFT('A', 3),

    /** Right arrow (D). */
    RIGHT('D', 4);

    /**
     * Instantiates a new key.
     *
     * @param c the character
     * @param i the value
     */
    Key(char c, int i) { _val = c;    _num = i; }

    /**
     * Gets the value.
     *
     * @return the character
     */
    public char GetValue() { return _val; }

    /**
     * Converts character to Key enum.
     *
     * @param val the character
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
     * Converts value to Key enum.
     *
     * @param num the value
     * @return the key
     */
    public static Key toEnum(int num)
    {
        for (Key v : Key.values())
            if (v._num == num)
                return v;

        return null;
    }

    /** The character. */
    private char _val;

    /** The value. */
    private int _num;
}
