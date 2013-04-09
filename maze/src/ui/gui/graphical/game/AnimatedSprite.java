package ui.gui.graphical.game;

import java.awt.image.BufferedImage;

public interface AnimatedSprite {
    public abstract void Update(int diff);
    public abstract BufferedImage GetCurrentImage();
}
