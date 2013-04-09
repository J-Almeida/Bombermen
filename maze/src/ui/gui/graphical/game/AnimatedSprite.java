package ui.gui.graphical.game;

import java.awt.image.BufferedImage;

import model.Position;

public interface AnimatedSprite {
    public abstract void Update(int diff);
    public abstract BufferedImage GetCurrentImage();
    public abstract Position GetPosition();
    public abstract Position GetDeltaPosition(int cell_width, int cell_height);
    public abstract boolean IsAlive();
    public abstract int GetUnitId();
}
