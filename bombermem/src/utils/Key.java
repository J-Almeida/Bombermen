package utils;

import java.awt.event.KeyEvent;

/**
 * Represents a keyboard key.
 */
public enum Key
{
    /** Up arrow (W). */
    UP(KeyEvent.VK_W),

    /** Down arrow (S). */
    DOWN(KeyEvent.VK_S),

    /** Left arrow (A). */
    LEFT(KeyEvent.VK_A),

    /** Right arrow (D). */
    RIGHT(KeyEvent.VK_D),

    /** Space bar (space). */
    SPACE(KeyEvent.VK_SPACE),

    /** Escape (esc). */
    ESC(KeyEvent.VK_ESCAPE);

    /**
     * Instantiates a new key.
     *
     * @param defaultKey key associated with this enum.
     */
    Key(int defaultKey) { _key = defaultKey; }

    /** Get key associated with this enum. */
    int GetKey() { return _key; }

    /** Key from KeyEvent associated with this enum. */
    private int _key;

    /** Converts KeyEvent to this enum. */
    public static Key ToEnum(int num)
    {
        for (Key v : Key.values())
            if (v._key == num)
                return v;

        return null;
    }
}
