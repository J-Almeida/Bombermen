package ui.gui.graphical.game;

import java.awt.event.KeyEvent;

/**
 * The Enum Action, used in settings to assign keys to actions
 */
public enum Action
{
    /** Going left */
    HERO_LEFT  (KeyEvent.VK_A),

    /** Going right */
    HERO_RIGHT (KeyEvent.VK_D),

    /** Going up */
    HERO_UP    (KeyEvent.VK_W),

    /** Going down */
    HERO_DOWN  (KeyEvent.VK_S),

    /** The send eagle. */
    SEND_EAGLE (KeyEvent.VK_F1);

    /** Possible actions. */
    public final static Action Actions[] = { HERO_LEFT, HERO_RIGHT, HERO_UP, HERO_DOWN, SEND_EAGLE };

    /**
     * Instantiates a new action.
     *
     * @param defaultKey the default key
     */
    Action(int defaultKey)
    {
        DefaultKey = defaultKey;
    }

    /** The Default key. */
    public final int DefaultKey;
}
