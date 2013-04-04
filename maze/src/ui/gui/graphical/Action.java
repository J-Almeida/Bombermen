package ui.gui.graphical;

import java.awt.event.KeyEvent;

public enum Action
{
    HERO_LEFT    (KeyEvent.VK_A),
    HERO_RIGHT    (KeyEvent.VK_D),
    HERO_UP        (KeyEvent.VK_W),
    HERO_DOWN    (KeyEvent.VK_S),
    SEND_EAGLE    (KeyEvent.VK_F1);

    public final static Action Actions[] = { HERO_LEFT, HERO_RIGHT, HERO_UP, HERO_DOWN, SEND_EAGLE };

    Action(int defaultKey)
    {
        DefaultKey = defaultKey;
    }

    public final int DefaultKey;
}
